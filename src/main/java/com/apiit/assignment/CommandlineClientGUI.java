package com.apiit.assignment;

import java.io.*;
import java.nio.Buffer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CommandlineClientGUI extends JFrame {
    private NonConcurrentAlgorithms algorithms;
    private JPanel panel;
    private JButton nthPrimeButton;
    private JButton wordCountButton;

    public CommandlineClientGUI() {
        algorithms = new NonConcurrentAlgorithms();

        panel = new JPanel();
        nthPrimeButton = new JButton("nth Prime Number (where n is greater than 4000)");
        wordCountButton = new JButton("Word count");

        panel.add(nthPrimeButton);
        panel.add(wordCountButton);
        add(panel);

        nthPrimeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String n = JOptionPane.showInputDialog(CommandlineClientGUI.this, "Enter n: ");
                long startTime = System.currentTimeMillis();
                long result = algorithms.nthPrime(Integer.parseInt(n));
                long endTime = System.currentTimeMillis();
                JOptionPane.showMessageDialog(CommandlineClientGUI.this,
                    "Nth Prime is " + result + "\nDuration " + (endTime-startTime) + " milliseconds",
                    "Result", JOptionPane.PLAIN_MESSAGE);
            }
        });

        wordCountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String path = JOptionPane.showInputDialog(CommandlineClientGUI.this, "Enter the path of the book text: ");
                try (BufferedReader fileReader = new BufferedReader(new FileReader(new File(path)))) {
                    StringBuilder book = new StringBuilder();
                    fileReader.lines().forEach(line -> book.append(line).append('\n'));
                    long startTime = System.currentTimeMillis();
                    long result = algorithms.wordCount(book);
                    long endTime = System.currentTimeMillis();
                    JOptionPane.showMessageDialog(CommandlineClientGUI.this,
                        "Word count is " + result + "\nDuration " + (endTime-startTime) + " milliseconds",
                        "Result", JOptionPane.PLAIN_MESSAGE);
                } catch (HeadlessException | IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        CommandlineClientGUI frame = new CommandlineClientGUI();
        frame.setTitle("AlgoJam");
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

