package com.hossam.algorithms;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;

public class PrimAlgorithm {

    public static List<Edge> primAlgorithm(Graph graph, String startNodeId, long durationMillis) {
        long startTime = System.currentTimeMillis();

        List<Edge> mst = new ArrayList<>();
        Set<Node> visited = new HashSet<>();
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(edge -> edge.getNumber("weight")));
        double totalWeight = 0;

        Node startNode = graph.getNode(startNodeId);
        if (startNode == null) {
            System.out.println("Start node not found in the graph.");
            return mst;
        }

        visited.add(startNode);
        priorityQueue.addAll(startNode.leavingEdges().toList());

        while (!priorityQueue.isEmpty()) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - startTime > durationMillis) {
                System.out.println("Time limit exceeded for Prim's algorithm.");
                break;
            }

            Edge edge = priorityQueue.poll();
            assert edge != null;
            Node node = edge.getOpposite(visited.contains(edge.getNode0()) ? edge.getNode0() : edge.getNode1());

            if (!visited.contains(node)) {
                mst.add(edge);
                totalWeight += edge.getNumber("weight");
                visited.add(node);
                for (Edge adjacentEdge : node.leavingEdges().toList()) {
                    if (!visited.contains(adjacentEdge.getOpposite(node))) {
                        priorityQueue.add(adjacentEdge);
                    }
                }
            }
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("Total weight of MST using Prim's algorithm: " + totalWeight+"\n");
        System.out.println("Prim's algorithm execution time: " + duration + " milliseconds"+"\n");

        return mst;
    }
}
