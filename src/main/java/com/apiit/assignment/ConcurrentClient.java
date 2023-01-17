package com.apiit.assignment;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ConcurrentClient extends JFrame {
    private static final int PORT = 1234;
    private JPanel panel;
    private JButton nthPrimeButton;
    private JButton wordCountButton;
    private String serverHostname;
    private int serverPort;

    public ConcurrentClient(String serverHostname, int serverPort) {
        this.serverHostname = serverHostname;
        this.serverPort = serverPort;

        panel = new JPanel();
        nthPrimeButton = new JButton("nth Prime Number (where n is greater than 4000)");
        wordCountButton = new JButton("Word count");

        panel.add(nthPrimeButton);
        panel.add(wordCountButton);
        add(panel);

        nthPrimeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String n = JOptionPane.showInputDialog(ConcurrentClient.this, "Enter n: ");
                long startTime = System.currentTimeMillis();
                String response = sendRequest("1 " + n);
                long endTime = System.currentTimeMillis();
                JOptionPane.showMessageDialog(ConcurrentClient.this,
                    "Nth Prime is " + response + "\nDuration " + (endTime-startTime) + " milliseconds",
                    "Result", JOptionPane.PLAIN_MESSAGE);
            }
        });

        wordCountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String path = JOptionPane.showInputDialog(ConcurrentClient.this, "Enter the path of the book text: ");
                try (BufferedReader fileReader = new BufferedReader(new FileReader(new File(path)))) {
                    StringBuilder book = new StringBuilder();
                    fileReader.lines().forEach(line -> book.append(line).append('\n'));
                    long startTime = System.currentTimeMillis();
                    String content = book.toString();
                    content = content.replaceAll("\n", "");
                    // System.out.println("Content: "+content);
                   // if(content.length() > 0)
                    String response = content.length() > 0 ? sendRequest("2 " + content) : "Error on content";
                    long endTime = System.currentTimeMillis();
            
                    JOptionPane.showMessageDialog(ConcurrentClient.this,
                        "Word count is " + response + "\nDuration " + (endTime-startTime) + " milliseconds",
                        "Result", JOptionPane.PLAIN_MESSAGE);
                } catch (HeadlessException | IOException e1) {
                    // TODO Auto-generated catch block
                    System.out.println("Exeption occured: \n");
                    e1.printStackTrace();
                }
            }
        });
    }

    private String sendRequest(String request) {
        try (Socket socket = new Socket(serverHostname, serverPort)) {
            // Send request to server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(request);

            // Read response from server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return in.readLine();
        } catch (IOException e) {
            System.out.println("exeption in Send req");
            e.printStackTrace();
            return "Error";
        }
    }

    public static void main(String[] args) {
        ConcurrentClient frame = new ConcurrentClient("localhost", PORT);
        frame.setTitle("AlgoJam");
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
