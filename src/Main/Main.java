/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Core.*;
import java.util.*;

/**
 * Playground for testing all graph utilities: - Directed / undirected -
 * Weighted / unweighted - Traversals (DFS/BFS) - Shortest paths (Dijkstra,
 * Bellman-Ford, Floyd-Warshall) - Minimum Spanning Trees (Prim, Kruskal)
 *
 * @author Rashid
 */
public class Main {

    public static void main(String[] args) {
        // ==========================================
        // GRAPH 1 - Undirected
        // ==========================================
        // V(G1) = {0,1,2,3}
        // E(G1) = {(0,1), (0,3), (1,2), (1,3)}
        int[][] adjacencyG1 = {
            {0, 1, 0, 1}, // 0
            {1, 0, 1, 1}, // 1
            {0, 1, 0, 0}, // 2
            {1, 1, 0, 0} // 3
        };

        Graph G1 = new Graph(adjacencyG1, false, false);

        System.out.println("===============================");
        System.out.println("GRAPH 1 (Undirected)");
        System.out.println("===============================");
        G1.printGraph();
        G1.printAdjacencyMatrix();

        // ==========================================
        // GRAPH 2 - Directed
        // ==========================================
        // V(G2) = {0,1,2,3,4}
        // E(G2) = {(0,1), (1,0), (1,2), (2,3), (4,0)}
        int[][] adjacencyG2 = {
            {0, 1, 0, 0, 0}, // 0
            {1, 0, 1, 0, 0}, // 1
            {0, 0, 0, 1, 0}, // 2
            {0, 0, 0, 0, 0}, // 3
            {1, 0, 0, 0, 0} // 4
        };

        Graph G2 = new Graph(adjacencyG2, true, false);

        System.out.println("===============================");
        System.out.println("GRAPH 2 (Directed)");
        System.out.println("===============================");
        G2.printGraph();
        G2.printAdjacencyMatrix();

        // ==========================================
        // Example of node-based graph (GRAPH 1)
        // ==========================================
        Node n0 = new Node(0);
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);

        n0.addEdge(new Edge(n0, n1, 1));
        n0.addEdge(new Edge(n0, n3, 1));
        n1.addEdge(new Edge(n1, n2, 1));
        n1.addEdge(new Edge(n1, n3, 1));

        ArrayList<Node> verticesG1 = new ArrayList<>(Arrays.asList(n0, n1, n2, n3));
        Graph G3 = new Graph(verticesG1, false, false);

        System.out.println("===============================");
        System.out.println("GRAPH 1 (Undirected - Node based)");
        System.out.println("===============================");
        G3.printGraph();
        G3.printAdjacencyMatrix();

        // ==========================================
        // Example of node-based graph (GRAPH 2)
        // ==========================================
        Node m0 = new Node(0);
        Node m1 = new Node(1);
        Node m2 = new Node(2);
        Node m3 = new Node(3);
        Node m4 = new Node(4);

        m0.addEdge(new Edge(m0, m1, 1));
        m1.addEdge(new Edge(m1, m0, 1));
        m1.addEdge(new Edge(m1, m2, 1));
        m2.addEdge(new Edge(m2, m3, 1));
        m4.addEdge(new Edge(m4, m0, 1));

        ArrayList<Node> verticesG2 = new ArrayList<>(Arrays.asList(m0, m1, m2, m3, m4));
        Graph G4 = new Graph(verticesG2, true, false);

        System.out.println("===============================");
        System.out.println("GRAPH 2 (Directed - Node based)");
        System.out.println("===============================");
        G4.printGraph();
        G4.printAdjacencyMatrix();

