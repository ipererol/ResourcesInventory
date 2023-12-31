package org.dam;


import org.dam.exception.CommandInterpreterException;
import org.dam.util.CommandInterpreter;
import org.dam.util.HibernateUtil;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String sentence = "";
        do {
            try {
                sentence = scanner.nextLine();
                sentence = sentence.strip();
                CommandInterpreter.read(sentence);
            } catch (CommandInterpreterException exception) {
                System.err.println(exception.getMessage());
            }
        } while(!sentence.equalsIgnoreCase("salir"));
        HibernateUtil.shutdown();
    }
}