package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class Selector {
    public static String randomElement;
    public static List<String> value;
    public static Random  rand = new Random();
    public String filename;


    public  HashMap selectDictionary( ) throws IOException {
        String regexLineTab = "[\\n\\r]";
        String regexUnderscoreStart="_";
        List<String> valueList = new ArrayList<>();
        HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>();

        FileContentReader fileContentReader=new FileContentReader();
        filename=fileContentReader.requireDictionary();
        fileContentReader.readFileContent(filename);


        String input= fileContentReader.content;
        String[] splitString = input.split(regexLineTab);


        int currentMatch = 0;

        for(int currentString=splitString.length-1;currentString>0;currentString--){

            if (splitString[currentString].contains(regexUnderscoreStart)) {
                currentMatch= currentString;

                List<String> keyList = new ArrayList<>(valueList);

                if(currentString!=splitString.length-1)
                {
                    keyList.removeIf(item -> item == null || "".equals(item));
                    hashMap.put(splitString[currentString],keyList);}

                valueList.clear();

            }
            else{
                if(currentMatch>currentString){
                    valueList.add(splitString[currentString]);

                }
            }

        }
    return  hashMap;
    }
    public  void selectCategory(HashMap<String, List<String>> hashMap) throws IOException {
        System.out.println("Select category");
        int counter=1;
        for(String key:hashMap.keySet()){
            System.out.println(counter+key);
            counter++;
        }
        Hangman hangman= new Hangman();
        BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
        UserInteractionHandler userInteractionHandler= new UserInteractionHandler(buffer);
         int userOption=userInteractionHandler.getIntegerInput();
        switch (userOption){

            case '1' :
                value = hashMap.get("_Football teams");
                randomElement = value.get(rand.nextInt(value.size()));
                hangman.playHangman(randomElement);

                break;
            case '2':
                value = hashMap.get("_Programming principles");
                randomElement = value.get(rand.nextInt(value.size()));
                hangman.playHangman(randomElement);
                break;
            case '3':
                value = hashMap.get("_Books");
                randomElement = value.get(rand.nextInt(value.size()));
                hangman.playHangman(randomElement);
                break;
        }
    }

}
