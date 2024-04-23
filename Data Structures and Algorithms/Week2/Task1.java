//Erokhin Evgenii
//e.erokhin@innopolis.university
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String inLine = in.nextLine();
        //        inLine.replace(",",")");
        String[] inLineSplit = inLine.split(" ");
        //   System.out.println(inLineSplit.length);
        Stack newStack = new Stack(1000);
        for (int j=0;j<inLineSplit.length;j++) {
            if ((!inLineSplit[j].equals("+")) &
                    (!inLineSplit[j].equals("-")) &
                    (!inLineSplit[j].equals("/")) &
                    (!inLineSplit[j].equals("*")) &
                    (!inLineSplit[j].equals("min")) &
                    (!inLineSplit[j].equals("max")) &
                    (!inLineSplit[j].equals("(")) &
                    (!inLineSplit[j].equals(",")) &
                    (!inLineSplit[j].equals(")"))) {
                System.out.print(inLineSplit[j] + " ");
            } else if (newStack.stackEmpty().equals("YES")) {
                newStack.push(inLineSplit[j]);
            } else if ((inLineSplit[j].equals("+") || inLineSplit[j].equals("-")) &
                    (!newStack.stackA[newStack.top].equals("+") &
                            !newStack.stackA[newStack.top].equals("-")&
                            !newStack.stackA[newStack.top].equals("*") &
                            !newStack.stackA[newStack.top].equals("/")&
                            !newStack.stackA[newStack.top].equals("min") &
                            !newStack.stackA[newStack.top].equals("max"))) {
                newStack.push(inLineSplit[j]);
            } else if ((inLineSplit[j].equals("*") || inLineSplit[j].equals("/")) &
                    (!newStack.stackA[newStack.top].equals("*") &
                            !newStack.stackA[newStack.top].equals("/")&
                            !newStack.stackA[newStack.top].equals("min") &
                            !newStack.stackA[newStack.top].equals("max"))) {
                newStack.push(inLineSplit[j]);
            } else if ((inLineSplit[j].equals("min") || inLineSplit[j].equals("max"))) {
                newStack.push(inLineSplit[j]);
            } else if ((inLineSplit[j].equals("+") || inLineSplit[j].equals("-"))) {
                while (newStack.stackEmpty().equals("NO")&&!newStack.stackA[newStack.top].equals("(")) {
                    System.out.print(newStack.pop() + " ");


                }newStack.push(inLineSplit[j]);
            } else if ((inLineSplit[j].equals("*") || inLineSplit[j].equals("/"))) {
                while (newStack.stackEmpty().equals("NO")&&!newStack.stackA[newStack.top].equals("+") &&
                        !newStack.stackA[newStack.top].equals("-")&&!newStack.stackA[newStack.top].equals("(")){
                    System.out.print(newStack.pop() + " ");

                }newStack.push(inLineSplit[j]);
            }/* else if ((inLineSplit[j].equals("min") || inLineSplit[j].equals("max"))) {
                while (!newStack.stackA[newStack.top].equals("+") ||
                        !newStack.stackA[newStack.top].equals("-") ||
                        !newStack.stackA[newStack.top].equals("*") ||
                        !newStack.stackA[newStack.top].equals("/") ||
                        !newStack.stackA[newStack.top].equals("min") ||
                        !newStack.stackA[newStack.top].equals("max")) {
                    System.out.print(newStack.pop() + " ");
                    if (newStack.stackEmpty().equals("YES")) {
                        newStack.push(inLineSplit[j]);
                        break;
                    }
                }

            }*/
            else if (inLineSplit[j].equals("(")) {newStack.push(inLineSplit[j]);
            } else if (inLineSplit[j].equals(",")) {
                while (newStack.stackEmpty().equals("NO")&&!newStack.stackA[newStack.top].equals("(")) {
                    System.out.print(newStack.pop() + " ");
                }

            } else if (inLineSplit[j].equals(")")){
                while (newStack.stackEmpty().equals("NO")&&!newStack.stackA[newStack.top].equals("(")){
                    System.out.print(newStack.pop() + " ");

                }
                if (newStack.stackEmpty().equals("NO"))newStack.pop();
                if (newStack.stackEmpty().equals("NO")&&(newStack.stackA[newStack.top].equals("max")||newStack.stackA[newStack.top].equals("min"))){
                    System.out.print(newStack.pop() + " ");
                }

            }
        }
        while (newStack.stackEmpty().equals("NO")){
            System.out.print(newStack.pop()+" ");
        }
    }
}
class Stack {
    public int size;
    public String[] stackA;
    public int top;
    public String element;
    public Stack(int m){
        this.size=m;
        this.top=-1;
        stackA=new String[size];
    }
    public void push(String a){this.top++;
        stackA[top]=a;
    }
    public String pop(){
        this.top--;
        return stackA[top+1];
    }
    public String stackEmpty(){
        if (top==-1)
        {
            return "YES";
        }
        else {
            return "NO";
        }

    }
}
