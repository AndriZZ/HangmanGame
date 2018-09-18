package com.company;

public class Alternator {

    public String[] turnFileIntoArrayOfStrings(String content, int lines) {
        String[] array = new String[lines];
        int index = 0;
        int tempInt = 0;
        int startIndex = 0;
        int lastIndex = content.length() - 1;
        while (true) {
            if (content.charAt(index) == '\n') {
                tempInt++;
                String temp = new String();
                for (int i = 0; i < index - startIndex; i++) {
                    temp += content.charAt(startIndex + i);
                }
                startIndex = index;
                array[tempInt - 1] = temp;
            }
            if (index == lastIndex) {
                tempInt++;
                String temp = new String();
                for (int i = 0; i < index - startIndex + 1; i++) {
                    temp += content.charAt(startIndex + i);
                }
                array[tempInt - 1] = temp;
                break;
            }
            index++;
        }
        return array;
    }
}
