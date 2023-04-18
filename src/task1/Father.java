package task1;

public class Father {
    public String name;
    private String idNumber;
    protected String phoneNum;
    int age;

    public Father() {
    }

    public Father(String name, String idNumber, String phoneNum, int age) {
        this.name = name;
        this.idNumber = idNumber;
        this.phoneNum = phoneNum;
        this.age = age;
    }
    public Father(String name, int age, String phoneNum, String idNumber) {
        this.name = name;
        this.idNumber = idNumber;
        this.phoneNum = phoneNum;
        this.age = age;
    }

    int getAge() {
        return age;
    }
    public void showInfo() {
        System.out.println("名字：" + name + " 年龄：" + age +" 身份证号：" + idNumber + " 手机号：" + phoneNum);
    }

    public String getName() {
        return name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setAge(int age) throws defException {
        if(age < 0 || age > 200)
            throw new defException(age);
        this.age = age;
    }
    protected void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    protected void setName(String name) {
        this.name = name;
    }
    protected void printHates() {
        System.out.println("我不喜欢CSGO");
    }
    private void printHabits() {
        System.out.println("我爱JAVA");
    }
}
