//Evgenii Erokhin
//e.erokin@innopolis.university

import java.util.*; 

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();                             //read vertices number
        Graph<Integer, Integer> graph = new Graph<>(N);   //make graph
        for (int i = 0; i < N; i++) {
            graph.addVertex(i);                           //add vertices
        }
        int M = in.nextInt();                             //read edges number

        //Make trio to fill adjustment matrix
        Trio<Integer> maxTrio = new Trio<>(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                maxTrio.indexTo = j;
                graph.adjustmentForGraph[i][j] = maxTrio;
            }
        }

        for (int i = 0; i < M; i++) {
            int firstI = in.nextInt() - 1;                      //read start index
            int secondI = in.nextInt() - 1;                     //read end index
            int weight = in.nextInt();                          //read weight of edge
            int throwPut = in.nextInt();                        //read bandWith
            Trio<Integer> pair = new Trio<>(secondI, weight, throwPut);
            graph.rewriteAdjustment(pair, firstI, secondI);     //add connection to adjustment matrix
        }

        int start = in.nextInt();                               //read start vertex for Dijkstra
        int end = in.nextInt();                                 // read end vertex for Dijkstra
        int throwPutTogo = in.nextInt();
        graph.dijkstra(start - 1, end - 1, throwPutTogo);


    }
}

class Trio<E> { //class Trio
    int indexTo; //index of destination
    E weight; //weight of edge
    E throwPut; //bandwidth of edge

    Trio(int indexTo, E weight, E throwPut) {
        this.indexTo = indexTo;
        this.weight = weight;
        this.throwPut = throwPut;
    }
}

class Vertex<V extends Number> { //class of vertex
    V value;

    public Vertex(V value) {
        this.value = value;
    }
}

class Graph<V extends Number, E extends Number> { // class of graph

    List<Vertex<V>> vertices; //List of vertices

    Trio<E>[][] adjustmentForGraph; //adjustment matrix


    public Graph(int size) {
        this.vertices = new ArrayList<>(size);
        this.adjustmentForGraph = new Trio[size][size];
    }

    public void rewriteAdjustment(Trio<E> trio, int vertexIndex1, int vertexIndex2) {
        adjustmentForGraph[vertexIndex1][vertexIndex2] = trio;
    }

    public void addVertex(V value) {
        Vertex<V> v = new Vertex<>(value);
        this.vertices.add(v);
    }


    public void dijkstra(int start, int end, int throwPutTogo) {
        int size = vertices.size();
        int[] dis = new int[size]; // array to keep the shortest path from initial vertex to all vertices
        boolean[] vis = new boolean[size]; //to keep information visited or not visited vertex
        int[] parents = new int[size]; //array of parents
        int minThrow = Integer.MAX_VALUE;
        for (int i = 0; i < size; i++) {  //fill arrays
            vis[i] = false;
            dis[i] = Integer.MAX_VALUE;
        }
        dis[start] = 0;
        for (int i = 0; i < size; i++) {
            int vertex = -1;
            for (int j = 0; j < size; j++) {
                if (!vis[j] && (vertex == -1 || dis[j] < dis[vertex])) { //choose current vertex
                    vertex = j;
                }
            }
            if (dis[vertex] == Integer.MAX_VALUE)
                break;
            vis[vertex] = true;
            for (int j = 0; j < size; j++) {
                int to = this.adjustmentForGraph[vertex][j].indexTo;
                int weight = this.adjustmentForGraph[vertex][j].weight.intValue();
                int toThrowPut = this.adjustmentForGraph[vertex][j].throwPut.intValue();

                //change d according to the shortest path save parents
                if (dis[vertex] + weight < dis[to] && toThrowPut >= throwPutTogo && weight != Integer.MAX_VALUE) {
                    dis[to] = dis[vertex] + weight;
                    parents[to] = vertex;
                }
            }
        }
        ArrayList<Integer> path = new ArrayList<>();
        if (dis[end] == Integer.MAX_VALUE) {
            System.out.println("IMPOSSIBLE"); //check can we reach end vertex
            System.exit(0);
        }
        for (int k = end; k != start; k = parents[k]) { //make path
            path.add(k);
        }

        path.add(start);


        for (int i = path.size() - 1; i > 0; i--) {
            int length = adjustmentForGraph[path.get(i)][path.get(i - 1)].throwPut.intValue();
            //find min bandwidth
            minThrow = Math.min(minThrow, length);
        }
        System.out.println(path.size() + " " + dis[end] + " " + minThrow);
        for (int i = path.size() - 1; i >= 0; i--) {
            System.out.print(path.get(i) + 1 + " ");
        }

    }


}
