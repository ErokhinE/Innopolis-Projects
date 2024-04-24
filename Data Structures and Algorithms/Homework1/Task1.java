//Erokhin Evgenii
//e.erokhin@innopolis.university
//This code was tested by CodeForces and passed all tests

import java.util.Scanner;

/**
 * The type Main.
 */
public class Main {
    /**
     * The usage. Num del
     */
    static int usage;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int numDel = in.nextInt();    // Num of del
        usage = numDel;
        int numStr = in.nextInt();    //Num of strings
        Delimiters listOfDel = new Delimiters();
        LinkedStack stack = new LinkedStack();


        for (int i = 0; i < numDel; i++) {
            String firstDel = in.next();
            String secondDel = in.next();
            listOfDel.del(firstDel, secondDel, i);  // Make an array of open and closed delimiters

        }
        in.nextLine();
        int len = 0; //length
        int currentLine = 0;
        for (int j = 0; j < numStr; j++) {
            currentLine = j + 1;
            String strRub = in.nextLine(); //line from input
            String[] strRubSplit = strRub.split(" ");
            for (int k = 0; k < strRubSplit.length; k++) {
                for (int i = 0; i < listOfDel.open.length; i++) {

                    if (strRubSplit[k].equals(listOfDel.open[i])) {
                        stack.push(strRubSplit[k]);  //if meet open delimiter push in stack


                    } else if (strRubSplit[k].equals(listOfDel.close[i]) && stack.isEmpty()) {
                        String temp = strRubSplit[k];
                        //if meet close delimiter and stack is empty print error
                        strRubSplit[k] = strRubSplit[k] + "%";
                        System.out.print("Error in line " + currentLine +
                                ", column " + column(strRubSplit, strRubSplit[k])
                                + ": unexpected closing token " + temp + ".");
                        System.exit(0);


                    } else if (strRubSplit[k].equals(listOfDel.close[i]) && !stack.isEmpty()) {

                        if (stack.peek().equals(listOfDel.open[i])) {
                            stack.pop();
                            //if meet close delimiter and stack is not empty and close and open delimiters have 1 type


                        } else {
                            String element = "";

                            for (int x = 0; x < listOfDel.open.length; x++) {
                                if (stack.peek().equals(listOfDel.open[x])) element = listOfDel.close[x];
                            }
                            //if meet close delimiter and stack is not empty and close and open delimiters
                            // have different type print error
                            strRubSplit[k] = strRubSplit[k] + "%";
                            System.out.print("Error in line " + currentLine +
                                    ", column " + column(strRubSplit, strRubSplit[k])
                                    + ": expected " + element + " but got " + listOfDel.close[i] + ".");
                            System.exit(0);
                        }

                    }
                }
            }

            if (j == numStr - 1) {
                len = strRub.length() + 2;//length of last string
            }
        }
        if (stack.isEmpty()) {
            System.out.print("The input is properly balanced.");//Print if every thing is ok


        } else {//if after all input stack is not empty print error
            String element = "";
            for (int i = 0; i < listOfDel.open.length; i++) {
                if (stack.peek().equals(listOfDel.open[i])) element = listOfDel.close[i];
            }
            System.out.print("Error in line " + currentLine + ", column " + len
                    + ": expected " + element + " but got end of input.");
        }
    }

    /**
     * Column int.
     * Finding column number of error
     *
     * @param stringFirst the String where we search for subString
     * @param subStringFirst the subString
     * @return the int
     */
    public static int column(String[] stringFirst, String subStringFirst) {
        int counter = 0;
        for (String s : stringFirst) {
            if (s.equals(subStringFirst)) {
                return counter + 1;
            } else {
                counter = counter + s.length() + 1;
            }
        }
        return 0;
    }

}

/**
 * The type Delimiters.
 */
class Delimiters {
    /**
     * The Open.
     */
    String[] open = new String[Main.usage];
    /**
     * The Close.
     */
    String[] close = new String[Main.usage];
    
    /**
     * Del.
     *
     * @param open       the open del
     * @param close       the close del
     * @param counter the counter
     */
    public void del(String open, String close, int counter) {
        this.open[counter] = open;
        this.close[counter] = close;
    }

    /**
     * Gets open.
     *
     * @param index the index to get open del
     * @return the open
     */
    public String getOpen(int index) {
        return open[index];
    }

    /**
     * Gets close.
     *
     * @param index the index to get close del
     * @return the close
     */
    public String getClose(int index) {
        return close[index];
    }
}

/**
 * List.
 */
class ListOf {
    public ListOf following;

    public String data;


    /**
     * Instantiates a new List.
     *
     * @param data the data
     */
    public ListOf(String data) {
        following = null;
        this.data = data;
    }

    /**
     * Gets next.
     *
     * @return the next
     */
    public ListOf next() {
        return following;
    }

    /**
     * Sets next.
     *
     */
    public void setNext(ListOf index) {
        following = index;
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public String getData() {
        return data;
    }
}

/**
 * The type Linked stack.
 */
class LinkedStack {
    private int length; // indicates the size of the linked list
    private ListOf top;

    /**
     * Instantiates a new Linked stack.
     */
    public LinkedStack() {
        length = 0; // Make new
        top = null;
    }

    /**
     * Push.
     *
     * @param data the data
     */
    public void push(String data) {
        ListOf temp = new ListOf(data);//push element to a stack
        temp.setNext(top); 
        top = temp;
        length++;
    }

    /**
     * Pop string.
     */
    public void pop() {
        String result = top.getData();
        top = top.next(); //pop element from a stack
        length--;
    }

    /**
     * Peek string.
     *
     * @return the string
     */
    public String peek() {
        return top.getData(); // return the top stack element
    }

    /**
     * Is empty boolean.
     *
     * @return the boolean
     */
    public boolean isEmpty() {
        return (length == 0);//Check empty
    }
}

