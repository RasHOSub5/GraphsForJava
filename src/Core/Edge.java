/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Core;

/**
 * Represents an edge in a graph connecting two nodes (vertices).
 * <p>
 * This class is used in both directed and undirected graphs, as well as
 * weighted or unweighted graphs. Each edge has a start node, an end node, and
 * an optional weight (default is 1).
 * </p>
 *
 * @author Rashid
 */
public class Edge {

    /**
     * Starting vertex of the edge
     */
    public Node v_i;

    /**
     * Ending vertex of the edge
     */
    public Node v_f;

    /**
     * Weight of the edge
     */
    public int weight;

    /**
     * Creates an edge between two nodes with a default weight of 1.
     *
     * @param v_i The starting node
     * @param v_f The ending node
     */
    public Edge(Node v_i, Node v_f) {
        this(v_i, v_f, 1);
    }

    /**
     * Creates an edge between two nodes with a specified weight.
     *
     * @param v_i The starting node
     * @param v_f The ending node
     * @param weight The weight of the edge
     */
    public Edge(Node v_i, Node v_f, int weight) {
        this.v_i = v_i;
        this.v_f = v_f;
        this.weight = weight;
    }

    /**
     * Returns a string representation of the edge.
     *
     * @return A string in the format "(start → end, weight=weight)"
     */
    @Override
    public String toString() {
        return "(" + v_i.data + " -> " + v_f.data + ", weight=" + weight + ")";
    }
}
