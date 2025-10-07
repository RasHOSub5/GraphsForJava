/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Core;

import java.util.*;

/**
 * Provides shortest path algorithms for graphs.
 * <p>
 * Supported algorithms:
 * <ul>
 * <li>Dijkstra (single-source shortest path, non-negative weights)</li>
 * <li>Bellman-Ford (single-source shortest path, supports negative
 * weights)</li>
 * <li>Floyd-Warshall (all-pairs shortest paths)</li>
 * </ul>
 *
 * @author Rashid
 */
public class ShortestPathAlgorithms {

    private static final int INF = 999999;
    private static int[][] floydDist;
    private static int[][] floydPred;

    /**
     * Computes shortest paths from a single source using Dijkstra's algorithm.
     * <p>
     * Assumes all edge weights are non-negative.
     * </p>
     *
     * @param g The graph to traverse
     * @param start The index of the starting vertex
     */
    public static void Dijkstra(Graph g, int start) {
        if (g == null || !g.validateVertex(start)) {
            return;
        }

        int[][] cost = g.getCostMatrix();
        int n = cost.length;

        int[] dist = new int[n];
        boolean[] visited = new boolean[n];
        int[] prev = new int[n];

        Arrays.fill(dist, INF);
        Arrays.fill(prev, -1);
        dist[start] = 0;

        for (int count = 0; count < n - 1; count++) {
            int u = -1, minDist = INF;
            for (int i = 0; i < n; i++) {
                if (!visited[i] && dist[i] < minDist) {
                    minDist = dist[i];
                    u = i;
                }
            }
            if (u == -1) {
                break;
            }
            visited[u] = true;

            for (int v = 0; v < n; v++) {
                if (!visited[v] && cost[u][v] != 0 && cost[u][v] != INF) {
                    if (dist[u] + cost[u][v] < dist[v]) {
                        dist[v] = dist[u] + cost[u][v];
                        prev[v] = u;
                    }
                }
            }
        }

        System.out.println("----- DIJKSTRA from vertex " + start + " -----");
        for (int i = 0; i < n; i++) {
            if (dist[i] == INF) {
                System.out.println("Distance to " + i + " = INF (unreachable)");
            } else {
                System.out.print("Distance to " + i + " = " + dist[i]);
                List<Integer> path = reconstructPath(prev, start, i);
                if (path != null) {
                    System.out.println(" | Path: " + path);
                } else {
                    System.out.println();
                }
            }
        }
        System.out.println("----------------------------------------\n");
    }

    /**
     * Helper method to reconstruct the shortest path from Dijkstra or
     * Bellman-Ford.
     *
     * @param prev Array of previous nodes
     * @param start Start vertex index
     * @param end End vertex index
     * @return List of vertex indices representing the path, or null if
     * unreachable
     */
    private static List<Integer> reconstructPath(int[] prev, int start, int end) {
        if (prev[end] == -1 && start != end) {
            return null;
        }

        List<Integer> path = new ArrayList<>();
        for (int at = end; at != -1; at = prev[at]) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    /**
     * Computes shortest paths from a single source using Bellman-Ford
     * algorithm.
     * <p>
     * Supports negative weight edges and detects negative cycles.
     * </p>
     *
     * @param g The graph to traverse
     * @param start The index of the starting vertex
     */
    public static void BellmanFord(Graph g, int start) {
        if (g == null || !g.validateVertex(start)) {
            return;
        }

        int[][] cost = g.getCostMatrix();
        int n = cost.length;
        int[] dist = new int[n];
        int[] prev = new int[n];
        Arrays.fill(dist, INF);
        Arrays.fill(prev, -1);
        dist[start] = 0;

        for (int k = 0; k < n - 1; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (cost[i][j] != INF && dist[i] != INF && dist[i] + cost[i][j] < dist[j]) {
                        dist[j] = dist[i] + cost[i][j];
                        prev[j] = i;
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (cost[i][j] != INF && dist[i] != INF && dist[i] + cost[i][j] < dist[j]) {
                    System.out.println("⚠️ Negative cycle detected.");
                    return;
                }
            }
        }

        System.out.println("----- BELLMAN-FORD from vertex " + start + " -----");
        for (int i = 0; i < n; i++) {
            if (dist[i] == INF) {
                System.out.println("Distance to " + i + " = INF");
            } else {
                System.out.print("Distance to " + i + " = " + dist[i]);
                List<Integer> path = reconstructPath(prev, start, i);
                if (path != null) {
                    System.out.println(" | Path: " + path);
                } else {
                    System.out.println();
                }
            }
        }
        System.out.println("--------------------------------------------\n");
    }

    /**
     * Computes shortest paths between all pairs of vertices using
     * Floyd-Warshall algorithm.
     * <p>
     * Automatically reconstructs paths and prints the distance matrix. Detects
     * negative cycles.
     * </p>
     *
     * @param g The graph to traverse
     */
    public static void FloydWarshall(Graph g) {
        if (g == null || g.getCostMatrix() == null || g.getCostMatrix().length == 0) {
            System.out.println("❌ The graph has no vertices.");
            return;
        }

        int[][] cost = g.getCostMatrix();
        int n = cost.length;
        floydDist = new int[n][n];
        floydPred = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                floydDist[i][j] = cost[i][j];
                floydPred[i][j] = (i == j || cost[i][j] >= INF) ? -1 : i;
            }
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (floydDist[i][k] != INF && floydDist[k][j] != INF
                            && floydDist[i][k] + floydDist[k][j] < floydDist[i][j]) {
                        floydDist[i][j] = floydDist[i][k] + floydDist[k][j];
                        floydPred[i][j] = floydPred[k][j];
                    }
                }
            }
        }

        boolean hasNegativeCycle = false;
        for (int i = 0; i < n; i++) {
            if (floydDist[i][i] < 0) {
                hasNegativeCycle = true;
                break;
            }
        }

        System.out.println("----- FLOYD-WARSHALL (distance matrix) -----");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print((floydDist[i][j] >= INF ? "INF" : floydDist[i][j]) + "\t");
            }
            System.out.println();
        }
        System.out.println("--------------------------------------------\n");

        if (hasNegativeCycle) {
            System.out.println("⚠️ The graph contains at least one negative cycle. Results may be inconsistent.\n");
        }

        System.out.println("----- SHORTEST PATHS (reconstructed) -----");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && floydDist[i][j] < INF) {
                    List<Integer> path = reconstructFloydPath(i, j);
                    System.out.println("Path (" + i + " → " + j + "): " + path
                            + "  |  Cost = " + floydDist[i][j]);
                }
            }
        }
        System.out.println("--------------------------------------------\n");
    }

    /**
     * Helper method to reconstruct the shortest path from Floyd-Warshall
     * algorithm.
     *
     * @param from Starting vertex index
     * @param to Ending vertex index
     * @return List of vertex indices representing the path, or null if
     * unreachable
     */
    private static List<Integer> reconstructFloydPath(int from, int to) {
        if (floydDist[from][to] >= INF) {
            return null;
        }

        LinkedList<Integer> path = new LinkedList<>();
        int current = to;
        path.addFirst(to);

        while (current != from) {
            current = floydPred[from][current];
            if (current == -1) {
                return null;
            }
            path.addFirst(current);
        }
        return path;
    }
}
