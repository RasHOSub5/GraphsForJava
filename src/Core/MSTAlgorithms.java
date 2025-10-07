/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Core;

import java.util.*;

/**
 * Provides Minimum Spanning Tree (MST) algorithms for undirected weighted
 * graphs.
 * <p>
 * Supported algorithms:
 * <ul>
 * <li>Prim's algorithm</li>
 * <li>Kruskal's algorithm (using Union-Find)</li>
 * </ul>
 * Note: Applicable only for undirected and weighted graphs.
 *
 * @author Rashid
 */
public class MSTAlgorithms {

    private static final int INF = 999999;

    /**
     * Computes the Minimum Spanning Tree of a graph using Prim's algorithm.
     * <p>
     * If the graph is disconnected, it computes the MST for each connected
     * component and prints their respective total weights.
     * </p>
     *
     * @param g The graph (must be undirected and weighted)
     * @param start The index of the starting vertex
     */
    public static void Prim(Graph g, int start) {
        if (g == null || !g.validateVertex(start)) {
            return;
        }
        if (g.directed) {
            System.out.println("⚠️ Prim's algorithm does not apply to directed graphs.");
            return;
        }
        if (!g.weighted) {
            System.out.println("⚠️ Prim's algorithm requires a weighted graph.");
            return;
        }

        int n = g.getVertices().size();
        boolean[] visitedGlobal = new boolean[n];
        final int[][] cost = g.getCostMatrix();

        int componentId = 0;
        for (int i = 0; i < n; i++) {
            if (!visitedGlobal[i]) {
                componentId++;
                boolean[] inMST = new boolean[n];
                int[] key = new int[n];
                int[] parent = new int[n];

                Arrays.fill(key, INF);
                Arrays.fill(parent, -1);
                key[i] = 0;

                for (int count = 0; count < n - 1; count++) {
                    int u = minKey(key, inMST);
                    if (u == -1) {
                        break;
                    }
                    inMST[u] = true;
                    visitedGlobal[u] = true;

                    for (int v = 0; v < n; v++) {
                        if (cost[u][v] != INF && !inMST[v] && cost[u][v] < key[v]) {
                            key[v] = cost[u][v];
                            parent[v] = u;
                        }
                    }
                }

                // Mostrar MST del componente
                System.out.println("----- MST Component " + componentId + " (Prim) -----");
                int totalCost = 0;
                for (int v = 0; v < n; v++) {
                    if (parent[v] != -1) {
                        int u = parent[v];
                        System.out.println(u + " — " + v + "  (weight " + cost[u][v] + ")");
                        totalCost += cost[u][v];
                    }
                }
                System.out.println("Total weight of component " + componentId + ": " + totalCost);
                System.out.println("--------------------------------------------\n");
            }
        }
    }

    /**
     * Helper function for Prim's algorithm. Finds the vertex with the minimum
     * key value not yet included in the MST.
     *
     * @param key Array of key values
     * @param inMST Array indicating which vertices are already in the MST
     * @return Index of the vertex with the minimum key
     */
    private static int minKey(int[] key, boolean[] inMST) {
        int min = INF, minIndex = -1;
        for (int v = 0; v < key.length; v++) {
            if (!inMST[v] && key[v] < min) {
                min = key[v];
                minIndex = v;
            }
        }
        return minIndex;
    }

    /**
     * Computes the Minimum Spanning Tree of a graph using Kruskal's algorithm.
     * <p>
     * If the graph is disconnected, it computes the MST for each connected
     * component and prints their respective total weights.
     * </p>
     *
     * @param g The graph (must be undirected and weighted)
     */
    public static void Kruskal(Graph g) {
        if (g == null || g.getVertices() == null) {
            return;
        }
        if (g.directed) {
            System.out.println("⚠️ Kruskal's algorithm does not apply to directed graphs.");
            return;
        }
        if (!g.weighted) {
            System.out.println("⚠️ Kruskal's algorithm requires a weighted graph.");
            return;
        }

        ArrayList<Edge> edges = new ArrayList<>();
        for (Node node : g.getVertices()) {
            edges.addAll(node.edges);
        }
        edges.sort(Comparator.comparingInt(e -> e.weight));

        int n = g.getVertices().size();
        UnionFind uf = new UnionFind(n);

        int componentId = 0;
        boolean[] processed = new boolean[n]; // track vertices already in an MST

        for (int i = 0; i < n; i++) {
            if (!processed[i]) {
                componentId++;
                ArrayList<Edge> mst = new ArrayList<>();
                int totalCost = 0;

                for (Edge e : edges) {
                    int u = e.v_i.data;
                    int v = e.v_f.data;
                    if (!processed[u] || !processed[v]) {
                        if (uf.find(u) != uf.find(v)) {
                            uf.union(u, v);
                            mst.add(e);
                            totalCost += e.weight;
                            processed[u] = processed[v] = true;
                        }
                    }
                }

                System.out.println("----- MST Component " + componentId + " (Kruskal) -----");
                for (Edge e : mst) {
                    System.out.println(e.v_i.data + " — " + e.v_f.data + "  (weight " + e.weight + ")");
                }
                System.out.println("Total weight of component " + componentId + ": " + totalCost);
                System.out.println("--------------------------------------------\n");
            }
        }
    }

    /**
     * Union-Find (Disjoint Set) helper class used for Kruskal's algorithm.
     */
    private static class UnionFind {

        int[] parent, rank;

        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        /**
         * Finds the representative (root) of the set that x belongs to.
         *
         * @param x The element index
         * @return Root index of the set
         */
        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        /**
         * Merges the sets containing x and y.
         *
         * @param x Element in the first set
         * @param y Element in the second set
         */
        void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX == rootY) {
                return;
            }

            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }
}
