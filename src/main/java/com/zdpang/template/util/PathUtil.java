package com.zdpang.template.util;

/**
 * @author ZhangDaPang 285296372@qq.com
 * @date 2018/11/8 16:33
 */
public class PathUtil {

    public static String getJarPath(){
        String osName = System.getProperties().getProperty("os.name");
        if(osName.equals("Linux")){
            return System.getProperty("java.class.path").replace("/env.jar", "");
        }else {
            return System.getProperty("user.dir");
        }
    }

    public static void main(String[] args){
        System.out.println(System.getProperty("user.dir"));
        String osName = System.getProperties().getProperty("os.name");
        if(osName.equals("Linux"))
            System.out.println("running in Linux");
        else
            System.out.println("don't running in Linux");
    }
}
