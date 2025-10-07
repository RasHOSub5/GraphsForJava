/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Core;

import java.util.*;

/**
 * Provides graph traversal algorithms.
 * <p>
 * Supports:
 * <ul>
 * <li>DFS (Depth-First Search)</li>
 * <li>BFS (Breadth-First Search)</li>
 * </ul>
 *
 * @author Rashid
 */
public class TraversalAlgorithms {

    /**
     * Performs a depth-first search (DFS) traversal starting from a given
     * vertex.
     *
     * @param g The graph to traverse
     * @param start The index of the starting vertex
     */
    public static void DFS(Graph g, int start) {
        if (g == null || !g.validateVertex(start)) {
            return;
        }

        ArrayList<Node> vertices = g.getVertices();
        Node startNode = vertices.get(start);
        HashSet<Node> visited = new HashSet<>();

        System.out.print("DFS from vertex " + startNode.data + ": ");
        dfsRecursive(startNode, visited);
        System.out.println();
    }

    /**
     * Recursive helper method for DFS traversal.
     *
     * @param node The current node being visited
     * @param visited Set of already visited nodes
     */
    private static void dfsRecursive(Node node, HashSet<Node> visited) {
        visited.add(node);
        System.out.print(node.data + " ");
        for (Edge e : node.edges) {
            Node neighbor = e.v_f;
            if (!visited.contains(neighbor)) {
                dfsRecursive(neighbor, visited);
            }
        }
    }

    /**
     * Performs a breadth-first search (BFS) traversal starting from a given
     * vertex.
     *
     * @param g The graph to traverse
     * @param start The index of the starting vertex
     */
    public static void BFS(Graph g, int start) {
        if (g == null || !g.validateVertex(start)) {
            return;
        }

        ArrayList<Node> vertices = g.getVertices();
        Node startNode = vertices.get(start);
        HashSet<Node> visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();

        visited.add(startNode);
        queue.add(startNode);

        System.out.print("BFS from vertex " + startNode.data + ": ");
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            System.out.print(current.data + " ");

            for (Edge e : current.edges) {
                Node neighbor = e.v_f;
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        System.out.println();
    }
}
