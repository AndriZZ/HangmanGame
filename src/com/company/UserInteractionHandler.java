package com.company;


import java.io.BufferedReader;
import java.io.IOException;


public class UserInteractionHandler
{
    private BufferedReader buffer;
    public UserInteractionHandler(BufferedReader buffer){
        this.buffer=buffer;
    }

    public Integer getIntegerInput() throws IOException {
        Integer line= buffer.read();
        return line;
    }
    public String readFilenameInput() throws IOException {

        String line=buffer.readLine();
        return line;
    }
    public void outputMessage(String text){
        System.out.print(text);
    }


}
