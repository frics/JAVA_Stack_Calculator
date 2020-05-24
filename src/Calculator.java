
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
    private static Scanner s = new Scanner(System.in);
    Operator operator = new Operator(); //연산자 스택
    Operand operand = new Operand(); //피연산자 스택
    String confirm;
    public static void main(String[] args) throws Exception{
        Calculator calculator = new Calculator();
        System.out.println("=======MyCalculator=======");
        calculator.Input();
    }
    private void Input() {

        String infix; //중위표기 수식을 입력 받음
        String postfix; //후위표기식 저장
        while (true) {
            s = new Scanner(System.in);
            System.out.print("\nInfix로 수식을 입력하시오. \n>>> ");
            infix = s.nextLine(); // nextLine을 사용하여 공백도 입력받을 수 있게 처
            infix = removeSpace(infix); //입력한 수식에 공백이 있는 경우 제거
            System.out.print(">>>Postfix로 변환 : ");
            postfix = Transform(infix);
            System.out.println(postfix);
            Run(postfix);
            while (true) {
                System.out.print("계속하시겠습니까? (Y/N)\n>>> ");
                confirm = s.next();
                if (confirm.equals("Y") || confirm.equals("y")) {
                    break;
                } else if (confirm.equals("N") || confirm.equals("n")){
                    System.out.print("사용해주셔서 감사합니다.\n프로그램을 종료합니다.\n");
                    System.out.println("==========================");
                    return;
                }
                else
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private void Run(String postfix){
        double result;
        while (true) {
            System.out.print("계산을 시작하시겠습니까? (Y/N)\n>>> ");
            confirm= s.next();
            if(confirm.equals("Y")||confirm.equals("y")) {
                result = calculate(postfix);
                System.out.println("계산 값 : "+result);
                break;
            }else if(confirm.equals("N")||confirm.equals("n"))
                break;
            else
                System.out.println("잘못된 입력입니다.");
        }
    }
    private String removeSpace(String infix) { //입력한 수식에 공백이 있는 경우 제거
        String[] splitExpression = infix.split("");
        StringBuilder fitExperssion = new StringBuilder();
        for (int i=0; i<splitExpression.length; i++) {
            String value = splitExpression[i];
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
                    postfix.append(ch);
                    while (true) { //1의 자리 이상의 숫자를 공백을 이용해 구분
                        if (infix.length() > i + 1) { //i가 해당 문자열보다 길지 않도록 조건문 삽입
                            op = infix.charAt(i + 1); //다음 문자를 확인
                            if (op >= '0' && op <= '9') { //다음 문자가 숫자이면 1의 자리 이상으로 판별, ch 뒤에 붙임
                                postfix.append(op);
                                i++;
                            } else if (op == '.') { //해당 숫자가 소수점이 있는지 확인
                                postfix.append(op);
                                i++;
                            } else { //다음 문자가 숫자가 아닐 경우 공백 삽입 후 반복문 탈출
                                postfix.append(" ");
                                break;
                            }
                        }
                        else { //해당 수식의 끝이 왔을 경우 공백 삽입 후 반복문 탈출
                            postfix.append(" ");
                            break;
                        }
                    }
            }
        }
        while(!operator.isEmpty()) //연산자 스택에 남아 있는 연산자 삽입
            if(operator.top == 0) //마지막 연산자일 경우 공백 삽입 방지
                postfix.append(operator.pop());
            else
                postfix.append(operator.pop()).append(" ");
        return postfix.toString();
    }

    private double calculate(String postfix){
        String[] expressionArray = postfix.split(" ");

        for(int i=0; i<expressionArray.length; i++){
            String ch = expressionArray[i];
            try {
                // if(
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

