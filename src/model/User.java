package model;
/**
 * Created by lizmiller on 5/12/16.
 */
public class User {

    private String name;
    private int cardNumber;
    private int age;
    private String loginCredential;
    private boolean isAdmin;
    private boolean isJudge;

    public User(int cardNumber, String name,int age, String loginCredential, boolean isAdmin, boolean isJudge) {
        this.age = age;
        this.cardNumber = cardNumber;
        this.name = name;
        this.loginCredential = loginCredential;
        this.isAdmin = isAdmin;
        this.isJudge = isJudge;
    }
    public boolean getIsJudge()  {
        return isJudge;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public int getAge () {
        return  age;
    }

    public String getName() {
        return name;
    }

    public String getLoginCredential() {
        return loginCredential;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }


    public String toString() {
        return  "["+name + ", " + cardNumber + ", " + age + ", " + loginCredential + ", " + isAdmin+ ", " +isJudge+"]" ;
    }
}