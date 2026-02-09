package org.example;

public class Calc {
    public static Integer run(String exp){
        int len = exp.length();
        char p;
        String[] operand=exp.split("[+]");

        return Integer.parseInt(operand[0])+Integer.parseInt(operand[1]);

    }
}
