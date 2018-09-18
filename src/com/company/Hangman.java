package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Hangman {
    private int lifes=3;
    private int score=0;

    public void playHangman(String word) {

        char[] fillerArray = new char[word.length()];
        int counter = 0;
        while (counter < word.length()) {
            fillerArray[counter] = '_';
            if (word.charAt(counter) == ' ') {
                fillerArray[counter] = ' ';
            }
            counter++;
        }
         if (lifes>0) {
         for (int j = 0; j < fillerArray.length; j++) {
        System.out.print(fillerArray[j] + " ");
         }
        System.out.println("    Lives remaining=" + lifes);
                     }
        ArrayList<Character> wordToGuess=new ArrayList<Character>();
        Scanner scanner = new Scanner(System.in); //reading characters
        while (lifes > 0) {
            char inputCharacter = scanner.next().charAt(0);
            if (wordToGuess.contains(inputCharacter)) {
                System.out.println("Already entered");
                continue;
            }
            wordToGuess.add(inputCharacter);
            if (word.contains(inputCharacter + "")) {
                for (int y = 0; y < word.length(); y++) {
                    if (word.charAt(y) == inputCharacter) {
                        fillerArray[y] = inputCharacter;
                    }
                }
            } else {
                lifes--;
            }
            if (word.equals(String.valueOf(fillerArray))) {
                for (int j = 0; j < fillerArray.length; j++) {
                    System.out.print(fillerArray[j] + " ");
                }
                System.out.println("You won!");
                score = score + 1;
                System.out.println("Score: " + score);
                Selector.randomElement = Selector.value.get(Selector.rand.nextInt(Selector.value.size()));
               playHangman(Selector.randomElement);


            }
            for (int j = 0; j < fillerArray.length; j++) {
                System.out.print(fillerArray[j] + " ");
            }
            System.out.println("    Lives remaining=" + lifes);

        }

        if(lifes==0){
            System.out.println("Game over");
        }
    }
}
