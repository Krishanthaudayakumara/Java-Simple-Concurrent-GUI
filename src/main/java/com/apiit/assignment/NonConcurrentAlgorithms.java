package com.apiit.assignment;

public class NonConcurrentAlgorithms {

    public long wordCount(StringBuilder text){
        long wordCount = 0;
        String[] words = text.toString().split(" ");
        for (String word : words){
            if (word.trim().length()>0)
            {
                wordCount++;
            }
        }
        return wordCount;
    }

    public long nthPrime(int n) {
        long num = 1;
        for (int i = 1; i<=n; i++) {
            while (!isPrime(num)) {
                num++;
            }
            if (i == n) {
                return num;
            }else{
                num++;
            }
        }
        return num;
    }

    private boolean isPrime(long num) {
        if (num == 1) return true;
        for(long i=num-1; i>1; i--) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}
