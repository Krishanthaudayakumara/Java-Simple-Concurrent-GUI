package com.apiit.assignment;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class TestClient {
    private static final String SERVER_HOSTNAME = "localhost";
    private static final int SERVER_PORT = 1234;
    private static final int NUM_REQUESTS = 100;
    private static final int start = 4000;
    private static final int THREAD_POOL_SIZE = 10;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        for (int i = start; i < start+NUM_REQUESTS; i++) {
            threadPool.submit(new RequestTask(i));
            Thread.sleep(100); // pause between requests
        }
        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.SECONDS);
    }
    
    private static class RequestTask implements Runnable {
        private final int requestNum;
    
        public RequestTask(int requestNum) {
            this.requestNum = requestNum;
        }
    
        @Override
        public void run() {
            try (Socket socket = new Socket(SERVER_HOSTNAME, SERVER_PORT)) {
                // Send request to server
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println("1 " + requestNum);

                // Read response from server
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String response = in.readLine();
                System.out.println("Response for request " + requestNum + ": " + response);
                TimeUnit.SECONDS.sleep(1000);


            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}    
