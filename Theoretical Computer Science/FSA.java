//Erokhin Evgenii DSAI-03
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
        File inFile = new File("fsa.txt"); //input file
        File outFile = new File("result.txt");//output file
        BufferedReader reader = new BufferedReader(new FileReader(inFile));
        FileOutputStream writer = new FileOutputStream("result.txt");
            String states1 = reader.readLine(); //Read fsa states
            String alpha1 = reader.readLine(); //Read fsa alphabet
            String init1 = reader.readLine(); //Read fsa initial state
            String finit1 = reader.readLine(); //Read fsa finite state
            String trans1 = reader.readLine(); ////Read fsa transitions



            String states = states1.substring(8, states1.length() - 1); //Split to work
        String alpha = alpha1.substring(7, alpha1.length() - 1);
        String init = init1.substring(9, init1.length() - 1);
        String finit = finit1.substring(8, finit1.length() - 1);
        String trans = trans1.substring(7, trans1.length() - 1);
            String[] statesSplit = states.split(","); //Split with , to take elements
            String[] alphaSplit = alpha.split(",");
            String[] initSplit = init.split(",");
            String[] finitSplit = finit.split(",");
            String[] transSplit = trans.split(",");

            Error newError = new Error();
            newError.error5(states1,statesSplit,alphaSplit,transSplit,writer); //Check the correctness of input file
            newError.error5(alpha1,statesSplit,alphaSplit,transSplit,writer);
            newError.error5(init1,statesSplit,alphaSplit,transSplit,writer);
            newError.error5(finit1,statesSplit,alphaSplit,transSplit,writer);
            newError.error5(trans1,statesSplit,alphaSplit,transSplit,writer);
            if (newError.counter!=5||newError.counter1!=5){
                writer.write("Error:\nE5: Input file is malformed".getBytes()); //If incorrect write error
                System.exit(0);
            }
        //Check all error
            newError.error1(statesSplit, transSplit,writer);
            newError.error2(transSplit, statesSplit, initSplit,writer);
            newError.error3(alphaSplit, transSplit,writer);
            newError.error4(initSplit,writer);

            //Array of hashsets of transitions
            Warnings newWar = new Warnings();
            HashSet<String>[] HashSet = new HashSet[statesSplit.length];
            for (int i=0;i< statesSplit.length;i++){
                HashSet[i] = new HashSet<>();
            }
            for(int i=0;i<statesSplit.length;i++){
                for (int j=0;j<transSplit.length;j++){
                    String[] trans2Split;
                    trans2Split = transSplit[j].split(">");
                    if(statesSplit[i].equals(trans2Split[0])){
                        HashSet[i].add(trans2Split[1]);
                    }
                }
            }
        // Check complete or not

            newWar.ComOrNot(HashSet, statesSplit.length, alphaSplit.length,writer);

        // Check all warnings for fsa

            if (!newWar.war1(finitSplit,writer).equals("0")){
                writer.write(("Warning:\n"+newWar.war1(finitSplit,writer)).getBytes());
            }
            if (newWar.war1(finitSplit,writer).equals("0")&&!newWar.war2(transSplit,statesSplit,initSplit,writer).equals("0")) {
                writer.write(("Warning:\n" + newWar.war2(transSplit,statesSplit,initSplit,writer)).getBytes());
            }
            else if (!newWar.war1(finitSplit,writer).equals("0")&&!newWar.war2(transSplit,statesSplit,initSplit,writer).equals("0")) {
                writer.write((newWar.war2(transSplit, statesSplit, initSplit, writer)).getBytes());
            }
            if ((!newWar.war1(finitSplit,writer).equals("0")||!newWar.war2(transSplit,statesSplit,initSplit,writer).equals("0"))
            &&!newWar.war3(transSplit,statesSplit,writer).equals("0"))
                writer.write((newWar.war3(transSplit,statesSplit,writer)).getBytes());
            else if (newWar.war1(finitSplit,writer).equals("0")&&newWar.war2(transSplit,statesSplit,initSplit,writer).equals("0")
                    &&!newWar.war3(transSplit,statesSplit,writer).equals("0"))
                writer.write(("Warning:\n" + newWar.war3(transSplit,statesSplit,writer)).getBytes());

    }
}

/**
 * The type Error.
 * Class of Errors
 *
 */
class Error {
    /**
     * The Counter to check start of each string
     */
    public int counter=0;
    /**
     * The Counter 1 to check end of each string
     */
    public int counter1=0;

