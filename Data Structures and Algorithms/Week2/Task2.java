//Erokhin Evgenii
//e.erokhin@innopolis.university
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
           Scanner in = new Scanner(System.in);
           String inLine = in.nextLine();
           String math = new String();
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
                math+=inLineSplit[j] + " ";
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
                    math+= newStack.pop() + " ";


                }newStack.push(inLineSplit[j]);
            } else if ((inLineSplit[j].equals("*") || inLineSplit[j].equals("/"))) {
                while (newStack.stackEmpty().equals("NO")&&!newStack.stackA[newStack.top].equals("+") &&
                        !newStack.stackA[newStack.top].equals("-")&&!newStack.stackA[newStack.top].equals("(")){
                    math+= newStack.pop() + " ";

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
                    math+= newStack.pop() + " ";
                }

            } else if (inLineSplit[j].equals(")")){
                while (newStack.stackEmpty().equals("NO")&&!newStack.stackA[newStack.top].equals("(")){
                    math+= newStack.pop() + " ";

                }
                if (newStack.stackEmpty().equals("NO"))newStack.pop();
                if (newStack.stackEmpty().equals("NO")&&(newStack.stackA[newStack.top].equals("max")||newStack.stackA[newStack.top].equals("min"))){
                    math+= newStack.pop() + " ";
                }

            }
        }
        while (newStack.stackEmpty().equals("NO")){
            math+= newStack.pop() + " ";
        }
       // System.out.print(math);
        String[] mathSplit = math.split(" ");
        StackInt newMathStack = new StackInt(1000);
        newMathStack.push(Integer.parseInt(mathSplit[0]));
        newMathStack.push(Integer.parseInt(mathSplit[1]));
        for (int i=2;mathSplit.length>i;i++){
            if(mathSplit[i].equals("+")){
                newMathStack.push(newMathStack.pop()+newMathStack.pop());
            } else if (mathSplit[i].equals("-")) {
                int a = newMathStack.pop();
                int b = newMathStack.pop();
                newMathStack.push(b - a);
            } else if (mathSplit[i].equals("*")){
                newMathStack.push(newMathStack.pop()*newMathStack.pop());

            } else if (mathSplit[i].equals("/")) {
                int a = newMathStack.pop();
                int b = newMathStack.pop();
                newMathStack.push(b / a);
            } else if (mathSplit[i].equals("min")) {
                int a = newMathStack.pop();
                int b = newMathStack.pop();
                if (a>b){newMathStack.push(b);

                }else newMathStack.push(a);

            }else if (mathSplit[i].equals("max")) {
                int a = newMathStack.pop();
                int b = newMathStack.pop();
                if (a > b) {
                    newMathStack.push(a);

                } else newMathStack.push(b);
            } else {newMathStack.push(Integer.parseInt(mathSplit[i]));}


        }
        System.out.print(newMathStack.pop());

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
class StackInt{
    public int sizeInt;
    public int[] stackInt;
    public int topInt;
    public StackInt(int m){
        this.sizeInt=m;
        this.topInt=-1;
        stackInt=new int[sizeInt];
    }
    public void push(int a){this.topInt++;
        stackInt[topInt]=a;
    }
    public int pop(){
        this.topInt--;
        return stackInt[topInt+1];
    }
}
