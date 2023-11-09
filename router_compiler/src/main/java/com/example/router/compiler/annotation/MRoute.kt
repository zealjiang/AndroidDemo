package com.example.router.compiler.annotation


/**
 * @Target 表示该注解可以用于什么地方。可能的 ElementType 包括：CONSTRUCTOR：构造器的声明，FIELD：域声明（包括enum实例），LOCAL_VARIABLE：局部变量声明 ，METHOD：方法声明 ，PACKAGE：包声明 ，PARAMETER：参数声明，TYPE：类、接口（包括注解类型）、或者enum声明
 * @Retention 表示需要在什么级别保存该注解信息。可选的 RetentionPolicy 参数包括：SOURCE：注解将被编译器丢弃，CLASS：注解在class文件中可用，但会被VM丢弃，RUNTIME：VM将在运行期也保留注解，因此可以通过反射机制读取注解的信息
 * @Target使用CLASS代表我们将只在类上使用Router，@Retention使用SOURCE代表 Router 只需要在源代码编译阶段存在
 */
/*
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class MRoute(val path: String)*/
