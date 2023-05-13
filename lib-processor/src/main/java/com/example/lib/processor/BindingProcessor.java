package com.example.lib.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

//@AutoService(Processor.class)
public class BindingProcessor extends AbstractProcessor {
    private Filer filter;
    private Messager mMessager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filter = processingEnv.getFiler();
        // 因为运行在 javac 执行过程中，打印必须使用 messager，而不能使用 System.out 或 Log
        mMessager = processingEnv.getMessager();
        mMessager.printMessage(Diagnostic.Kind.NOTE,"---BindingProcessor  init---");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        mMessager.printMessage(Diagnostic.Kind.NOTE,"------------------process------------------");

        MethodSpec.Builder constructorBuilder;
        ClassName className;
        for (Element element:roundEnv.getRootElements()) {
            String packageStr = element.getEnclosingElement().toString();
            String classStr = element.getSimpleName().toString();
            mMessager.printMessage(Diagnostic.Kind.NOTE,"packageStr ="+packageStr+"  classStr ="+classStr);

            constructorBuilder = null;
            className = null;

            //标记类中是否有@BindView注解的修饰字段，如果没有就没有必要生成类了
            boolean hasBinding = false;
            for (Element enclosedElement: element.getEnclosedElements()) {
                if(enclosedElement.getKind() == ElementKind.FIELD){
                    BindView bindView = enclosedElement.getAnnotation(BindView.class);
                    if(bindView != null){
                        hasBinding = true;

                        if(constructorBuilder == null){
                            //System.out.println("packageStr ="+packageStr+"  classStr ="+classStr);
                            //要生成的java文件的类名：com.example.myapplication.apt.APTActivityBinding
                            className = ClassName.get(packageStr,classStr+"Binding");
                            //生成构造器 public AptActivityBinding()
                            constructorBuilder = MethodSpec.constructorBuilder()
                                    .addModifiers(Modifier.PUBLIC)
                                    .addParameter(ClassName.get(packageStr,classStr),"activity");

                            mMessager.printMessage(Diagnostic.Kind.NOTE,"---className ="+className);
                        }


                        //在构造器内添加代码
                        //activity.textView1 = activity.findViewById(xxxx);
                        constructorBuilder.addStatement("activity.$N = activity.findViewById($L)",
                                enclosedElement.getSimpleName(),bindView.value());

                        mMessager.printMessage(Diagnostic.Kind.NOTE,"className ="+className+"  field="+enclosedElement.getSimpleName()+" value="+bindView.value());
                    }
                }
            }

            if(hasBinding){
                //生成com.example.myapplication.apt.AptActivityBinding类
                TypeSpec buildClass = TypeSpec.classBuilder(className)
                        .addModifiers(Modifier.PUBLIC)
                        .addMethod(constructorBuilder.build())
                        .build();


                try {
                    // 最后要将内容写入到 java 文件中，这里必须使用 processingEnv 中获取的 Filer 对象
                    // 它会自动处理路径问题，我们只需要定义好包名类名和文件内容即可。
                    //生成AptActivityBinding.java文件
                    JavaFile.builder(packageStr,buildClass).build().writeTo(filter);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        // 返回值表示处理了 set 参数中包含的所有注解，不会再将这些注解移交给编译流程中的
        // 其他 Annotation Processor。一般都不会有多个 Annotation Processor，一般都写 true。
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(BindView.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }
}