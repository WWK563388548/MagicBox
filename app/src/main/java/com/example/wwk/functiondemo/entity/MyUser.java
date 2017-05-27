package com.example.wwk.functiondemo.entity;

import cn.bmob.v3.BmobUser;

/**
 * Created by wwk on 17/5/23.
 * User's properties
 */

public class MyUser extends BmobUser{

    private int age;
    private boolean gender;
    private String introduction;
    
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
