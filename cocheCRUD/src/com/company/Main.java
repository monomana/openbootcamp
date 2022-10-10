package com.company;

public class Main {

    public static void main(String[] args) {
	CocheCRUD cocheCRUDImpl= new CocheCRUDImpl();
    cocheCRUDImpl.save();
    cocheCRUDImpl.findall();
    cocheCRUDImpl.delete();
    }
}
