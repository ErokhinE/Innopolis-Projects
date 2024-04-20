//Erokhin Evgenii DSAI-03
//For solving this task I used my code from assigment 1 change it according to current task

import java.io.*;
import java.util.*;

/**
 * The type Main.
 */
public class Main {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {
        File inFile = new File("input.txt"); //input file
        // File outFile = new File("result.txt");//output file
        BufferedReader reader = new BufferedReader(new FileReader(inFile));
        //FileOutputStream writer = new FileOutputStream("result.txt");
        String states1 = reader.readLine(); //Read fsa states

        //check all data input

        if (!states1.startsWith("states=[") || !states1.endsWith("]")) {
            System.out.println("E1: Input file is malformed"); //If incorrect write error
            System.exit(0);
        }
        String alpha1 = reader.readLine(); //Read fsa alphabet
        if (!alpha1.startsWith("alpha=[") || !alpha1.endsWith("]")) {
            System.out.println("E1: Input file is malformed"); //If incorrect write error
            System.exit(0);
        }
        String initial1 = reader.readLine(); //Read fsa initial state
        if (!initial1.startsWith("initial=[") || !initial1.endsWith("]")) {
            System.out.println("E1: Input file is malformed"); //If incorrect write error
            System.exit(0);
        }
        String accepting1 = reader.readLine(); //Read fsa finite state
        if (!accepting1.startsWith("accepting=[") || !accepting1.endsWith("]")) {
            System.out.println("E1: Input file is malformed"); //If incorrect write error
            System.exit(0);
        }
        String trans1 = reader.readLine(); ////Read fsa transitions
        if (!trans1.startsWith("trans=[") || !trans1.endsWith("]")) {
            System.out.println("E1: Input file is malformed"); //If incorrect write error
            System.exit(0);
        }



        String states = states1.substring(8, states1.length() - 1); //Split to work
        String alpha = alpha1.substring(7, alpha1.length() - 1);
        String init = initial1.substring(9, initial1.length() - 1);
        String accept = accepting1.substring(11, accepting1.length() - 1);
        String trans = trans1.substring(7, trans1.length() - 1);
        String[] statesSplit = states.split(","); //Split with , to take elements
        String[] alphaSplit = alpha.split(",");
        String[] initSplit = init.split(",");
        String[] acceptSplit = accept.split(",");
        String[] transSplit = trans.split(",");

        Error newError = new Error();
        if (initSplit.length > 1) { //check number of initial states
            System.out.println("E1: Input file is malformed"); //If incorrect write error
            System.exit(0);
        }
        newError.error1(states1, statesSplit, alphaSplit, transSplit); //Check the correctness of input file
        newError.error1(alpha1, statesSplit, alphaSplit, transSplit);
        newError.error1(initial1, statesSplit, alphaSplit, transSplit);
        newError.error1(accepting1, statesSplit, alphaSplit, transSplit);
        newError.error1(trans1, statesSplit, alphaSplit, transSplit);
        if (newError.counter != 5 || newError.counter1 != 5) {
            System.out.println("E1: Input file is malformed"); //If incorrect write error
            System.exit(0);
        }
        //Check all error
        newError.error2(initSplit);
        newError.error3(acceptSplit);
        newError.error4(statesSplit, transSplit, acceptSplit, initSplit);
        newError.error5(alphaSplit, transSplit);
        newError.error6(transSplit, statesSplit, initSplit);
        newError.error7(transSplit, statesSplit);


        //Array of hashsets of transitions
        HashSet<String>[] HashSet = new HashSet[statesSplit.length];
        for (int i = 0; i < statesSplit.length; i++) {
            HashSet[i] = new HashSet<>();
        }
        for (int i = 0; i < statesSplit.length; i++) {
            for (int j = 0; j < transSplit.length; j++) {
                String[] trans2Split;
                trans2Split = transSplit[j].split(">");
                if (statesSplit[i].equals(trans2Split[0])) {
                    HashSet[i].add(trans2Split[1]);
                }
            }
        }
        KleeneAlg(statesSplit, transSplit, initSplit, acceptSplit); //start of algorithm

    }

