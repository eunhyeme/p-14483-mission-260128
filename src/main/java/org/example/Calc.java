package org.example;

public class Calc {
    public static Integer run(String exp){
        String[] operand=exp.split("[-+*/]");
        String[] operator=exp.split("[0-9]");
        for (String i :operator){
            switch (i){
                case "+":
                    return Integer.parseInt(operand[0]) + Integer.parseInt(operand[1]);
                case "-":
                    return Integer.parseInt(operand[0]) - Integer.parseInt(operand[1]);
                case "*":
                    return Integer.parseInt(operand[0]) * Integer.parseInt(operand[1]);
                case "/":
                    return Integer.parseInt(operand[0]) / Integer.parseInt(operand[1]);

            }

        }
        return 0;

    }
}
