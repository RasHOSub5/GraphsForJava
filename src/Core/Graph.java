/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Core;

import java.util.*;

/**
 * Represents a general graph structure.
 * <p>
 * Supports directed and undirected graphs, weighted and unweighted graphs.
 * Provides utilities for traversals, shortest path algorithms, and minimum
 * spanning trees.
 * </p>
 *
 * @author Rashid
 */
public class Graph {

    /**
     * List of nodes (vertices) in the graph
     */
    public ArrayList<Node> vertices;

    /**
     * True if the graph is directed
     */
    public boolean directed;

    /**
     * True if the graph is weighted
     */
    public boolean weighted;

    /**
     * Adjacency matrix of the graph
     */
    public int[][] adjacencyMatrix;

    /**
     * Cost (weight) matrix of the graph
     */
    public int[][] costMatrix;

    /**
     * Creates a graph from a list of nodes.
     *
     * @param vertices The list of nodes
     * @param directed True if the graph is directed
     * @param weighted True if the graph is weighted
     */
    public Graph(ArrayList<Node> vertices, boolean directed, boolean weighted) {
        this.vertices = vertices;
        this.directed = directed;
        this.weighted = weighted;
        this.adjacencyMatrix = buildAdjacencyMatrix();
        this.costMatrix = buildCostMatrix();
    }

    /**
     * Creates a graph from an adjacency matrix.
     *
     * @param adjacencyMatrix The adjacency matrix
     * @param directed True if the graph is directed
     * @param weighted True if the graph is weighted
     */
    public Graph(int[][] adjacencyMatrix, boolean directed, boolean weighted) {
        this.adjacencyMatrix = adjacencyMatrix;
        this.directed = directed;
        this.weighted = weighted;
        this.vertices = buildNodesFromMatrix();
        this.costMatrix = buildCostMatrix();
    }

    /**
     * Creates a graph from a cost matrix.
     *
     * @param costMatrix The cost matrix
     * @param directed True if the graph is directed
     * @param weighted True if the graph is weighted
     * @param isCostMatrix True to indicate this is a cost matrix
     */
    public Graph(int[][] costMatrix, boolean directed, boolean weighted, boolean isCostMatrix) {
        this.directed = directed;
        this.weighted = weighted;
        this.costMatrix = normalizeCostMatrix(costMatrix);
        this.adjacencyMatrix = buildAdjacencyFromCostMatrix(this.costMatrix);
        this.vertices = buildNodesFromMatrix();
    }