    /**
     * Kleene algorithm for solving task
     *
     * @param statesSplit    the states split
     * @param transSplit     the trans split
     * @param initialSplit   the initial split
     * @param acceptingSplit the accepting split
     */
    public static void KleeneAlg(String[] statesSplit, String[] transSplit, String[]
            initialSplit, String[] acceptingSplit) {
        String[][] adj = new String[statesSplit.length][statesSplit.length];//matrix with values
        String[][] adjTemp = new String[statesSplit.length][statesSplit.length];// temporary storing
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < statesSplit.length; i++) {
            map.put(statesSplit[i], i);
        }
        for (int i = 0; i < statesSplit.length; i++) {
            for (int j = 0; j < statesSplit.length; j++) { //make it empty
                adj[i][j] = "";
            }
        }

        //fill matrix by k=-1 elements
        for (int i = 0; i < transSplit.length; i++) {
            String[] trans2split = transSplit[i].split(">");
            if (adj[map.get(trans2split[0])][map.get(trans2split[2])].equals("")) {
                adj[map.get(trans2split[0])][map.get(trans2split[2])]
                        = adj[map.get(trans2split[0])][map.get(trans2split[2])] + "(" + trans2split[1];
            } else {
                adj[map.get(trans2split[0])][map.get(trans2split[2])]
                        = adj[map.get(trans2split[0])][map.get(trans2split[2])] + "|" + trans2split[1];
            }
        }

        //change matrix to satisfy condition
        for (int i = 0; i < statesSplit.length; i++) {
            for (int j = 0; j < statesSplit.length; j++) {
                if (i == j && !adj[i][j].equals("")) {
                    adj[i][j] = adj[i][j] + "|eps)";
                } else if (i == j && adj[i][j].equals("")) {
                    adj[i][j] = adj[i][j] + "(eps)";
                } else if (adj[i][j].equals("")) {
                    adj[i][j] = "({})";
                } else adj[i][j] = adj[i][j] + ")";
            }
        }

        //count k steps of Kleene's algorithm
        for (int k = 0; k < statesSplit.length; k++) {
            for (int i = 0; i < statesSplit.length; i++) {
                for (int j = 0; j < statesSplit.length; j++) {
                    adjTemp[i][j] = "(" + adj[i][k] + adj[k][k] + "*" + adj[k][j] + "|" + adj[i][j] + ")";
                }
            }
            for (int i = 0; i < statesSplit.length; i++) {
                System.arraycopy(adjTemp[i], 0, adj[i], 0, statesSplit.length);
            }
        }
        String res = ""; //result string
        int[] arr = new int[acceptingSplit.length];
        for (int i = 0; i < acceptingSplit.length; i++) {
            arr[i] = map.get(acceptingSplit[i]);
        }
        for (int i = 0; i < arr.length - 1; i++) { //sort accepting states to print result correctly
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        for (int i = 0; i < arr.length; i++) {
            if (res.equals("")) {
                res = res + adj[map.get(initialSplit[0])][arr[i]];
            } else res = res + "|" + adj[map.get(initialSplit[0])][arr[i]];//make result string
        }
        System.out.println(res);

    }
}

/**
 * The type Error.
 * Class of Errors
 */
class Error {
    /**
     * The Counter to check start of each string
     */
    public int counter = 0;
    /**
     * The Counter 1 to check end of each string
     */
    public int counter1 = 0;

    /**
     * Error 1.If input is incorrect. Throw error in output
     *
     * @param Any         any of string we want to check
     * @param statesSplit the states Split
     * @param alphaSplit  the alpha Split
     * @param transSplit  the trans Split
     * @throws IOException the io exception
     */
    public void error1(String Any, String[] statesSplit, String[] alphaSplit, String[] transSplit) {
        if (Any.startsWith("states=[") || Any.startsWith("alpha=[") || Any.startsWith("initial=[")
                || Any.startsWith("accepting=[") || Any.startsWith("trans=[")) {
            counter = counter + 1;
        } else {
            System.out.println("E1: Input file is malformed"); //If incorrect write error
            System.exit(0);
        }
        if (Any.endsWith("]")) {
            counter1 = counter1 + 1;
        } else {
            System.out.println("E1: Input file is malformed"); //If incorrect write error
            System.exit(0);
        }
        for (String state : statesSplit) {
            if (!state.matches("\\w+")) {
                System.out.println("E1: Input file is malformed");
                System.exit(0);
            }
        }
        for (String alpha : alphaSplit) {
            if (!alpha.matches("\\w+")) {
                System.out.println("E1: Input file is malformed");
                System.exit(0);
            }
        }
        for (String trans : transSplit) {
            if (trans.split(">").length != 3) {
                System.out.println("E1: Input file is malformed");
                System.exit(0);
            }
        }
    }

