package org.dam.util;

import org.dam.exception.CommandInterpreterException;

import java.util.Arrays;

public class CommandInterpreter {
    public static void read(String sentence) throws CommandInterpreterException {
        String[] sentenceArray = sentence.split(" ", 3);
        String command = sentenceArray[0];
        int amount = 0;
        String resourceName = "";
        switch (command) {
            case Commands.LISTAR:
                // Handle listar command
                handleListar();
                break;
            case Commands.HAY:
                handleHay(sentenceArray);
                break;
            case Commands.USAR:
            case Commands.ADQUIRIR:
                handleUsarAdquirir(sentenceArray);
                break;
            case Commands.SALIR:
                handleSalir();
                break;
            default:
                throw new CommandInterpreterException("La opcion " + command + " no es una opcion valida");
        }
    }

    private static void handleListar() {
        // Implementation for listar command
    }

    private static void handleHay(String[] sentenceArray) throws CommandInterpreterException {
        if (sentenceArray.length == 2) {
            String resourceName = sentenceArray[1];
            // Implementation for hay command with resourceName
        } else {
            throw new CommandInterpreterException("La sentencia hay requiere un valor de busqueda");
        }
    }

    private static void handleUsarAdquirir(String[] sentenceArray) throws CommandInterpreterException {
        if (sentenceArray.length == 3 && isNumeric(sentenceArray[1])) {
            int amount = Integer.parseInt(sentenceArray[1]);
            String resourceName = sentenceArray[2];
            // Implementation for usar/adquirir command with amount and resourceName
        } else {
            throw new CommandInterpreterException("La sentencia usar requiere un numero y un recurso");
        }
    }

    private static void handleSalir() {
        System.out.println("Bye!");
    }

    private static boolean isNumeric(String value) {
        if (value == null) {
            return false;
        }
        try {
            Integer i = Integer.parseInt(value);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
