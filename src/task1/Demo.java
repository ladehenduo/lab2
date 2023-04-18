package task1;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Demo {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class person = Class.forName("task1.Father");
        Method[] method =  person.getDeclaredMethods();
        Field[] fields = person.getFields();
        System.out.println("方法数：" + method.length);
        for(int i = 0; i < method.length; i++){
            System.out.println(method[i].getName());
        }
        System.out.println("属性数：" + fields.length);
        for(int i = 0; i < fields.length; i++) {
            System.out.println(fields[i].getName());
        }
        Object[] object = new Object[4];
        object[0] = "xx";
        object[1] = "45612333";
        object[2] = "131654132";
        object[3] = 18;
        Father p1 = (Father) person.newInstance();
        Father p2 = (Father) person.newInstance();
//        person.newInstance(new Object()[]);
    }

}
