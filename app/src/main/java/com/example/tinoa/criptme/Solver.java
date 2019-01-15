package com.example.tinoa.criptme;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Solver {
    private final String sequence = " abcdefghijklmnopqrstuvwxyz";
    private Integer p;
    private Integer g;
    private Integer a;
    private Integer g_a_mod_p;
    private Integer alpha;
    private Integer beta;
    private Random random;
    private List<MyPair<Integer,Integer>> alpha_beta;

    public Solver() {
        // TODO make a randomly chosen between 1<=a<= p-2
        this.a = 1751; // private key Alice
        // TODO make p a randomly large prime (let's say between 2700 and 10000)
        // TODO - aici 2626 e minim deoarece p trebuie sa fie mai mare decat orice combinatie de doua litere
        this.p = 2657;
        // TODO - not entirely sure but. Make generator randomly chosen idk range ( 2 - 9 ?)
        this.g = 2;
        System.out.println("g^a mod p: ");
        this.g_a_mod_p = compute(g, a, p);
        System.out.println("=======================");
        this.random = new Random();
        alpha_beta=new ArrayList<>();
    }

    private Integer compute(Integer a, Integer t, Integer n) {
        System.out.print("compute: ");
        System.out.print(a+" ");
        System.out.print(t+" ");
        System.out.println(n+" ");

        List<MyPair<Integer, Integer>> squaring_modular_values = new ArrayList<>();
        squaring_modular_values.add(new MyPair<>(1, a % n));

        Integer j = 0;
        for (int i = 2; i < t; i = i * 2) {
            j++;
            squaring_modular_values.add(new MyPair<>(i, (a * a) % n));
            a = squaring_modular_values.get(j).getValue();
        }
        System.out.println(squaring_modular_values);

        Long result = Long.valueOf(1);

        for (int i = squaring_modular_values.size() - 1; i >= 0; i--) {
            if (squaring_modular_values.get(i).getKey() <= t) {
                System.out.print(t+" ");
                System.out.println(i);
                t = t - squaring_modular_values.get(i).getKey();
                result *= squaring_modular_values.get(i).getValue();
                result=result%n;
            }
        }

        Long rez = result % n;

        return rez.intValue();
    }

    public String encrypt(String plain) {
        // message in terms of numbers
        // randomly chosen k
        // alpha = generator^k mod p
        // beta = message*((generator^a)^k) mod p
        // message(alpha,beta)
        // encrypted message in terms of letters

        if (plain.length() % 2 != 0) {
            plain += " ";
        }
        List<String> splittedMessage = splitToNChar(plain, 2);

        List<Integer> message = new ArrayList<>();
        for (String s : splittedMessage) {
            message.add(sequence.indexOf(s.charAt(0)) * 100 + sequence.indexOf(s.charAt(1)));
        }
        StringBuilder result= new StringBuilder();

        for(int i=0;i<message.size();i++){
            result.append(cipher(message.get(i)));
            alpha_beta.add(i,new MyPair(alpha,beta));
        }

        return result.toString();
//        return cipher(2035);
    }

    private String cipher(Integer message) {

        //Integer k = random.nextInt(p-2+1)+1;
        Integer k = 1520;
        alpha = compute(g, k, p);
        beta = (message * compute(g_a_mod_p, k, p)) % p;

        // cipher =message(alpha,beta)   ex: cipher =alpha+beta;
        Integer cipherNumber = beta;
        String cipher = "";
        // mod 27 so that is will return the index of the letter
        Integer first = (cipherNumber / 100) % 27;
        Integer second = (cipherNumber % 100) % 27;

        cipher += sequence.charAt(first);
        cipher += sequence.charAt(second);

        return cipher;
    }

    private List<String> splitToNChar(String text, int size) {
        List<String> parts = new ArrayList<>();

        int length = text.length();
        for (int i = 0; i < length; i += size) {
            parts.add(text.substring(i, Math.min(length, i + size)));
        }
        return parts;
    }


    public String decrypt(String cipher) {
        // cipher in terms of numbers
        // known alpha and beta -> message = (alpha^(-a)* beta) mod p
        // encrypted cipher in terms of letters

        if (cipher.length() % 2 != 0) {
            cipher += " ";
        }
        // cipher has upper case letters and we only know about lowercase letters in our sequence
        String lowerCipher=cipher.toLowerCase();

        List<String> splittedMessage = splitToNChar(lowerCipher, 2);

        List<Integer> message = new ArrayList<>();
        // create a number from 2 letters -- chars index in sequence string
        for (String s : splittedMessage) {
            message.add(sequence.indexOf(s.charAt(0)) * 100 + sequence.indexOf(s.charAt(1)));
        }

        String result ="";

        System.out.println(message);
        for(int i=0;i<message.size();i++){
            result+=plain(i);
        }

        return result;
    }

    private String plain(Integer i) {

        Integer beta=alpha_beta.get(i).getValue();
        Integer alpha = alpha_beta.get(i).getKey();

        Integer plainNumber=(beta*compute(alpha,p-1-a,p))%p;
        String plain = "";
        // mod 27 so that is will return the index of the letter
        Integer first = (plainNumber / 100) % 27;
        Integer second = (plainNumber % 100) % 27;

        plain += sequence.charAt(first);
        plain += sequence.charAt(second);

        return plain;
    }
}
