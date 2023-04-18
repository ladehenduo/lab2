package task1;

public class defException extends Exception{
// 检查年龄
    private String tip;
    private int age;

    public defException(int age) {
        this.age = age;
        if(age < 0) {
            tip = "年龄不能小于0";
        }
        else if(age > 200) {
            tip = "年龄不能大于200";
        }
    }
}
