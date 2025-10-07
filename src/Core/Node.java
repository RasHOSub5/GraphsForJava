/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Core;

import java.util.*;

/**
 * Represents a node (vertex) in a graph.
 * <p>
 * Each node contains a unique identifier (data) and a list of edges connecting
 * it to other nodes. Supports both directed and undirected graphs, as well as
 * weighted edges.
 * </p>
 *
 * <p>
 * Includes utility methods to add edges and to add undirected edges between two
 * nodes.
 * </p>
 *
 * @author Rashid
 */
public class Node {

    /**
     * Unique identifier or value of the node
     */
    public int data;

    /**
     * List of edges connected to this node
     */
    public ArrayList<Edge> edges;

    /**
     * Creates a node with a given identifier and an empty list of edges.
     *
     * @param data The unique identifier for the node
     */
    public Node(int data) {
        this.data = data;
        this.edges = new ArrayList<>();
    }

    /**
     * Creates a node with a given identifier and a predefined list of edges.
     *
     * @param data The unique identifier for the node
     * @param edges List of edges to initialize the node with
     */
    public Node(int data, ArrayList<Edge> edges) {
        this.data = data;
        this.edges = edges;
    }

    /**
     * Adds an edge to this node's list of edges.
     *
     * @param e The edge to add
     */
    public void addEdge(Edge e) {
        edges.add(e);
    }

    /**
     * Adds an undirected edge between two nodes.
     * <p>
     * This method automatically creates an edge from u to v and from v to u.
     * </p>
     *
     * @param u The first node
     * @param v The second node
     * @param weight The weight of the edge
     */
    public static void addUndirectedEdge(Node u, Node v, int weight) {
        u.addEdge(new Edge(u, v, weight));
        v.addEdge(new Edge(v, u, weight));
    }

    /**
     * Returns a string representation of the node, including its identifier and
     * all connected edges.
     *
     * @return A string in the format "Node data → [edges]"
     */
    @Override
    public String toString() {
        return "Node " + data + " -> " + edges;
    }
}
