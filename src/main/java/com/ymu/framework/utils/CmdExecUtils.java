package com.ymu.framework.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 功能简述:<br>
 *      执行系统命令工具类。
 * @author zmt
 * @create 2018-03-05 下午3:34
 * @updateTime
 * @since 1.0.0
 */
public final class CmdExecUtils {

    private CmdExecUtils() {
    }

   /* public static void main(String[] args) {
        //第一个参数是可执行文件
//        boolean flg = execCommond("/home/mutian/dev/tools/wkhtmltox/bin/wkhtmltopdf","https://www.jianshu.com/p/559c594678b6", "/home/mutian/cba.pdf");
//        System.out.println("执行结果：" + flg);

//        boolean flg = execCommond("ping www.baidu.com");

        boolean flg = execCommond("/home/mutian/dev/tools/wkhtmltox/bin/wkhtmltopdf","/home/mutian/html/Purchase-Order.html" ,"/home/mutian/Desktop/adf.pdf");
        System.out.println("执行结果：" + flg);
    }*/

    /**
     * 启动进程执行命令。
     * @param args
     * @return
     */
    public static boolean execCommond(String... args) {
        boolean flg = true;
//        String cmd = "ping www.baidu.com";
//        String[] cmd=new String[3];
//        cmd[0] = "/home/mutian/dev/tools/wkhtmltox/bin/wkhtmltopdf"; //可执行文件
//        cmd[1] = "https://www.jianshu.com/p/559c594678b6";
//        cmd[2] = "/home/mutian/abc.pdf";
        Runtime run = Runtime.getRuntime();//返回与当前 Java 应用程序相关的运行时对象
        try {
            Process p;
            if (args != null && args.length == 1) {
                p = run.exec(args[0]);// 启动另一个进程来执行命令
            } else {
                p = run.exec(args);// 启动另一个进程来执行命令
            }

            BufferedInputStream in = new BufferedInputStream(p.getInputStream());
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
            String lineStr;
            while ((lineStr = inBr.readLine()) != null)
                //获得命令执行后在控制台的输出信息
                System.out.println(lineStr);// 打印输出信息
            //检查命令是否执行失败。
            if (p.waitFor() != 0) {
                if (p.exitValue() == 1) {//p.exitValue()==0表示正常结束，1：非正常结束
                    System.err.println("命令执行失败!");
                    flg = false;
                }
            }
            inBr.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            flg = false;
        }
        return flg;
    }

}
