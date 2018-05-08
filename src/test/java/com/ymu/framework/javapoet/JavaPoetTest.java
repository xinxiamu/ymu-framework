package com.ymu.framework.javapoet;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.junit.Test;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;

/**
 * https://github.com/square/javapoet
 *
 * https://blog.csdn.net/xuguobiao/article/details/72775730
 *
 * https://cloud.tencent.com/developer/article/1006210
 */
public class JavaPoetTest {

    @Test
    public void helloWorld() throws Exception {
        //方法
        MethodSpec main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC) //添加修饰符
                .returns(void.class) //返回类型
                .addParameter(String[].class, "args") //参数类型
                .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!") //方法体内声明
                .build();

        //类
        TypeSpec helloWorld = TypeSpec.classBuilder("JavaPoetTest") //类名
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main) //添加方法
                .build();

        //文件
        JavaFile javaFile = JavaFile.builder("com.example.helloworld", helloWorld)
                .build();

        javaFile.writeTo(System.out); //输出到控制台
        javaFile.writeTo(new File("src/main/java"));//文件输出路径
    }

    @Test
    public void controlFlowFor() throws IOException {
        MethodSpec getUserByName = MethodSpec.methodBuilder("getUserByName")
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class)
                .addParameter(String.class,"userName")
                .addStatement("int total = 0")
                .beginControlFlow("for (int i = 0; i < 10; i++)")
                .addStatement("total += i")
                .endControlFlow()
                .build();

        //类
        TypeSpec controlFlow = TypeSpec.classBuilder("ControlFlow") //类名
                .addModifiers(Modifier.PUBLIC)
                .addMethod(getUserByName) //添加方法
                .addMethod(computeRange("getUserById",1,20,"+"))
                .build();

        JavaFile javaFile = JavaFile.builder("com.example", controlFlow)
                .build();
        javaFile.writeTo(System.out);
    }

    /**
     * 创建一个方法。包括一个for循环体。
     * @param name 方法名称
     * @param from 循环起始下标
     * @param to 结束下标
     * @param op 操作符
     * @return
     */
    private MethodSpec computeRange(String name, int from, int to, String op) {
        return MethodSpec.methodBuilder(name)
                .addModifiers(Modifier.PUBLIC)
                .returns(int.class)
                .addStatement("int result = 1")
                .beginControlFlow("for (int i = " + from + "; i < " + to + "; i++)")
                .addStatement("result = result " + op + " i")
                .endControlFlow()
                .addStatement("return result")
                .build();
    }

}
