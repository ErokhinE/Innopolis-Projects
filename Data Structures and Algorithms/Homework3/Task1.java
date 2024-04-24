//Evgenii Erokhin
//e.erokhin@innopolis.university

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        Graph<Integer, Integer> graph = new Graph<>();
        for (int i = 0; i < N; i++) {
            graph.addVertex(i);//add vertices
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int temp = in.nextInt();
                graph.addEdge(graph.getVertex(i), graph.getVertex(j), temp); //add edges
            }
        }

        graph.FloydWarshallAlg();

    }
}

class Graph<V, E extends Number> {

    List<Vertex> vertices;
    List<Edge<E>> edges;

    public Graph() {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public void addVertex(V value) {
        Vertex v = new Vertex(value);
        this.vertices.add(v);
    }

    public void addEdge(Vertex from, Vertex to, E weight) {
        Edge<E> e = new Edge<>(from, to, weight);
        this.edges.add(e);
        from.adjacent.add(e);
    }

    public Vertex getVertex(V value) {
        for (Vertex v : vertices) {
            if (value == v.value)
                return v;
        }
        return null;
    }

    public void FloydWarshallAlg() {
        int n = vertices.size();
        Pair[][] cost = new Pair[n][n];
        int[][] parent = new int[n][n];


        //fill matrix cost with pairs according to weight of edge
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Pair pair1 = new Pair<>(vertices.get(i).adjacent.get(j).weight,
                        vertices.get(i).adjacent.get(j).weight.intValue());
                cost[i][j] = pair1;

            }

        }

        //fill parents matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (cost[i][j].weightInt != 100000) {
                    parent[i][j] = j;

                } else parent[i][j] = -1;

            }

        }


        //count path by adding new vertex in each iteration
        int a, b;
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    a = cost[i][k].weightInt;
                    b = cost[k][j].weightInt;
                    int costThrowK = 0;
                    if (a == 100000 || b == 100000) {
                        costThrowK = 100000;
                    } else costThrowK = a + b;
                    if (costThrowK < cost[i][j].weightInt) {
                        cost[i][j].weightInt = costThrowK;
                        parent[i][j] = parent[i][k];
                    }
                }
            }
        }

        //detect negative cycle if exist take path
        boolean flag = false;
        for (int i = 0; i < n; i++) {
            if (cost[i][i].weightInt < 0) {
                System.out.println("YES");

                flag = true;
                int end = parent[i][i];
                ArrayList<Integer> path = new ArrayList<>();

                //go n times through parents to catch vertex which is negative cycle
                for (int x = 0; x < n; x++) {
                    end = parent[end][end];
                }

                //after finding make path according to vertex
                int end1 = parent[end][end];
                while (end1 != end) {
                    path.add(end1);
                    end1 = parent[end1][end1];
                }
                path.add(end1);


                System.out.println(path.size());
                for (int k = 0; k < path.size(); k++) {
                    System.out.print(path.get(k) + 1 + " ");
                }
                System.exit(0);

            }
        }
        System.out.println("NO");

    }

    //class of pairs. First is generic weight, second its int representation
    class Pair<E extends Number> {
        E weight;
        int weightInt;

        public Pair(E weight, int weightInt) {
            this.weight = weight;
            this.weightInt = weightInt;
        }
    }

    class Edge<E extends Number> {
        Vertex from;
        Vertex to;
        E weight;

        public Edge(Vertex from, Vertex to, E weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

    }

    class Vertex {
        V value;
        boolean visited;
        List<Edge<E>> adjacent;

        public Vertex(V value) {
            this.value = value;
            this.adjacent = new ArrayList<Edge<E>>();
            this.visited = false;
        }
    }
}

