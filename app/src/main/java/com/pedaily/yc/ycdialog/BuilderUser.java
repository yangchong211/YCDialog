package com.pedaily.yc.ycdialog;

/**
 * Created by PC on 2017/10/20.
 * 作者：PC
 */

//设置抽象类
public abstract class BuilderUser {

    protected String name;
    protected String cardID;
    protected int age;
    protected String address;

    public BuilderUser() {

    }

    //设置名字
    public void setName(String name) {
        this.name = name;
    }

    //抽象方法
    public abstract void setCardID();

    //设置年龄
    public void setAge(int age) {
        this.age = age;
    }

    //设置地址
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "BuilderUser{" +
                "name='" + name + '\'' +
                ", cardID='" + cardID + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }

    /**
     * 具体的Product角色 UserCard
     */
    public class UserCard extends BuilderUser {

        public UserCard() {}

        @Override
        public void setCardID() {
            cardID="13667225184";             //设置默认ID
        }
    }

    /**
     * 抽象Builder类
     */
    public abstract class Builder {
        public abstract void buildName(String name);
        public abstract void buildCardID();
        public abstract void buildAge(int age);
        public abstract void buildAddress(String address);
        public abstract BuilderUser create();
    }


    /**
     * 具体的Builder类
     */
    public class AccountBuilder extends Builder{

        private BuilderUser user = new UserCard();

        @Override
        public void buildName(String name) {
            user.setName(name);
        }

        @Override
        public void buildCardID() {
            user.setCardID();
        }

        @Override
        public void buildAge(int age) {
            user.setAge(age);
        }

        @Override
        public void buildAddress(String address) {
            user.setAddress(address);
        }

        @Override
        public BuilderUser create() {
            return user;
        }
    }

    /**
     * Director角色，负责构造User
     */
    public class Director {
        Builder mBuilder =null;

        public Director(Builder builder){
            this.mBuilder =builder;
        }

        public void construct(String name,int age ,String address){
            mBuilder.buildName(name);
            mBuilder.buildCardID();
            mBuilder.buildAge(age);
            mBuilder.buildAddress(address);
        }
    }


}