        // ====== Weighted Directed Graph Example ======
        int[][] costMatrix = {
            {0, 1, 0, 0, 0},
            {0, 0, 2, 0, 0},
            {0, 6, 0, 0, 2},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0}
        };

        Graph G5 = new Graph(costMatrix, true, true, true);

        System.out.println("===============================");
        System.out.println("GRAPH 3 (Directed & Weighted)");
        System.out.println("===============================");
        G5.printGraph();
        G5.printAdjacencyMatrix();
        G5.printCostMatrix();

        // Optional: connectivity analysis
        G5.analyzeConnectivity();

        // ====== ADJACENCY MATRIX ======
        int[][] adjacencyMatrix = {
            {0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
            {0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0},
            {0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0}
        };

        // ====== BUILD GRAPH ======
        // undirected = false, unweighted = false
        Graph G6 = new Graph(adjacencyMatrix, false, false);

        // ====== PRINT GRAPH ======
        System.out.println("===============================");
        System.out.println("GRAPH 4 (Undirected & Unweighted)");
        System.out.println("===============================");
        G6.printGraph();
        G6.printAdjacencyMatrix();

        // ====== TRAVERSALS ======
        System.out.println("===== TRAVERSALS =====");
        System.out.println("DFS starting from vertex 0:");
        G6.runDFS(0);
        System.out.println("\nBFS starting from vertex 0:");
        G6.runBFS(0);

        // ====== WEIGHTED GRAPH ======
        int[][] costMatrix2 = {
            {0, 3, 8, 5, 0, 0, 0, 0, 0, 0},
            {3, 0, 5, 0, 0, 7, 0, 0, 0, 0},
            {8, 5, 0, 2, 8, 5, 0, 0, 0, 0},
            {5, 0, 2, 0, 0, 0, 4, 0, 0, 0},
            {0, 0, 8, 0, 0, 5, 6, 1, 3, 0},
            {0, 7, 5, 0, 5, 0, 0, 6, 0, 0},
            {0, 0, 0, 4, 6, 0, 0, 0, 4, 0},
            {0, 0, 0, 0, 1, 6, 0, 0, 0, 2},
            {0, 0, 0, 0, 3, 0, 4, 0, 0, 6},
            {0, 0, 0, 0, 0, 0, 0, 2, 6, 0}
        };

        // false = undirected, true = weighted, true = cost matrix
        Graph G7 = new Graph(costMatrix2, false, true, true);

        // ====== PRINT GRAPH ======
        System.out.println("===============================");
        System.out.println("GRAPH 5 (Undirected & Weighted)");
        System.out.println("===============================");
        G7.printGraph();
        G7.printCostMatrix();

        // ====== DIJKSTRA ======
        System.out.println("===== DIJKSTRA =====");
        System.out.println("Starting from vertex A (index 0):");
        G7.runDijkstra(0);

        // ====== NODE-BASED DIRECTED GRAPH ======
        ArrayList<Node> vertices = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            vertices.add(new Node(i));
        }

        vertices.get(0).addEdge(new Edge(vertices.get(0), vertices.get(1), 10));
        vertices.get(1).addEdge(new Edge(vertices.get(1), vertices.get(3), 2));
        vertices.get(3).addEdge(new Edge(vertices.get(3), vertices.get(2), -2));
        vertices.get(2).addEdge(new Edge(vertices.get(2), vertices.get(1), 1));
        vertices.get(0).addEdge(new Edge(vertices.get(0), vertices.get(5), 8));
        vertices.get(5).addEdge(new Edge(vertices.get(5), vertices.get(4), 1));
        vertices.get(4).addEdge(new Edge(vertices.get(4), vertices.get(1), -4));
        vertices.get(4).addEdge(new Edge(vertices.get(4), vertices.get(3), -1));

        Graph G8 = new Graph(vertices, true, true); // directed & weighted

        System.out.println("===============================");
        System.out.println("GRAPH 6 (Directed & Weighted - Node based)");
        System.out.println("===============================");
        G8.printGraph();
        G8.printAdjacencyMatrix();
        G8.printCostMatrix();

        // ====== BELLMAN-FORD ======
        System.out.println("===== BELLMAN-FORD =====");
        System.out.println("Starting from vertex 1 (index 0):");
        G8.runBellmanFord(0);

        // ====== SIMPLE WEIGHTED GRAPH (4 vertices) ======
        int[][] costMatrix3 = {
            {0, 1, 5, 2},
            {1, 0, 0, 0},
            {5, 0, 0, 3},
            {2, 0, 3, 0}
        };
        Graph G9 = new Graph(costMatrix3, false, true, true);

        System.out.println("===============================");
        System.out.println("GRAPH 7 (Simple Weighted, 4 vertices)");
        System.out.println("===============================");
        G9.printGraph();
        G9.printAdjacencyMatrix();
        G9.printCostMatrix();

        // ====== MST ======
        System.out.println("\n=== PRIM (starting from vertex 1) ===");
        G9.runPrim(0);

        System.out.println("\n=== KRUSKAL ===");
        G9.runKruskal();

        // ====== UNDIRECTED WEIGHTED GRAPH (larger) ======
        int[][] costMatrix4 = {
            {0, 8, 0, 0, 1, 5, 6},
            {8, 0, 4, 0, 6, 0, 0},
            {0, 4, 0, 7, 2, 0, 0},
            {0, 0, 7, 0, 9, 0, 0},
            {1, 6, 2, 9, 0, 0, 5},
            {5, 0, 0, 0, 0, 0, 3},
            {6, 0, 0, 0, 5, 3, 0}
        };
        Graph G10 = new Graph(costMatrix4, false, true, true);

        System.out.println("===============================");
        System.out.println("GRAPH 8 (Undirected Weighted - MST example)");
        System.out.println("===============================");
        G10.printGraph();
        G10.printAdjacencyMatrix();
        G10.printCostMatrix();

        System.out.println("\n=== PRIM (starting from vertex A = 0) ===");
        G10.runPrim(0);

        System.out.println("\n=== KRUSKAL ===");
        G10.runKruskal();

        System.out.println("===============================");
        System.out.println("GRAPH G11 (Directed & Weighted)");
        System.out.println("===============================");

        System.out.println("===============================");
        System.out.println("G11 = (V,E) - Directed & Weighted");
        System.out.println("===============================");
        System.out.println("V = {1,2,3,4,5,6}  (indices 0..5)");
        System.out.println("E = {(1,2,10),(1,3,5),(4,1,60),(4,3,25),(3,2,10),(2,5,30),(3,5,15),(3,6,10),(6,4,20),(6,5,20)}\n");

        // =====================================================
        // NODE DEFINITION (indices 0..5 representing 1..6)
        // =====================================================
        ArrayList<Node> vertices2 = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            vertices2.add(new Node(i));
        }

        // =====================================================
        // EDGE DEFINITION (directed & weighted)
        // =====================================================
        vertices2.get(0).addEdge(new Edge(vertices2.get(0), vertices2.get(1), 10));
        vertices2.get(0).addEdge(new Edge(vertices2.get(0), vertices2.get(2), 5));
        vertices2.get(3).addEdge(new Edge(vertices2.get(3), vertices2.get(0), 60));
        vertices2.get(3).addEdge(new Edge(vertices2.get(3), vertices2.get(2), 25));
        vertices2.get(2).addEdge(new Edge(vertices2.get(2), vertices2.get(1), 10));
        vertices2.get(1).addEdge(new Edge(vertices2.get(1), vertices2.get(4), 30));
        vertices2.get(2).addEdge(new Edge(vertices2.get(2), vertices2.get(4), 15));
        vertices2.get(2).addEdge(new Edge(vertices2.get(2), vertices2.get(5), 10));
        vertices2.get(5).addEdge(new Edge(vertices2.get(5), vertices2.get(3), 20));
        vertices2.get(5).addEdge(new Edge(vertices2.get(5), vertices2.get(4), 20));

        // =====================================================
        // BUILD GRAPH
        // =====================================================
        Graph G11 = new Graph(vertices2, true, true); // directed & weighted

        // ====== PRINT GRAPH ======
        G11.printGraph();

        // ====== PRINT ADJACENCY AND COST MATRICES ======
        G11.printAdjacencyMatrix();
        G11.printCostMatrix();

        // ====== RUN DIJKSTRA FROM VERTEX 1 (index 0) ======
        System.out.println("\n=== DIJKSTRA from vertex 1 ===");
        ShortestPathAlgorithms.Dijkstra(G11, 0);

        // ====== RUN FLOYD–WARSHALL WITH AUTOMATIC PATH RECONSTRUCTION ======
        System.out.println("\n=== FLOYD–WARSHALL ===");
        ShortestPathAlgorithms.FloydWarshall(G11);

        // ====== BFS FROM VERTEX 1 ======
        System.out.println("\n=== BFS (from vertex 1) ===");
        TraversalAlgorithms.BFS(G11, 0);

        // ====== DFS FROM VERTEX 1 ======
        System.out.println("\n=== DFS (from vertex 1) ===");
        TraversalAlgorithms.DFS(G11, 0);

        // =====================================================
        // UNDIRECTED WEIGHTED GRAPH (G12)
        // =====================================================
        ArrayList<Node> vertices3 = new ArrayList<>();
        for (int i = 0; i < 9; i++) { // A=0 ... I=8
            vertices3.add(new Node(i));
        }

        // =====================================================
        // EDGE DEFINITION (undirected & weighted)
        // =====================================================
        Node.addUndirectedEdge(vertices3.get(8), vertices3.get(5), 18); // (I,F,18)
        Node.addUndirectedEdge(vertices3.get(8), vertices3.get(6), 2);  // (I,G,2)
        Node.addUndirectedEdge(vertices3.get(8), vertices3.get(7), 4);  // (I,H,4)
        Node.addUndirectedEdge(vertices3.get(5), vertices3.get(6), 14); // (F,G,14)
        Node.addUndirectedEdge(vertices3.get(6), vertices3.get(7), 3);  // (G,H,3)
        Node.addUndirectedEdge(vertices3.get(7), vertices3.get(2), 1);  // (H,C,1)
        Node.addUndirectedEdge(vertices3.get(7), vertices3.get(4), 5);  // (H,E,5)
        Node.addUndirectedEdge(vertices3.get(6), vertices3.get(4), 11); // (G,E,11)
        Node.addUndirectedEdge(vertices3.get(6), vertices3.get(3), 17); // (G,D,17)
        Node.addUndirectedEdge(vertices3.get(5), vertices3.get(3), 16); // (F,D,16)
        Node.addUndirectedEdge(vertices3.get(5), vertices3.get(1), 12); // (F,B,12)
        Node.addUndirectedEdge(vertices3.get(3), vertices3.get(1), 8);  // (D,B,8)
        Node.addUndirectedEdge(vertices3.get(4), vertices3.get(3), 19); // (E,D,19)
        Node.addUndirectedEdge(vertices3.get(4), vertices3.get(0), 15); // (E,A,15)
        Node.addUndirectedEdge(vertices3.get(3), vertices3.get(0), 10); // (D,A,10)
        Node.addUndirectedEdge(vertices3.get(4), vertices3.get(2), 9);  // (E,C,9)
        Node.addUndirectedEdge(vertices3.get(0), vertices3.get(1), 7);  // (A,B,7)
        Node.addUndirectedEdge(vertices3.get(0), vertices3.get(2), 6);  // (A,C,6)
        Node.addUndirectedEdge(vertices3.get(1), vertices3.get(2), 13); // (B,C,13)

        // =====================================================
        // BUILD GRAPH (undirected & weighted)
        // =====================================================
        Graph G12 = new Graph(vertices3, false, true);

        // ====== PRINT GRAPH ======
        System.out.println("===================================");
        System.out.println("GRAPH G12 (Undirected & Weighted)");
        System.out.println("===================================\n");

        G12.printGraph();
        G12.printAdjacencyMatrix();
        G12.printCostMatrix();

        // ====== TRAVERSALS ======
        System.out.println("===== TRAVERSALS =====");
        System.out.println("DFS from A (vertex 0):");
        G12.runDFS(0);

        System.out.println("\nBFS from F (vertex 5):");
        G12.runBFS(5);

        System.out.println("\nDFS from G (vertex 7):");
        G12.runDFS(7);

        System.out.println("\nBFS from C (vertex 2):");
        G12.runBFS(2);

        // ====== MINIMUM SPANNING TREES ======
        System.out.println("\n===== MINIMUM SPANNING TREES =====");
        System.out.println("Prim from D (vertex 3):");
        G12.runPrim(3);

        System.out.println("\nKruskal for the entire graph:");
        G12.runKruskal();

        System.out.println("Prim from H (vertex 8):");
        G12.runPrim(8);
    }
}
