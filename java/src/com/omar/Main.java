package com.omar;

import com.omar.controller.Menu;

public class Main {

    public static void main(String[] args) {
        mostrarBienvenida();
        Menu menu = new Menu();
        menu.start();
        System.out.println("Gracias por usar el CRM. Hasta pronto!");
    }

    private static void mostrarBienvenida() {
        System.out.println("+------------------------------------------+");
        System.out.println("|                                          |");
        System.out.println("|          CRM - INSTITUTO XTART           |");
        System.out.println("|       Gestion de alumnos y cursos        |");
        System.out.println("|                                          |");
        System.out.println("+------------------------------------------+");
    }
}