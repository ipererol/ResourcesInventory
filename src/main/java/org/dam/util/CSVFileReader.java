package org.dam.util;

import org.dam.model.Resource;
import org.dam.pojo.ResourcePojo;

import java.io.*;

public class CSVFileReader {
    File csvFile;
    public CSVFileReader(String fileName) throws FileNotFoundException {
        this.csvFile = new File(fileName);
        if (!this.csvFile.exists() || !this.csvFile.isFile()){
            throw new FileNotFoundException("No se ha encontrado el fichero " + fileName);
        }
    }

    public void read() {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(this.csvFile))){
            ResourcePojo resourcePojo = new ResourcePojo();
            bufferedReader.readLine();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Resource resource = new Resource();
                resource.setName(line.split(";")[1].toLowerCase());
                resource.setAmount(Integer.parseInt(line.split(";")[0]));
                resourcePojo.createOrUpdateResource(resource);
            }
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        } catch (NumberFormatException exception) {
            System.err.println("Error while parsing amount in " + csvFile.getName() + " for resource");
        }
    }


}
