
import java.util.Scanner;

import java.util.Scanner;

class Stack {
    int top = -1;
    int size = 100;
    boolean isEmpty() {
        return top == -1;
    }
}

class Operator extends Stack {
    char[] stack = new char[size];
    void push(char op) {
        stack[++top] = op;
    }
    char pop() {
        return stack[top--];
    }
}

class Operand extends Stack {
    double[] stack = new double[size];
    void push(double num) {
        stack[++top] = num;
    }
    double pop() {
        return stack[top--];
    }
}
public class Calculator {
    static Scanner s = new Scanner(System.in);
    Operator operator = new Operator(); //연산자 스택
    Operand operand = new Operand(); //피연산자 스택
    public static void main(String[] args) throws Exception{
        Calculator calculator = new Calculator();
        System.out.println("=======MyCalculator=======");
        String infix; //중위표기 수식을 입력 받음
        String postfix; //후위표기식 저장
        double result;

        while (true) {
            System.out.print("Infix로 수식을 입력하시오. \n>>> ");
            infix = s.nextLine();
            infix = calculator.Preprocessor(infix); //입력한 수식에 공백이 있는 경우 제거
            System.out.println(infix);
            postfix = calculator.Transform(infix);
            System.out.println(postfix);
            result = calculator.calculate(postfix);
            System.out.println(result);

        }
    }

    private String Preprocessor(String infix) { //입력한 수식에 공백이 있는 경우 제거
        String[] splitExpression = infix.split("");
        StringBuilder fitExperssion = new StringBuilder();
        for (int i=0; i<splitExpression.length; i++) {
            String value = splitExpression[i];
            System.out.println("(화긴 :" + splitExpression[i] + ")");
            if (!(value.equals(" ")))
                fitExperssion.append(value);
        }

        return fitExperssion.toString(); //공백이 제거된 수식 리턴
    }

    private String Transform(String infix) {
        StringBuilder postfix = new StringBuilder();
        char op; //연산자를 저장할 임시 변수
        for (int i = 0; i < infix.length(); i++) {
            char ch = infix.charAt(i);
            switch (ch) {
                case '(':
                    operator.push(ch);
                    break;
                case ')':
                    while (true) {
                        op = operator.pop();
                        if (op != '(')
                            postfix.append(op).append(" ");
                        else
                            break;
                    }
                    break;
                case '+':
                case '-':
                    while (true) {
                        if (operator.isEmpty())
                            break;
                        op = operator.pop();

                        if (op == '+' || op == '-' || op == '*' || op == '/')
                            postfix.append(op).append(" ");
                        else {
                            operator.push(op);
                            break;
                        }
                    }
                    operator.push(ch);
                    break;
                case '*':
                case '/':
                    while (true) {
                        if (operator.isEmpty()) break;
                        op = operator.pop();

                        if (op == '*' || op == '/')
                            postfix.append(op).append(" ");
                        else {
                            operator.push(op);
                            break;
                        }
                    }
                    operator.push(ch);
                    break;
                default:
                    postfix.append(ch).append(" ");
                    if (infix.length() > i + 1) {
                        op = infix.charAt(i+1);
                        if (op == '+' || op == '-' || op == '*' || op == '/'
                                || op == ')') {
                            //postfix.append(" ");
                        }
                    }
                    break;
            }
        }
        while(!operator.isEmpty())
            if(operator.top == 0)
                postfix.append(operator.pop());
            else
            postfix.append(operator.pop()).append(" ");
        return postfix.toString();
    }

    private double calculate(String postfix){
        /*
        int cnt=0;
        double operand1, operand2, result;
        for(int i=0; i<postfix.length(); i++){
            char op = postfix.charAt(i);

            if(op>= '0' && op<='9'){
                while(postfix.charAt(i+cnt)!=' ')
                    cnt++;
                result = (double)postfix.charAt(i);
                i += cnt;
                cnt=0;
                operand.push(result);
            }else{
                if(op != ' '){
                    operand1 = operand.pop();
                    operand2 = operand.pop();
                    switch (op) {
                        case '+':
                            operand.push(operand2 + operand1);
                            break;
                        case '-':
                            operand.push(operand2 - operand1);
                            break;
                        case '*':
                            operand.push(operand2 * operand1);
                            break;
                        case '/':
                            operand.push(operand2 / operand1);
                            break;
                    }
                }
            }
        }
*/
        String[] expressionArray = postfix.split(" ");
        //String ch = "";

        for(String ch:expressionArray){
            try {
                double num = Double.parseDouble(ch);
                operand.push(num);
            }catch (NumberFormatException e){
                double operand1 = operand.pop();
                double operand2 = operand.pop();
                switch (ch) {
                    case "+":
                        operand.push(operand2 + operand1);
                        break;
                    case "-":
                        operand.push(operand2 - operand1);
                        break;
                    case "*":
                        operand.push(operand2 * operand1);
                        break;
                    case "/":
                        operand.push(operand2 / operand1);
                        break;
                }

            }
        }
        return operand.pop();
    }
}