    /**
     * Error 1. If we do not have some state from transition in state list. Throw error in output
     *
     * @param statesSplit the states Split
     * @param transSplit  the trans Split
     * @param writer        the writer
     * @throws IOException the io exception
     */
    public void error1(String[] statesSplit, String[] transSplit,FileOutputStream writer) throws IOException {
        String[] trans2Split = new String[transSplit.length * 3];
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
                writer.write(("Error:\nE1: A state '" + trans2Split[0] + "' is not in the set of states").getBytes());
                System.exit(0);
            }
            if (counter1 == 0) {
                writer.write(("Error:\nE1: A state '" + trans2Split[2] + "' is not in the set of states").getBytes());
                System.exit(0);
            }

        }
    }

    /**
     * Error 2. Throw exception if some states from fsa are disjoint
     *
     * @param transSplit  the trans Split
     * @param statesSplit the states Split
     * @param initSplit   the init Split
     * @param writer        the writer
     * @throws IOException the io exception
     */
    public void error2(String[] transSplit, String[] statesSplit, String[] initSplit,FileOutputStream writer) throws IOException {
        FsaNew g = new FsaNew(statesSplit.length);
        for (int i=0;i<statesSplit.length;i++){
            for (int j=0;j<transSplit.length;j++){
                String[] trans2Split;
                trans2Split = transSplit[j].split(">");
                int dest=0;
                for (int k=0;k<statesSplit.length;k++) {
                    if (statesSplit[k].equals(trans2Split[2])) dest=k;
                }
                if(statesSplit[i].equals(trans2Split[0])){
                    g.addToGraph(i,dest); //Make fsa
                }
            }
        }

        int coutMax = 0;
        for (int i=0;i<statesSplit.length;i++){
            boolean[] visited = new boolean[statesSplit.length];
            g.ver.clear();
            g.dfs(i,visited);
            if(coutMax<g.count1){
                coutMax= g.count1; 
                //CountMax = number of vertices we have visited if we use dfs for all vertices as initial and max of it
            }
        }
        if (coutMax!= statesSplit.length){
            writer.write("Error:\nE2: Some states are disjoint".getBytes()); 
            //If not equal to number of states throw exception
            System.exit(0);
        }
//        for (int i=0;i<g.ver.size();i++){
//            System.out.print(g.ver.get(i)+" ");
//        }

    }

    /**
     * Error 3. If we do not have some transition in alphabet list. Throw error in output
     *
     * @param alphaSplit the alpha Split
     * @param transSplit the trans Split
     * @param writer       the writer
     * @throws IOException the io exception
     */
    public void error3(String[] alphaSplit, String[] transSplit,FileOutputStream writer) throws IOException {
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
                writer.write(("Error:\nE3: A transition '" + trans2Split[1] + "' is not represented in the alphabet").getBytes());
                System.exit(0);
            }
        }
    }

    /**
     * Error 4. If we do not have initial state. Throw error in output
     *
     * @param initSplit the init Split
     * @param writer      the writer
     * @throws IOException the io exception
     */
    public void error4(String[] initSplit,FileOutputStream writer) throws IOException {
        if(initSplit[0].equals("")) {
            writer.write("Error:\nE4: Initial state is not defined".getBytes());
            System.exit(0);
        }
    }

    /**
     * Error 5.If input is incorrect. Throw error in output
     *
     * @param Any           any of string we want to check
     * @param statesSplit the states Split
     * @param alphaSplit  the alpha Split
     * @param transSplit  the trans Split
     * @param writer        the writer
     * @throws IOException the io exception
     */
    public void error5(String Any,String[] statesSplit, String[] alphaSplit,String[] transSplit,FileOutputStream writer) throws IOException {
        if (Any.startsWith("states=[")|| Any.startsWith("alpha=[")|| Any.startsWith("init.st=[")||Any.startsWith("fin.st=[")||Any.startsWith("trans=[")){
            counter=counter+1;
        }
        if (Any.endsWith("]")) counter1=counter1+1;
        for (String state : statesSplit){
            if (!state.matches("\\w+")){
                writer.write("Error:\nE5: Input file is malformed".getBytes());
                System.exit(0);
            }
        }
        for (String alpha : alphaSplit){
            if (!alpha.matches("\\w+")){
                writer.write("Error:\nE5: Input file is malformed".getBytes());
                System.exit(0);
            }
        }
        for (String trans : transSplit){
            if (trans.split(">").length!=3){
                writer.write("Error:\nE5: Input file is malformed".getBytes());
                System.exit(0);
            }
        }
    }
}

