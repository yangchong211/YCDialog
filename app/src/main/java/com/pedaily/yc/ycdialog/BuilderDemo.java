package com.pedaily.yc.ycdialog;

/**
 * Created by PC on 2017/10/20.
 * 作者：PC
 */

public class BuilderDemo {

    private final String name;         //必选
    private final String cardID;       //必选
    private final int age;             //可选
    private final String address;      //可选
    private final String phone;        //可选


    public BuilderDemo(UserBuilder userBuilder){
        this.name=userBuilder.name;
        this.cardID=userBuilder.cardID;
        this.age=userBuilder.age;
        this.address=userBuilder.address;
        this.phone=userBuilder.phone;
    }

    public String getName() {
        return name;
    }

    public String getCardID() {
        return cardID;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public static class UserBuilder{
        private final String name;
        private final String cardID;
        private int age;
        private String address;
        private String phone;


        // 线程不安全，不要这样写
        public BuilderDemo build(){
            if(age > 100){
                throw new IllegalStateException("Age out of range");
            }
            return new BuilderDemo(this);
        }

        public UserBuilder(String name,String cardID){
            this.name=name;
            this.cardID=cardID;
        }

        public UserBuilder age(int age){
            this.age=age;
            return this;
        }

        public UserBuilder address(String address){
            this.address=address;
            return this;
        }

        public UserBuilder phone(String phone){
            this.phone=phone;
            return this;
        }

    }

}
