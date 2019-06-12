package com.mobei.utils;

import javax.tools.*;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class ProxyUtil {
    //制表符:格式化代码,不用也没事
    private static final String TAB = "\t";
    //换行符
    private static final String LINE = "\n";

    /**
     * 动态生成代理对象
     * @param targetImpl 目标对象
     * @return 动态代理对象
     */
    public static Object newInstance(Object targetImpl) {
        Object proxy = null;
        /***************************生成.java开始*****************************/
        //拿到目标类的接口
        Class<?>[] interfaces = targetImpl.getClass().getInterfaces();
        String targetInterfaceFullName = interfaces[0].getName();
        String targetInterfaceSimpleName = interfaces[0].getSimpleName();
        //类所在的包路径,这里随意,主要影响后面输出到本地磁盘需要写目录
        String packageStr = "package com.mobei.impl;" + LINE;
        //目标类实现了接口,需要导入该接口所在的包
        String importStr = "import " + targetInterfaceFullName + ";" + LINE;
        //类开始行:类的结构大体是固定的,代理类类名可以自定义,这里使用$Proxy
        String classStartStr = "public class $Proxy implements " + targetInterfaceSimpleName + " {" + LINE;
        //类成员变量
        String fieldStr = TAB + "private " + targetInterfaceSimpleName + " target;" + LINE;
        //构造函数起始行:
        String constructorStartStr = TAB + "public $Proxy(" + targetInterfaceSimpleName + " target) {" + LINE;
        //构造函数内容体:
        String constructorContentStr = TAB + TAB + "this.target = target;" + LINE;
        //构造函数结束:
        String constructorEndStr = TAB + "}" + LINE;
        //重写的方法:接口中可能会有很多的方法,我们需要拿到接口中声明的方法
        Method[] methods = interfaces[0].getMethods();
        //方法字符串
        String methodContent = "";
        for (Method method : methods) {
            //方法中的形参列表(String p1, String p2)
            String paramsWithType = "";
            //调用目标对象方法时候传递的参数(p1,p2)
            String paramsTarget = "";
            //拿到方法返回值类型,需要据此判断方法执行完后是否需要加return
            Class<?> returnType = method.getReturnType();
            String returnTypeSimpleName = returnType.getSimpleName();
            //拿到方法的形参列表
            Class<?>[] parameterTypes = method.getParameterTypes();
            //封装方法第一行
            methodContent += TAB + "public " + returnTypeSimpleName + " " + method.getName() + "(";
            for (int i = 0; i < parameterTypes.length; i++) {
                //封装形参列表
                paramsWithType += parameterTypes[i].getSimpleName() + " p" + i + ", ";
                //同时把需要传递给目标对象的参数也封装
                paramsTarget += "p" + i + ", ";
            }
            //处理最后的,号和空格
            if (paramsWithType.length() > 0) {//需要判断,如果没有参数就不要处理
                paramsWithType = paramsWithType.substring(0, paramsWithType.lastIndexOf(","));
                paramsTarget = paramsTarget.substring(0, paramsTarget.lastIndexOf(","));
            }
            //将形参封装
            methodContent += paramsWithType  + ") {" + LINE;
            //开始方法体:代理类增强的内容,实际不应该写死,这里作为入门先写死
            methodContent += TAB + TAB + "System.out.println(\"----log----\");" + LINE;
            if ("void".equals(returnTypeSimpleName)) {//void不需要拼return
                methodContent += TAB + TAB + "target." + method.getName() + "(" + paramsTarget + ");" + LINE;
            } else {//有返回值类型需要拼装return
                methodContent += TAB + TAB + "return target." + method.getName() + "(" + paramsTarget + ");" + LINE;
            }
            methodContent += TAB + "}" + LINE;
        }
        //最终完整的.java内容
        String javaStr = packageStr +
                importStr +
                classStartStr +
                fieldStr +
                constructorStartStr +
                constructorContentStr +
                constructorEndStr +
                methodContent +
                "}";
        //写入本地磁盘
        try {
            //这里需要创建目录,否则会报错,也可以通过代码的方式去创建,这里不作为重点考虑,我们手动创建
            File file = new File("D:\\com\\mobei\\impl\\$Proxy.java");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            fw.write(javaStr);
            fw.flush();
            fw.close();
            /***************************生成.java结束*****************************/

            /***************************开始编译*****************************/
            JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager standardFileManager = javaCompiler.getStandardFileManager(null, null, null);
            Iterable<? extends JavaFileObject> units = standardFileManager.getJavaFileObjects(file);
            JavaCompiler.CompilationTask t = javaCompiler.getTask(null, standardFileManager, null, null, null, units);
            t.call();
            standardFileManager.close();

            //只能写$Proxy.java所在目录的根地址:因为$Proxy.java这个资源是带了包路径的,这个路径包含了资源所在的目录
            URL[] urls = new URL[]{new URL("file:D:\\\\")};
            URLClassLoader urlClassLoader = new URLClassLoader(urls);
            Class<?> clazz = urlClassLoader.loadClass("com.mobei.impl.$Proxy");
            /***************************编译结束*****************************/
            
            /***************************生成代理对象返回*****************************/
            Constructor constructor = clazz.getConstructor(interfaces[0]);
            proxy = constructor.newInstance(targetImpl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return proxy;
    }

}