    /**
     * Error 2. If we do not have initial state. Throw error in output
     *
     * @param initSplit the init Split
     * @throws IOException the io exception
     */
    public void error2(String[] initSplit) {
        if (initSplit[0].equals("")) {
            System.out.println("E2: Initial state is not defined");
            System.exit(0);
        }
    }

    /**
     * Error 3. If accepting state is not defined return Warning
     *
     * @param finitSplit the finitSplit
     * @return the string
     * @throws IOException the io exception
     */
    public void error3(String[] finitSplit) {
        if (finitSplit[0].equals("")) {
            System.out.println("E3: Set of accepting states is empty");
            System.exit(0);
        }
    }

    /**
     * Error 4. If we do not have some state from transition in state list. Throw error in output
     *
     * @param statesSplit    the states Split
     * @param transSplit     the trans Split
     * @param acceptingSplit the accepting split
     * @param intitalSplit   the intital split
     * @throws IOException the io exception
     */
    public void error4(String[] statesSplit, String[] transSplit, String[] acceptingSplit, String[] intitalSplit) {
        String[] trans2Split = new String[transSplit.length * 3];
        int has = -1;
        int has1 = -1;
        for (int i = 0; i < statesSplit.length; i++) {
            if (statesSplit[i].equals(intitalSplit[0])) {
                has = i;
                break;
            }
        }
        if (has == -1) {
            System.out.println(("E4: A state '" + intitalSplit[0] + "' is not in the set of states"));
            System.exit(0);
        }
        for (int i = 0; i < acceptingSplit.length; i++) {
            has1 = -1;
            for (int j = 0; j < statesSplit.length; j++) {
                if (statesSplit[j].equals(acceptingSplit[i])) {
                    has1 = i;
                    break;
                }
            }
            if (has1 == -1) {
                System.out.println(("E4: A state '" + acceptingSplit[i] + "' is not in the set of states"));
                System.exit(0);
            }
        }
        for (int i = 0; i < transSplit.length; i++) {
            int counter = 0;
            int counter1 = 0;
            trans2Split = transSplit[i].split(">");
            for (String s : statesSplit) {
                if (trans2Split[0].equals(s)) { // Check first transition (Ex: on>turn_off>off Check "on" )
                    counter = counter + 1;
                }
                if (trans2Split[2].equals(s)) { // Check second transition (Ex: on>turn_off>off Check "off" )
                    counter1 = counter1 + 1;
                }
            }
            if (counter == 0) {
                System.out.println(("E4: A state '" + trans2Split[0] + "' is not in the set of states"));
                System.exit(0);
            }
            if (counter1 == 0) {
                System.out.println(("E4: A state '" + trans2Split[2] + "' is not in the set of states"));
                System.exit(0);
            }

        }
    }

    /**
     * Error 5. If we do not have some transition in alphabet list. Throw error in output
     *
     * @param alphaSplit the alpha Split
     * @param transSplit the trans Split
     * @throws IOException the io exception
     */
    public void error5(String[] alphaSplit, String[] transSplit) {
        String[] trans2Split;
        for (int i = 0; i < transSplit.length; i++) {
            int counter = 0;
            trans2Split = transSplit[i].split(">");
            for (String s : alphaSplit) {
                if (trans2Split[1].equals(s)) {
                    counter = counter + 1;
                }
            }
            if (counter == 0) {
                System.out.println(("E5: A transition '" + trans2Split[1] + "' is not represented in the alphabet"));
                System.exit(0);
            }
        }
    }

