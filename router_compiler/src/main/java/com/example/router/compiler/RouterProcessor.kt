package com.example.router.compiler

import com.example.router.compiler.annotation.MRoute
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeSpec
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Filer
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic
import javax.tools.JavaFileObject

/**
 * 注解处理是javac内置的一个工具，用于在编译时扫描和处理注解
 * 它可以创建新的源文件；但是，它不能修改现有的
 * 它是轮流完成的。当编译到达预编译阶段时，第一轮开始。如果这一轮生成任何新文件，则下一轮以生成的文件作为其输入开始。这种情况一直持续到处理器处理完所有新文件。
 * javac 是java语言编程编译器。全称java compiler。javac工具由java语言编写的类和接口的定义，并将它们编译成字节代码的class文件。
 */
@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class RouterProcessor: AbstractProcessor() {

    private var moduleName: String = "Register"
    private var filer: Filer?=null

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }

    override fun init(processingEnvironment: ProcessingEnvironment?) {
        super.init(processingEnvironment)
        filer = processingEnvironment?.filer
        processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "RouterProcessor init")
    }

    /**
     * getSupportedAnnotationTypes() 定义了该处理器在运行时查找的一组注解。
     * 如果目标模块中的任何元素都没有使用该集合中的注解进行注释，则处理器将不会运行。
     */
    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "canonicalName: ${MRoute::class.java.canonicalName}")
        return mutableSetOf(MRoute::class.java.canonicalName)//super.getSupportedAnnotationTypes()
    }

/*    override fun getSupportedAnnotationTypes(): Set<String?>? {
        return setOf<String>(MRoute::class.java.canonicalName)
    }*/

    /**
     * process 是在每个注解处理轮次中调用的核心方法。
     */
    override fun process(annotations : MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "RouterProcessor process--->: $filer")

        // 这行代码的意思检查处理器是否能够找到必要的文件夹并将文件写入其中。
        // 如果可以，处理器将返回提供的使用路径。否则，处理器将中止并从 process 方法返回 false。
        //val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME] ?: return false


        if (!annotations.isNullOrEmpty() && roundEnv != null) {
            //获得被Route注解的类
            var roundElements = roundEnv.getElementsAnnotatedWith(MRoute::class.java)
            generatedClass(roundElements)
            return false
        }
        return false
    }

    fun generatedClass(routeElements: Set<out Element>) {
        var sb = StringBuilder()
        sb.append("package com.example.router;\n")
        sb.append("import android.app.Activity;\n")
        sb.append("import com.example.router.IRouteLoad;\n")
        sb.append("import java.util.HashMap;\n")

        //导入项目中要路由跳转的activity
        for (element in routeElements) {
            var typeElement = element as TypeElement
            sb.append("import ")
            sb.append(typeElement.qualifiedName)
            sb.append(";\n")

            processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "element: $element, qualifiedName: ${element.qualifiedName}")
        }

        //类
        sb.append("public class "+moduleName+"Router implements IRouteLoad{\n")
        sb.append("@Override\n")
        sb.append("public void loadInto(HashMap<String, Class<? extends Activity>> routers) {\n")

        for (element in routeElements) {
            //获得注解
            var route = element.getAnnotation(MRoute::class.java)
            //函数体
            sb.append("routers.put(\"")
            sb.append(route.value)
            sb.append("\",")
            sb.append(element.simpleName)
            sb.append(".class);\n")

            processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "element: $element, simpleName: ${element.simpleName}, path =${route.value}")
        }

        sb.append("}\n")
        sb.append("}\n")

        val clazzStr = sb.toString()
        processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "clazzStr: $clazzStr")
        //创建kotlin文件
        try {
            var sourceFile: JavaFileObject = filer!!.createSourceFile("com.example.routers."+moduleName+"Router")
            //输出字符串
            var outputStream = sourceFile.openOutputStream()
            outputStream.write(sb.toString().toByteArray())
            outputStream.flush()
            outputStream.close()
        } catch (e:Exception) {
            e.printStackTrace()
        }

    }

    private fun kotlinPoetGenerate() {
/*        val clazz = ClassName("com.example.routers", "Register")
        FileSpec.builder("com.example.routers", "Register")
            .addFunction(
                //FunSpec.builder("loadInto").addParameter("routers", HashMap::class.parameterizedBy(String::class, Class::class.parameterizedBy(Activity::class out)))
                    //.build()
            )*/
    }
}

/**
 * package com.example.routers
 *
 * import android.app.Activity
 * import com.example.router.IRouteLoad
 * import com.example.takeout.MainActivity
 *
 * class Register : IRouteLoad{
 *
 *     override fun loadInto(routers: HashMap<String, Class<out Activity>>) {
 *         routers.put("/takeout/MainActivity", MainActivity::class.java)
 *     }
 * }
 */

/**
 * package com.example.routers;
 *
 * import android.app.Activity;
 * import com.example.router.IRouteLoad;
 * import com.example.takeout.MainActivity;
 * import java.util.HashMap;
 *
 * public class Register implements IRouteLoad{
 *     @Override
 *     public void loadInto(HashMap<String, Class<? extends Activity>> routers) {
 *         routers.put("/takeout/MainActivity", MainActivity.class);
 *     }
 * }
 */