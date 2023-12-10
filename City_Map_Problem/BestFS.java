// Best First Search is nothing but the BFS + Heuristics.
// here, priority queue is used instead of simple queue.
// here, heuristics is taken as pre-determined distance of
// cities from Bucharest

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class BestFS{

    // Using HashMap to store the edges of the graph
    Map<String, List<String>> graph;

    // HashMap to maintain the heiristics
    Map<String, Integer> heuristics;

    public BestFS(){
        this.graph = new HashMap<>();
        this.heuristics = new HashMap<>();
    }

    // Adding city of the graph
    public void addNode(String city, int distance){
        graph.put(city, new ArrayList<>());
        heuristics.put(city, distance);
    }

    // Connecting cities of the graph
    public void addEdge(String source, String destination){
        graph.get(source).add(destination);
    }

    // checking if there exists any path from source to destination anf if there then returning the path
    public List<String> solution(String source, String destination){

        Set<String> visited = new HashSet<>();
        Map<String, String> parentMap = new HashMap<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(new NodeCompare());

        visited.add(source);
        queue.add(new Node(source, heuristics.get(source)));
        parentMap.put(source, null);

        while (!queue.isEmpty()) {
            
            Node cur = queue.poll();

            if(cur.city.equals(destination)){
                return way(parentMap, cur.city);
            }

            Iterator<String> i = graph.get(cur.city).iterator();

            while (i.hasNext()) {

                String city = i.next();

                if(!visited.contains(city)){

                    visited.add(city);
                    queue.add(new Node(city, heuristics.get(city)));
                    parentMap.put(city, cur.city);
                }
            }
        }

        return null;
    } 

    // Constructing path from source to destination
    public static List<String> way(Map<String, String> parentMap, String city){

        List<String> path = new ArrayList<>();
        path.add(city);

        while (parentMap.get(city) != null) {
            String parent = parentMap.get(city);
            path.add(parent);
            city = parent;
        }

        // Reversing path bc while loop ran from goal state to initial state.
        Collections.reverse(path);
        return path;
    }

     // object Node to store city and it's heuristics
    private class Node{
        String city;
        int distance;

        public Node(String city, int distance){
            this.city = city;
            this.distance = distance;
        }
    }

    public static class NodeCompare implements Comparator<Node>{

        @Override
        public int compare(Node node1, Node node2){
            return Integer.compare(node1.distance, node2.distance);
        }
    }

    public static void main(String[] args) {
        
        BestFS graph = new BestFS();

        graph.addNode("Arad", 366);
        graph.addNode("Bucharest", 0);
        graph.addNode("Craiova", 160);
        graph.addNode("Drobeta", 242);
        graph.addNode("Eforie", 161);
        graph.addNode("Fagaras", 178);
        graph.addNode("Giurgiu", 77);
        graph.addNode("Hirsova", 151);
        graph.addNode("Iasi", 226);
        graph.addNode("Lugoj", 244);
        graph.addNode("Mehadia", 241);
        graph.addNode("Neamt", 234);
        graph.addNode("Oradea", 380);
        graph.addNode("Pitesti", 98);
        graph.addNode("Rimnicu", 193);
        graph.addNode("Sibiu", 253);
        graph.addNode("Timisoara", 329);
        graph.addNode("Urziceni", 80);
        graph.addNode("Vaslui", 199);
        graph.addNode("Zerind", 374);


        graph.addEdge("Arad", "Zerind");
        graph.addEdge("Zerind", "Oradea");
        graph.addEdge("Oradea", "Sibiu");
        graph.addEdge("Arad", "Timisoara");
        graph.addEdge("Arad", "Sibiu");
        graph.addEdge("Sibiu", "Fagaras");
        graph.addEdge("Timisoara", "Lugoj");
        graph.addEdge("Lugoj", "Mehadia");
        graph.addEdge("Mehadia", "Drobeta");
        graph.addEdge("Drobeta", "Craiova");
        graph.addEdge("Craiova", "Rimnicu");
        graph.addEdge("Sibiu", "Rimnicu");
        graph.addEdge("Sibiu", "Fagaras");
        graph.addEdge("Rimnicu", "Pitesti");
        graph.addEdge("Pitesti", "Bucharest");
        graph.addEdge("Bucharest", "Giurgiu");
        graph.addEdge("Fagaras", "Bucharest");
        graph.addEdge("Bucharest", "Urziceni");
        graph.addEdge("Urziceni", "Hirsova");
        graph.addEdge("Hirsova", "Eforie");
        graph.addEdge("Urziceni", "Vaslui");
        graph.addEdge("Vaslui", "Iasi");
        graph.addEdge("Iasi", "Neamt");

        System.out.println("Using Best First Search");
        System.out.println(graph.solution("Arad", "Bucharest"));
    }
}