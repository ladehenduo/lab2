package task1;

import java.lang.reflect.*;

public class Demo {
    public static void main(String[] agrs) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class fClass = null;
        Class cClass = null;
        Method[] fmethods = null;
        Method[] cmethods = null;
        Field[] ffield = null;
        Field[] cfield = null;
        Field[] dffield = null;
        Field[] dcfield = null;
        try {
            fClass = Class.forName("task1.Father");
            cClass = Class.forName("task1.Son");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        fmethods = fClass.getDeclaredMethods(); //getDeclaredMethods 获取在当前类里面声明的方法，可以获取 private 方法
        cmethods = cClass.getMethods();     //getMethods 获得当前类的所有方法（不包括private方法）包括继承的方法
        ffield = fClass.getFields();
        cfield = cClass.getFields();
        dffield = fClass.getDeclaredFields();
        dcfield = cClass.getDeclaredFields();
        System.out.println("能被映射的父类方法：");
        for(Method m : fmethods) {
            System.out.print("所在类：" + m.getDeclaringClass() + "   方法名：" + m.getName() + "  参数类型：");
            Type[] types = m.getParameterTypes();
            for(Type p : types){
                System.out.print(p.getTypeName() + ' ');
            }
            if(types.length == 0) System.out.print("null");
            System.out.print("   返回值类型：" + m.getReturnType().getName() + "   异常类型：");
            types = m.getExceptionTypes();
            for(Type p : types){
                System.out.print(p.getTypeName() + ' ');
            }
            if(types.length == 0) System.out.print("null");
            System.out.println();
        }
        for(Method m : cmethods) {
            System.out.print("所在类：" + m.getDeclaringClass() + "   方法名：" + m.getName() + "  参数类型：");
            Type[] types = m.getParameterTypes();
            for(Type p : types){
                System.out.print(p.getTypeName() + ' ');
            }
            if(types.length == 0) System.out.print("null");
            System.out.print("   返回值类型：" + m.getReturnType().getName() + "   异常类型：");
            types = m.getExceptionTypes();
            for(Type p : types){
                System.out.print(p.getTypeName() + ' ');
            }
            if(types.length == 0) System.out.print("null");
            System.out.println();
        }
        for(Field f : ffield){
            System.out.println("属性名：" + f.getName() + "类型：" + f.getType().getName() + "  作用域：" + f.getModifiers());
        }
        for(Field f : dffield){
            System.out.println("属性名：" + f.getName() + "类型：" + f.getType().getName() + "  作用域：" + f.getModifiers());
        }
        // 无参构造
        Son son = (Son) cClass.newInstance();
        son.showMessage2();
        // 有参构造
        Constructor c = cClass.getConstructor(String.class, int.class);
        Son son1 = (Son) c.newInstance("wang", 20);
        son1.showMessage2();
    }
}
