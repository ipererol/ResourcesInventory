package org.dam;


import org.dam.exception.CommandInterpreterException;
import org.dam.util.CSVFileReader;
import org.dam.util.CommandInterpreter;
import org.dam.util.HibernateUtil;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CSVFileReader csvFileReader = null;
        try {
            csvFileReader = new CSVFileReader("compra.csv");
            csvFileReader.read();

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
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }

    }
}