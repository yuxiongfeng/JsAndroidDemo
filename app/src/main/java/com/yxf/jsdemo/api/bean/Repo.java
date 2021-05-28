package com.yxf.jsdemo.api.bean;

/**
 * @Description:
 * @Author: yxf
 * @CreateDate: 2021/5/27 15:15
 * @UpdateUser: yxf
 * @UpdateDate: 2021/5/27 15:15
 */
public class Repo {
    private String name;
    private int age;

    public Repo(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
