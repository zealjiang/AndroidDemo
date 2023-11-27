package com.example.main.bean;

import java.io.Serializable;

public class Person implements Serializable {
    public String name;
    public String sex;

    public Person(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }
}
