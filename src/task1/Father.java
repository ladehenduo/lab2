package task1;

public class Father {
    public String name;
    private String idNumber;
    protected String phoneNum;
    int age;

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    protected void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    int getAge() {
        return age;
    }

    public void setAge(int age) throws defException {
        if(age < 0 || age > 200)
            throw new defException(age);
        this.age = age;
    }
}
