package com.company;

public class CocheCRUDImpl implements CocheCRUD{
    @Override
    public void save() {
        System.out.println("guardado");
    }

    @Override
    public void findall() {
        System.out.println("lista de coches");
    }

    @Override
    public void delete() {
        System.out.println("borrado");
    }
}
