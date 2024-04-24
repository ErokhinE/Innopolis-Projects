//Erokhin Evgenii
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int Map[][] = new int[N][N];
        for (int i = 0;i<N;i++){
            for (int j = 0;j<N;j++){
                Map[i][j] = in.nextInt();
            }
        }
        connectivness(Map,N);
    }

    public static void connectivness(int[][] Map, int N) {


        DirectedGraph g = new DirectedGraph(N);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (Map[i][j] == 1) {
                    g.addToGraph(i, j);
                    g.addToGraph(j, i);
                }
            }
        }

        for (int i = 0; i < N; i++) {
            boolean[] vis = new boolean[N];
            g.ver.clear();
            g.dfs(i, vis);
            if (g.ver.size()!=N) {
                System.out.print("NO");
                System.exit(0);
            }
        }

        System.out.print("YES");
        System.exit(0);
    }
}
class DirectedGraph<T> {

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
     * Instantiates a new DirectedGraph.
     *
     * @param vertices
     */
    DirectedGraph(int vertices) {
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
    void addToGraph(int start, T dest) {
        adj[start].add(dest);
    } //add edge from start to destination

    /**
     * Dfs algorithm
     *
     * @param i  root for dfs algorithm then the vertices where we are
     * @param vis the visited vertices
     */
    void dfs(int i, boolean[] vis) {
        int count=0;
        vis[i] = true;
        ver.add(i);
        count=ver.size();
        count1=count;
        for (int in : (Iterable<Integer>) adj[i]) {
            if (!vis[in])
                dfs(in, vis);
        }
    }
}