    /**
     * Error 6. Throw exception if some states from fsa are disjoint
     *
     * @param transSplit  the trans Split
     * @param statesSplit the states Split
     * @param initSplit   the init Split
     * @throws IOException the io exception
     */
    public void error6(String[] transSplit, String[] statesSplit, String[] initSplit) {
        FsaNew g = new FsaNew(statesSplit.length);
        for (int i = 0; i < statesSplit.length; i++) {
            for (int j = 0; j < transSplit.length; j++) {
                String[] trans2Split;
                trans2Split = transSplit[j].split(">");
                int dest = 0;
                for (int k = 0; k < statesSplit.length; k++) {
                    if (statesSplit[k].equals(trans2Split[2])) dest = k;
                }
                if (statesSplit[i].equals(trans2Split[0])) {
                    g.addToGraph(i, dest); //Make fsa
                }
            }
        }

        int coutMax = 0;
        for (int i = 0; i < statesSplit.length; i++) {
            boolean[] visited = new boolean[statesSplit.length];
            g.ver.clear();
            g.dfs(i, visited);
            if (coutMax < g.count1) {
                coutMax = g.count1;
                //CountMax = number of vertices we have visited if we use dfs for all vertices as initial and max of it
            }
        }
        if (coutMax != statesSplit.length) {
            System.out.println("E6: Some states are disjoint");
            //If not equal to number of states throw exception
            System.exit(0);
        }

    }

    /**
     * Error 7. If more than 1 equals transitions goes from 1 state return Warning that is not deterministic
     *
     * @param transSplit  the trans Split
     * @param statesSplit the states Split
     * @return the string
     * @throws IOException the io exception
     */
    public void error7(String[] transSplit, String[] statesSplit) throws IOException {
        HashMap<Integer, ArrayList<String>> Map = new HashMap<Integer, ArrayList<String>>();
        for (int i = 0; i < statesSplit.length; i++) {
            Map.put(i, new ArrayList<>());
        }
        for (int i = 0; i < statesSplit.length; i++) {
            HashSet<String> set = new HashSet<>();
            for (int j = 0; j < transSplit.length; j++) {
                String[] trans2Split;
                trans2Split = transSplit[j].split(">");
                if (statesSplit[i].equals(trans2Split[0])) {
                    Map.get(i).add(trans2Split[1]);
                    set.add(trans2Split[1]);
                }
            }
            if (set.size() != Map.get(i).size()) {
                // Check the set size with map key num of state value ArrList with transitions
                // If size of set not equals the ArrList size throw warning
                System.out.println("E7: FSA is nondeterministic");
                System.exit(0);
            }
        }

    }
}



/**
 * The type FsaNew. Fsa as a tree to check the joint of states and reachable from initial state
 *
 * @param <V> the type parameter
 */
class FsaNew<V> {

    /**
     * The Vertices
     */
    public final ArrayList<Object> ver = new ArrayList<>();
    private final LinkedList[] adj; //adjustment list of edges
    /**
     * The Count 1. Number of vertices we have visited
     */
    public int count1 = 0;

    /**
     * Instantiates a new FsaNew.
     *
     * @param vertices the vertices
     */
    FsaNew(int vertices) {
        adj = new LinkedList[vertices];
        for (int i = 0; i < vertices; ++i)
            adj[i] = new LinkedList(); //adjustment list of edges for all vertices
    }

    /**
     * Add to graph edge
     *
     * @param start the start of edge
     * @param dest  the destination of edge
     */
    void addToGraph(int start, V dest) {
        adj[start].add(dest);
    } //add edge from start to destination

    /**
     * Dfs algorithm
     *
     * @param initial root for dfs algorithm then the vertices where we are
     * @param vis     the visited vertices
     */
    void dfs(int initial, boolean[] vis) {
        int count = 0;
        vis[initial] = true;
        ver.add(initial);
        count = ver.size();
        count1 = count;
        for (int in : (Iterable<Integer>) adj[initial]) {
            if (!vis[in])
                dfs(in, vis);
        }
    }
}
