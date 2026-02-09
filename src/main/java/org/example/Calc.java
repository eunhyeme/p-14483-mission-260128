package org.example;

public class Calc {
    public static Integer run(String exp){
        String[] operand=exp.split("[-+*/]",2);
        String[] operator=exp.split("[0-9]");

        if(operand.length==1)
            return Integer.parseInt(operand[0]);

        for (String i :operator){
            switch (i){
                case "+":
                    return Integer.parseInt(operand[0]) + run(operand[1]);
                case "-":
                    return Integer.parseInt(operand[0]) - run(operand[1]);
                case "*":
                    return Integer.parseInt(operand[0]) * run(operand[1]);
                case "/":
                    return Integer.parseInt(operand[0]) / run(operand[1]);

            }

        }
        return 0;

    }
}
