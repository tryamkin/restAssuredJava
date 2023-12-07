package org.example;

interface I {
    default void m() {
        System.out.println(1);
    }
}

class A {
    public void m() {
        System.out.println(2);
    }
}

class Main extends A implements I {
    public static void main(String[] args) {
        I i = new Main();
        i.m();
    }
}
