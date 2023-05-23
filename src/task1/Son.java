package task1;

public class Son extends Father{
    public Son(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Son() {
    }

    private void showMessage() {
        System.out.println("姓名：" +  this.name);
        System.out.println("年龄：" + this.age);
        System.out.println("手机号：" + this.phoneNum);
        System.out.println("身份证号：" + this.getIdNumber());
    }

    public void showMessage2() {
        System.out.println("姓名：" +  this.name);
        System.out.println("年龄：" + this.age);
        System.out.println("手机号：" + this.phoneNum);
        System.out.println("身份证号：" + this.getIdNumber());
    }
}
