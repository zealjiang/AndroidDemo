package com.example.main.proxy;

public class Buyer1 implements ISubject {
    @Override
    public void buy() {
        System.out.println("buyer1 buy");
    }
}