    /**
     * Normalizes a cost matrix by replacing zeros with a large value (INF)
     * except for the diagonal.
     *
     * @param input The original cost matrix
     * @return A normalized cost matrix
     */
    private int[][] normalizeCostMatrix(int[][] input) {
        int n = input.length;
        final int INF = 999999;
        int[][] m = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    m[i][j] = 0;
                } else if (input[i][j] == 0) {
                    m[i][j] = INF;
                } else {
                    m[i][j] = input[i][j];
                }
            }
        }
        return m;
    }

    /**
     * Validates if a vertex index is within the graph.
     *
     * @param v The vertex index
     * @return True if valid, false otherwise
     */
    public boolean validateVertex(int v) {
        if (vertices == null || vertices.isEmpty()) {
            System.out.println("❌ The graph has no vertices.");
            return false;
        }
        if (v < 0 || v >= vertices.size()) {
            System.out.println("❌ Vertex " + v + " does not exist.");
            return false;
        }
        return true;
    }

    /**
     * Builds nodes and edges from the adjacency or cost matrices.
     *
     * @return A list of nodes with edges constructed
     */
    private ArrayList<Node> buildNodesFromMatrix() {
        ArrayList<Node> nodes = new ArrayList<>();
        int n = adjacencyMatrix.length;
        final int INF = 999999;

        for (int i = 0; i < n; i++) {
            nodes.add(new Node(i));
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int weight = (costMatrix != null) ? costMatrix[i][j] : adjacencyMatrix[i][j];
                if (weight == 0 || weight == INF) {
                    continue;
                }

                Node from = nodes.get(i);
                Node to = nodes.get(j);

                if (!directed) {
                    if (j < i) {
                        continue; // avoid duplicates
                    }
                    from.addEdge(new Edge(from, to, weight));
                    if (i != j) {
                        to.addEdge(new Edge(to, from, weight));
                    }
                } else {
                    from.addEdge(new Edge(from, to, weight));
                }
            }
        }
        return nodes;
    }

    /**
     * Builds the adjacency matrix from the nodes' edges.
     *
     * @return The adjacency matrix
     */
    private int[][] buildAdjacencyMatrix() {
        int n = vertices.size();
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            Node from = vertices.get(i);
            for (Edge e : from.edges) {
                matrix[i][e.v_f.data] = 1;
            }
        }
        return matrix;
    }

    /**
     * Builds the cost matrix from the nodes' edges.
     *
     * @return The cost matrix
     */
    private int[][] buildCostMatrix() {
        int n = vertices.size();
        int INF = 999999;
        int[][] cost = new int[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(cost[i], INF);
        }
        for (int i = 0; i < n; i++) {
            cost[i][i] = 0;
        }

        for (Node from : vertices) {
            for (Edge e : from.edges) {
                cost[from.data][e.v_f.data] = e.weight;
            }
        }

        return cost;
    }

    /**
     * Builds the adjacency matrix from a cost matrix.
     *
     * @param costMatrix The cost matrix
     * @return The adjacency matrix
     */
    private int[][] buildAdjacencyFromCostMatrix(int[][] costMatrix) {
        int n = costMatrix.length;
        final int INF = 999999;
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = (i != j && costMatrix[i][j] < INF) ? 1 : 0;
            }
        }
        return matrix;
    }

    /**
     * Analyzes the connectivity of the graph.
     * <p>
     * For undirected graphs: determines if the graph is connected. If not, it
     * calculates the number of connected components and the number of vertices
     * in each component.
     * </p>
     * <p>
     * For directed graphs: checks if the graph is strongly connected or weakly
     * connected.
     * </p>
     */
    public void analyzeConnectivity() {
        if (vertices == null || vertices.isEmpty()) {
            System.out.println("❌ The graph has no vertices.");
            return;
        }

        if (!directed) {
            // Undirected graph analysis
            boolean[] visited = new boolean[vertices.size()];
            int componentCount = 0;
            List<Integer> componentSizes = new ArrayList<>();

            for (int i = 0; i < vertices.size(); i++) {
                if (!visited[i]) {
                    int size = dfsCountComponent(i, visited);
                    componentSizes.add(size);
                    componentCount++;
                }
            }

            if (componentCount == 1) {
                System.out.println("✅ The undirected graph is connected.");
            } else {
                System.out.println("❌ The undirected graph is NOT connected.");
                System.out.println("Number of connected components: " + componentCount);
                for (int i = 0; i < componentSizes.size(); i++) {
                    System.out.println("Component " + (i + 1) + " size: " + componentSizes.get(i));
                }
            }
        } else {
            // Directed graph analysis
            boolean strongly = isStronglyConnected();
            boolean weakly = isWeaklyConnected();

            if (strongly) {
                System.out.println("✅ The directed graph is strongly connected.");
            } else if (weakly) {
                System.out.println("⚠️ The directed graph is weakly connected (not strongly).");
            } else {
                System.out.println("❌ The directed graph is NOT connected.");
            }
        }
    }

    /**
     * Performs a DFS to count the size of a connected component (for undirected
     * graphs).
     *
     * @param v The starting vertex index
     * @param visited Array tracking visited vertices
     * @return The number of vertices in this component
     */
    private int dfsCountComponent(int v, boolean[] visited) {
        visited[v] = true;
        int count = 1;

        for (Edge e : vertices.get(v).edges) {
            int neighbor = e.v_f.data;
            if (!visited[neighbor]) {
                count += dfsCountComponent(neighbor, visited);
            }
        }
        return count;
    }

    /**
     * Checks if an undirected graph is connected.
     *
     * @return True if the graph is connected, false otherwise
     */
    private boolean isConnected() {
        if (directed) {
            return false;
        }
        boolean[] visited = new boolean[vertices.size()];
        dfsUtil(0, visited);
        for (boolean v : visited) {
            if (!v) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a directed graph is strongly connected.
     *
     * @return True if strongly connected, false otherwise
     */
    private boolean isStronglyConnected() {
        if (!directed) {
            return false;
        }
        int n = vertices.size();
        for (int i = 0; i < n; i++) {
            boolean[] visited = new boolean[n];
            dfsUtil(i, visited);
            for (boolean v : visited) {
                if (!v) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if a directed graph is weakly connected.
     *
     * @return True if weakly connected, false otherwise
     */
    private boolean isWeaklyConnected() {
        if (!directed) {
            return false;
        }
        boolean[] visited = new boolean[vertices.size()];
        dfsWeak(0, visited);
        for (boolean v : visited) {
            if (!v) {
                return false;
            }
        }
        return true;
    }

    /**
     * Depth-first search utility for connectivity checking in directed or
     * undirected graphs.
     *
     * @param v The starting vertex index
     * @param visited Array to track visited vertices
     */
    private void dfsUtil(int v, boolean[] visited) {
        visited[v] = true;
        for (Edge e : vertices.get(v).edges) {
            int neighbor = e.v_f.data;
            if (!visited[neighbor]) {
                dfsUtil(neighbor, visited);
            }
        }
    }

    /**
     * Depth-first search utility that ignores edge directions (used for weak
     * connectivity).
     *
     * @param v The starting vertex index
     * @param visited Array to track visited vertices
     */
    private void dfsWeak(int v, boolean[] visited) {
        visited[v] = true;
        for (Edge e : vertices.get(v).edges) {
            int neighbor = e.v_f.data;
            if (!visited[neighbor]) {
                dfsWeak(neighbor, visited);
            }
        }
        // Explore edges in the reverse direction
        for (int i = 0; i < vertices.size(); i++) {
            for (Edge e : vertices.get(i).edges) {
                if (e.v_f.data == v && !visited[i]) {
                    dfsWeak(i, visited);
                }
            }
        }
    }

    /**
     * Returns the list of nodes (vertices) in the graph.
     *
     * @return ArrayList of nodes
     */
    public ArrayList<Node> getVertices() {
        return vertices;
    }

    /**
     * Returns the adjacency matrix of the graph.
     *
     * @return 2D array representing the adjacency matrix
     */
    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    /**
     * Returns the cost (weight) matrix of the graph.
     *
     * @return 2D array representing the cost matrix
     */
    public int[][] getCostMatrix() {
        return costMatrix;
    }

    /**
     * Prints the graph to the console.
     * <p>
     * Shows each node and all its edges. If the graph is weighted, the weight
     * of each edge is also printed.
     * </p>
     */
    public void printGraph() {
        System.out.println("----- GRAPH -----");
        for (Node node : vertices) {
            System.out.print("Node " + node.data + " -> ");
            if (node.edges.isEmpty()) {
                System.out.println("(no edges)");
                continue;
            }
            for (Edge edge : node.edges) {
                System.out.print(edge.v_f.data);
                if (weighted) {
                    System.out.print("(" + edge.weight + ")");
                }
                System.out.print("  ");
            }
            System.out.println();
        }
        System.out.println("-----------------\n");
    }

    /**
     * Prints the adjacency matrix of the graph to the console.
     */
    public void printAdjacencyMatrix() {
        System.out.println("----- ADJACENCY MATRIX -----");
        for (int[] row : adjacencyMatrix) {
            for (int val : row) {
                System.out.print(val + "\t");
            }
            System.out.println();
        }
        System.out.println("----------------------------\n");
    }

    /**
     * Prints the cost (weight) matrix of the graph to the console.
     * <p>
     * Uses "INF" to represent infinite costs (no edge).
     * </p>
     */
    public void printCostMatrix() {
        System.out.println("----- COST MATRIX -----");
        int INF = 999999;
        for (int[] row : costMatrix) {
            for (int val : row) {
                System.out.print((val >= INF ? "INF" : val) + "\t");
            }
            System.out.println();
        }
        System.out.println("----------------------\n");
    }

    /**
     * Runs a depth-first search (DFS) traversal starting from a given vertex.
     *
     * @param start The index of the starting vertex
     */
    public void runDFS(int start) {
        TraversalAlgorithms.DFS(this, start);
    }

    /**
     * Runs a breadth-first search (BFS) traversal starting from a given vertex.
     *
     * @param start The index of the starting vertex
     */
    public void runBFS(int start) {
        TraversalAlgorithms.BFS(this, start);
    }

    /**
     * Runs Dijkstra's algorithm for shortest paths from a given vertex.
     *
     * @param start The index of the starting vertex
     */
    public void runDijkstra(int start) {
        ShortestPathAlgorithms.Dijkstra(this, start);
    }

    /**
     * Runs the Bellman-Ford algorithm for shortest paths from a given vertex.
     *
     * @param start The index of the starting vertex
     */
    public void runBellmanFord(int start) {
        ShortestPathAlgorithms.BellmanFord(this, start);
    }

    /**
     * Runs the Floyd-Warshall algorithm to compute shortest paths between all
     * pairs of vertices.
     */
    public void runFloydWarshall() {
        ShortestPathAlgorithms.FloydWarshall(this);
    }

    /**
     * Runs Prim's algorithm to compute the minimum spanning tree starting from
     * a given vertex.
     *
     * @param start The index of the starting vertex
     */
    public void runPrim(int start) {
        MSTAlgorithms.Prim(this, start);
    }

    /**
     * Runs Kruskal's algorithm to compute the minimum spanning tree for the
     * graph.
     */
    public void runKruskal() {
        MSTAlgorithms.Kruskal(this);
    }
}