/**
 * The type Warnings. Warnings of fsa
 */
class Warnings {

    /**
     * Check complete fsa or not
     *
     * @param outTrans          the out trans
     * @param statesSplitSize the states Split size
     * @param alphaSplitSize  the alpha Split size
     * @param writer            the writer
     * @throws IOException the io exception
     */
    public void ComOrNot(HashSet<String>[] outTrans, int statesSplitSize, int alphaSplitSize, FileOutputStream writer) throws IOException {
        int comp = 1;
        for (int i = 0; i < statesSplitSize; i++) {
            if (outTrans[i].size() != alphaSplitSize) {
                comp = 0;
                break;
            }
        }
        if(comp==1) {
            writer.write("FSA is complete\n".getBytes());
        }
        else {
            writer.write("FSA is incomplete\n".getBytes());

        }
    }

    /**
     * If accepting state is not defined return Warning
     *
     * @param finitSplit the finitSplit
     * @param writer       the writer
     * @return the string
     * @throws IOException the io exception
     */
    public String war1(String[] finitSplit,FileOutputStream writer) throws IOException {
        if (finitSplit[0].equals("")) {
            return "W1: Accepting state is not defined\n";
        }
        return "0";
    }

    /**
     * If some state are not reachable from initial state return Warning
     *
     * @param transSplit  the trans Split
     * @param statesSplit the states Split
     * @param initSplit   the init Split
     * @param writer        the writer
     * @return the string
     * @throws IOException the io exception
     */
    public String war2(String[] transSplit, String[] statesSplit, String[] initSplit,FileOutputStream writer) throws IOException {
        // New Fsa graph with number of states = number of graph vertices
        FsaNew g = new FsaNew(statesSplit.length);
        for (int i=0;i<statesSplit.length;i++){
            for (int j=0;j<transSplit.length;j++){
                String[] trans2Split;
                trans2Split = transSplit[j].split(">");
                int dest=0;
                for (int k=0;k<statesSplit.length;k++) {
                    if (statesSplit[k].equals(trans2Split[2])) dest=k;
                }
                if(statesSplit[i].equals(trans2Split[0])){
                    g.addToGraph(i,dest); //Add edges for graph
                }
            }
        }
        boolean[] vis = new boolean[statesSplit.length];
        int intit = 0;
        for (int i=0;i<statesSplit.length;i++){
            if (statesSplit[i].equals(initSplit[0])){
                intit = i;
            }
        }

        g.dfs(intit,vis);// Use dfs ones from initial state and the check number of vertices 

        if (g.ver.size()!=statesSplit.length){
            return "W2: Some states are not reachable from the initial state\n";
        }
        return "0";
    }

    /**
     * If more than 1 eqals transitions goes from 1 state return Warning that is not deterministic 
     *
     * @param transSplit  the trans Split
     * @param statesSplit the states Split
     * @param writer        the writer
     * @return the string
     * @throws IOException the io exception
     */
    public String war3(String[] transSplit, String[] statesSplit,FileOutputStream writer) throws IOException {
        HashMap<Integer, ArrayList<String>> Map = new HashMap<Integer,ArrayList<String>>();
        for (int i=0;i<statesSplit.length;i++){
            Map.put(i,new ArrayList<>());
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
            if(set.size()!=Map.get(i).size()){
                // Check the set size with map key num of state value ArrList with transitions
                // If size of set not equals the ArrList size throw warning
                return "W3: FSA is nondeterministic"; 
            }
        }
        return "0";

    }
}

/**
 * The type FsaNew. Fsa as a tree to check the joint of states and reachable from initial state
 */
class FsaNew {

    private final LinkedList[] adj; //adjustment list of edges
    /**
     * The Vertices
     */
    public ArrayList<Object> ver = new ArrayList<>(); 
    /**
     * The Count 1. Number of vertices we have visited 
     */
    public int count1=0;

    /**
     * Instantiates a new FsaNew.
     *
     * @param vertices 
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
     * @param dest the destination of edge
     */
    void addToGraph(int start, int dest) {
        adj[start].add(dest);
    } //add edge from start to destination

    /**
     * Dfs algorithm 
     *
     * @param initial  root for dfs algorithm then the vertices where we are
     * @param vis the visited vertices
     */
    void dfs(int initial, boolean[] vis) {
        int count=0;
        vis[initial] = true;
        ver.add(initial);
        count=ver.size();
        count1=count; 
        for (int in : (Iterable<Integer>) adj[initial]) {
            if (!vis[in])
                dfs(in, vis);
        }
    }
}
