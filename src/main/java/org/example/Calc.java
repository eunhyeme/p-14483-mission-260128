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
        return calc.intoBracket(exp.replace(" ",""));

    }
    public int findCloseBracket(String exp){
        //열린 브라켓을 포함한 문자열을 넣어주면
        // 그 브라켓의 짝인 닫는 브라켓의 인덱스를 찾아주는 함수
        int openBracketCount=0;
        int rightCloseBracketIndex=0;
        for(char a:exp.toCharArray()){
            //열린 브라켓
            if(a=='('){
                openBracketCount++;
            }
            else if(a==')')
            {
                openBracketCount--;
            }

            if(openBracketCount==0){
                return rightCloseBracketIndex;
            }
            rightCloseBracketIndex++;
        }
        return -10000;
    }

    public int intoBracket(String exp) {
        //괄호열기
        //괄호속 수식 중 가장 가까운 자식 괄호찾기
        //while로 찾다가 없으면 바로 findAdd를 보내는게 아니라 문자열을 새롭게 만들어서 보내기
        String expCopy=exp;
        int startIndex;
        int endIndex;

        int bracketValue;

        int finalTotalValue=-1000;

        StringBuilder newExpSB = new StringBuilder();

        while(true){
            if(expCopy.contains("(")){
                //괄호 찾아서
                startIndex = expCopy.indexOf('(');
                endIndex = findCloseBracket(expCopy.substring(startIndex))+startIndex;

                //괄호 안의 값 연산
                bracketValue=intoBracket(expCopy.substring(startIndex+1,endIndex));

                //연산값 및 괄호 전후 수식 전부 새 수식에 붙이기
                //괄호전
                newExpSB.append(expCopy, 0, startIndex);
                //괄호값
                newExpSB.append(bracketValue);

                // 자식관계의 괄호를 모두 탐색하기 위해 처리 문자열을
                //이전에 처리한 괄호의 뒷부분으로 서브스트링
                expCopy=expCopy.substring(endIndex+1);

            } else {
                //괄호 후 값 붙이고 연산 들어가기
                newExpSB.append(expCopy);
                expCopy = "";
                break;
            }

        }
        finalTotalValue=findAdd(newExpSB.toString());

        return finalTotalValue;

    }

    boolean isUnary(String exp, int index){
        //선택한 연산자가 단항 연산자인지 True False를 뱉는 함수
        // 단항연산자일 조건
        // -맨앞에 있는 연산자
        // -다른 연산자와 연달아 있는 연산자
        if(index<1)
            //맨앞에 있거나 연산자가 존재하지 않을 때도 필터링가능
            return true;
        //연산자가 앞에 붙어있으면
        return exp.charAt(index - 1) < 48;
    }
    public int findAdd(String exp){
        //가장 오른쪽에 있는 연산자 찾기
        int operatorIndex=Math.max(exp.lastIndexOf('+'),exp.lastIndexOf('-'));

        if(operatorIndex>0){ //연산자가 있으면
            if(isUnary(exp,operatorIndex)){//연산자가 단항 연산자인지 검색
                int rightOperand;
                int leftOperand;

                if(operatorIndex==0) {//단항연산자가 맨앞에 있을떄
                    int result=findMul(exp.substring(operatorIndex));
                    return result;

                }
                else {//단항연산자가 중간에 있을때
                    leftOperand= findAdd(exp.substring(0,operatorIndex-1));
                    rightOperand = findMul(exp.substring(operatorIndex));
                    return evaluateExpr(leftOperand,rightOperand,exp.charAt(operatorIndex-1));
                }


            }


            int leftOperand=findAdd(exp.substring(0,operatorIndex));
            int rightOperand=findMul(exp.substring(operatorIndex+1));

            return evaluateExpr(leftOperand,rightOperand,exp.charAt(operatorIndex));
        }
        else return findMul(exp);

    }
    public int findMul(String exp){

        int operatorIndex=Math.max(exp.lastIndexOf('*'),exp.lastIndexOf('/'));

        if(operatorIndex>0) { //연산자가 있으면

            int leftOperand=findMul(exp.substring(0,operatorIndex));
            int rightOperand=(int)Integer.parseInt(exp.substring(operatorIndex+1));

            return evaluateExpr(leftOperand,rightOperand,exp.charAt(operatorIndex));
        }
        else //연산자가 없으면 - 단항만 남았다는 뜻
            return (int)Integer.parseInt(exp);
    }
//
//    public int findMul(String exp){
//        int operatorIndex=Math.max(exp.lastIndexOf('*'),exp.lastIndexOf('/'));
//
//        if(operatorIndex!=-1){ //연산자가 있으면
//            int leftOperand= findMul(exp.substring(0,operatorIndex));
//            int rightOperand=findAdd(exp.substring(operatorIndex+1));
//
//            return evaluateExpr(leftOperand,rightOperand,exp.charAt(operatorIndex));
//        }
//        else return findAdd(exp);
//
//    }
//    public int findAdd(String exp){
//
//        int operatorIndex=Math.max(exp.lastIndexOf('+'),exp.lastIndexOf('-'));
//
//        if(operatorIndex>0) { //+ 또는 - 연산자가 있으면
//
//
//            int leftOperand=findAdd(exp.substring(0,operatorIndex));
//            int rightOperand=(int)Integer.parseInt(exp.substring(operatorIndex+1));
//
//            return evaluateExpr(leftOperand,rightOperand,exp.charAt(operatorIndex));
//        }
//        else //연산자가 없으면 - 단항만 남았다는 뜻
//            return (int)Integer.parseInt(exp);
//    }

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
