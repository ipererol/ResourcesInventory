package org.dam.util;

import java.io.*;

public class CSVFileReader {
    File csvFile;
    public CSVFileReader(String fileName) throws FileNotFoundException {
        this.csvFile = new File(fileName);
        if (!this.csvFile.exists() || this.csvFile.isFile()){
            throw new FileNotFoundException("No se ha encontrado el fichero " + fileName);
        }
    }

    public void read() {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(this.csvFile))){
            bufferedReader.readLine();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
    }


}
