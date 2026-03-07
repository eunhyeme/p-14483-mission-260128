package org.example;

import com.sun.tools.jconsole.JConsoleContext;

import java.util.Deque;

public class Calc {
    public static Integer run(String exp){

        //test9 해결책
        // 괄호 찾아서 파싱-> 곱셈찾아서 파싱->덧셈 찾아서 파싱
        //1) intoBracket 함수로 계속 넘기기
        //2) findMul 가장뒤에있는 * , /로 수식 파싱해 넘기기 - lastIndexOf()
        //3) findAdd 로 식 넘기기
        Calc calc= new Calc();
        return calc.intoBracket("("+exp+")");

    }


    public int intoBracket(String exp) {
        if (exp.contains("(") && exp.contains(")")) {

            int startIndex = exp.indexOf('(');
            int endIndex = exp.lastIndexOf(')');

            int bracketLeftOperand;
            int bracketRightOperand;
            int bracketValue;

            int total=0;

            bracketValue=intoBracket(exp.substring(startIndex + 1, endIndex));
            total+=bracketValue;

            //괄호 이전 항들 있으면 곱셈함수 넣어주기
            if(startIndex!=0)
            {
                bracketLeftOperand=findMul(exp.substring(startIndex+1,endIndex-2));
                total+=evaluateExpr(bracketLeftOperand,total,exp.charAt(startIndex-1));
            }
            //괄호 이후 항들 있으면 곱셈함수 넣어주기
            if(endIndex!=exp.length()-1){
                bracketRightOperand=findMul(exp.substring(endIndex+2));
                total+=evaluateExpr(total,bracketRightOperand,exp.charAt(endIndex+1));
            }

            return total;
        } else if (exp.contains("(") ^ exp.contains(")")){
            System.exit(-1);
        }
        else //만약 괄호가 더이상 없다면
        {
            return findMul(exp);
            //곱셈함수 불러서 리턴
        }
        return -1;
    }

    public int findMul(String exp){
        int operatorIndex=Math.max(exp.lastIndexOf('*'),exp.lastIndexOf('/'));

        if(operatorIndex!=-1){ //연산자가 있으면
            int leftOperand= findMul(exp.substring(0,operatorIndex));
            int rightOperand=findAdd(exp.substring(operatorIndex+1));

            return evaluateExpr(leftOperand,rightOperand,exp.charAt(operatorIndex));
        }
        else return findAdd(exp);

    }
    public int findAdd(String exp){
        int operatorIndex=Math.max(exp.lastIndexOf('+'),exp.lastIndexOf('-'));

        if(operatorIndex!=-1) { //+ 또는 - 연산자가 있으면

            int leftOperand=findAdd(exp.substring(0,operatorIndex));
            int rightOperand=(int)Integer.parseInt(exp.substring(operatorIndex+1));

            return evaluateExpr(leftOperand,rightOperand,exp.charAt(operatorIndex));
        }
        else //연산자가 없으면 - 단항만 남았다는 뜻
            return (int)Integer.parseInt(exp);
    }

    int evaluateExpr(int num1, int num2, char operator){
        return switch (operator) {
            case '*' -> num1 * num2;
            case '/' -> num1 / num2;
            case '+' -> num1 + num2;
            case '-' -> num1 - num2;
            default -> 0;
        };
    }


}
