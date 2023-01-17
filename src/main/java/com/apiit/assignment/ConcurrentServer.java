package com.apiit.assignment;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ConcurrentServer {
    private static final int PORT = 1234;
    private static final int THREAD_POOL_SIZE = 10;

    public static void main(String[] args) throws IOException {
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server initiated on Port: "+ PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client request accepted");
                threadPool.submit(new RequestHandler(socket));
            }
        }
    }

    private static class RequestHandler implements Runnable {
        private final Socket socket;

        public RequestHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                // Read request from client
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String request = in.readLine();

                // Process request
                NonConcurrentAlgorithms algorithms = new NonConcurrentAlgorithms();
                String response;
                if (request.startsWith("1")) {
                    int n = Integer.parseInt(request.split(" ")[1]);
                    response = Long.toString(algorithms.nthPrime(n));
                    System.out.println(response);
                } else if (request.startsWith("2")) {
                   // System.out.println("req : "+ request);
                    String book = request.split(" ", 2)[1];
                   // System.out.println("Req text: "+book);
                    response = Long.toString(algorithms.wordCount(new StringBuilder(book)));
                    System.out.println(response);
                } else {
                    response = "Invalid request";
                    System.out.println(response);
                }

             
                // Send response to client
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(response);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

