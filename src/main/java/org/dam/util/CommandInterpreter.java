package org.dam.util;

import org.dam.exception.CommandInterpreterException;
import org.dam.model.Resource;
import org.dam.pojo.ResourcePojo;

import java.util.List;

public class CommandInterpreter {
    public static void read(String sentence) throws CommandInterpreterException {
        String[] sentenceArray = sentence.split(" ", 3);
        String command = sentenceArray[0];
        switch (command) {
            case Commands.LISTAR:
                handleListar();
                break;
            case Commands.HAY:
                handleHay(sentenceArray);
                break;
            case Commands.USAR:
                handleUsar(sentenceArray);
                break;
            case Commands.ADQUIRIR:
                handleAdquirir(sentenceArray);
                break;
            case Commands.SALIR:
                handleSalir();
                break;
            default:
                throw new CommandInterpreterException("La opcion " + command + " no es una opcion valida");
        }
    }

    private static void handleUsar(String[] sentenceArray)  throws CommandInterpreterException {
        if(sentenceArray.length == 3) {
            String resourceName = sentenceArray[2];
            int resourceAmout = Integer.parseInt(sentenceArray[1]);
            ResourcePojo resourcePojo = new ResourcePojo();
            Resource resource = new Resource();
            resource.setAmount(resourceAmout);
            resource.setName(resourceName);
            resourcePojo.updateOrDeleteResource(resource);
        } else {
            throw new CommandInterpreterException("La sentencia usar requiere un numero y un recurso");
        }

    }

    private static void handleListar() {
        ResourcePojo resourcePojo = new ResourcePojo();
        List<Resource> resources = resourcePojo.listResources();
        if(resources.isEmpty()){
            System.err.println("No hay recursos");
        } else {
            for(Resource resource : resources){
                System.out.println(resource);
            }
        }
    }

    private static void handleHay(String[] sentenceArray) throws CommandInterpreterException {
        if (sentenceArray.length == 2) {
            String resourceName = sentenceArray[1];
            ResourcePojo resourcePojo = new ResourcePojo();
            List<Resource> resources = resourcePojo.listResourcesByName(resourceName);
            if(resources.isEmpty()){
                System.err.println("No hay " + resourceName);
            } else {
                for(Resource resource : resources){
                    System.out.println(resource);
                }
            }
        } else {
            throw new CommandInterpreterException("La sentencia hay requiere un valor de busqueda");
        }
    }

    private static void handleAdquirir(String[] sentenceArray) throws CommandInterpreterException {
        if (sentenceArray.length == 3 && isNumeric(sentenceArray[1])) {
            int amount = Integer.parseInt(sentenceArray[1]);
            String resourceName = sentenceArray[2];
            ResourcePojo resourcePojo = new ResourcePojo();
            Resource resource = new Resource();
            resource.setAmount(amount);
            resource.setName(resourceName);
            resourcePojo.createOrUpdateResource(resource);
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
