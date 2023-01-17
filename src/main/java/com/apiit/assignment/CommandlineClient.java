package com.apiit.assignment;

import java.io.*;
import java.nio.Buffer;

public class CommandlineClient {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        NonConcurrentAlgorithms algorithms = new NonConcurrentAlgorithms();
        String command = null;
        System.out.println("Welcome to AlgoJam");
        System.out.println("Pick a choice below (1/2) to run an algorithm");
        System.out.println("1. nth Prime Number (where n is greater than 4000)");
        System.out.println("2. Word count");
        System.out.print("Enter Choice : ");
        while (!(command = bufferedReader.readLine()).equalsIgnoreCase("exit")) {
            if (command.equalsIgnoreCase("1")) {
                System.out.print("Enter n : ");
                String n = bufferedReader.readLine();
                long startTime = System.currentTimeMillis();
                System.out.printf("Nth Prime is %d%n",algorithms.nthPrime(Integer.parseInt(n)));
                long endTime = System.currentTimeMillis();
                System.out.printf("Duration %d Milliseconds %n",(endTime-startTime));
            }

            if (command.equalsIgnoreCase("2")) {
                System.out.print("Enter the path of the book text : ");
                String path = bufferedReader.readLine();
                BufferedReader fileReader = new BufferedReader(new FileReader(new File(path)));
                StringBuilder book = new StringBuilder();
                fileReader.lines().forEach(line -> book.append(line).append('\n'));
                long startTime = System.currentTimeMillis();
                System.out.printf("Word count is %d%n", algorithms.wordCount(book));
                long endTime = System.currentTimeMillis();
                System.out.printf("Duration %d Milliseconds %n",(endTime-startTime));
            }

            System.out.print("\n\nEnter Choice : ");
        }



    }
}
