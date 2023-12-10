// We are trying to find a way from Arad
// to bukarest using Breadth First Search

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class BFS {

    // Using HashMap to store the edges of the graph
    Map<String, List<String>> graph;

    public BFS(){
        this.graph = new HashMap<>();
    }

    // Adding city of the graph
    public void addNode(String city){
        if(!graph.containsKey(city)){
            graph.put(city, new ArrayList<String>());
        }
    }

    // Connecting cities of the graph
    public void addEdge(String source, String destination){
        if(graph.containsKey(source) && graph.containsKey(destination)){
            graph.get(source).add(destination);
            graph.get(destination).add(source);
        }
    }

    // Printing the HashMap
    public void print(){
        for(Map.Entry<String, List<String>> entry : graph.entrySet()){
            System.out.print(entry.getKey() + ":" );

            for (String neighbor : entry.getValue()) {
                System.out.print(neighbor + " ");
            }

            System.out.println();
        }
    }

    // BSF traversal of the Graph
    public void bsftravel(String source){

        // for keeping the track of visited states
        HashSet<String> visited = new HashSet<>();

        Queue<String> queue = new LinkedList<>();

        queue.add(source);
        visited.add(source);

        while (!queue.isEmpty()) {

            String city = queue.poll();
            System.out.println(city);

            Iterator<String> i = graph.get(city).iterator();

            while (i.hasNext()) {

                String a = i.next();

                if(!visited.contains(a)){
                    queue.add(a);
                    visited.add(a);
                }
            }
        }

    }

    // checking if there exists any path from source to destination anf if there then returning the path
    public List<String> canReach(String source, String destination){

        // for keeping the track of visited states  
        Set<String> visited = new HashSet<>();

        Queue<String> queue = new LinkedList<>();

        //for keeping track of child and parent states.
        Map<String, String> parentMap = new HashMap<>();

        queue.add(source);
        visited.add(source);

        // parent of initial state will be null;
        parentMap.put(source, null);

        while (!queue.isEmpty()) {

            String cur = queue.poll();
            
            if(cur == destination){
                return reconstructpath(parentMap, cur);
            }

            Iterator<String> i = graph.get(cur).iterator();

            while (i.hasNext()) {

                String city = i.next();

                if(!visited.contains(city)){

                    queue.add(city);
                    visited.add(city);
                    parentMap.put(city, cur);
                }
            }
        }
        return null;
    }

    // Constructing path from source to destination
    public List<String> reconstructpath(Map<String, String> parentMap, String city){

        List<String> path = new ArrayList<>();
        String cur = city;
        path.add(cur);

        while (parentMap.get(cur) != null) {
            String parent = parentMap.get(cur);
            path.add(parent);
            cur = parent;
        }

        // Reversing path bc while loop ran from goal state to initial state.
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        BFS graph = new BFS();

        graph.addNode("Arad");
        graph.addNode("Zerind");
        graph.addNode("Oradea");
        graph.addNode("Timisoara");
        graph.addNode("Sibiu");
        graph.addNode("Fagaras");
        graph.addNode("Rimnicu");
        graph.addNode("Lugoj");
        graph.addNode("Mehadia");
        graph.addNode("Drobeta");
        graph.addNode("Craiova");
        graph.addNode("Pitesti");
        graph.addNode("Bucharest");
        graph.addNode("Giurgiu");
        graph.addNode("Urziceni");
        graph.addNode("Hirsova");
        graph.addNode("Eforie");
        graph.addNode("Vaslui");
        graph.addNode("Iasi");
        graph.addNode("Neamt");
        graph.addNode("parli");


        graph.addEdge("Arad", "Zerind");
        graph.addEdge("Zerind", "Oradea");
        graph.addEdge("Oradea", "Sibiu");
        graph.addEdge("Arad", "Timisoara");
        graph.addEdge("Arad", "Sibiu");
        graph.addEdge("Sibiu", "Fagaras");
        graph.addEdge("Timisoara", "Lugoj");
        graph.addEdge("Mehadia", "Drobeta");
        graph.addEdge("Lugoj", "Mehadia");
        graph.addEdge("Drobeta", "Craiova");
        graph.addEdge("Craiova", "Rimnicu");
        graph.addEdge("Rimnicu", "Sibiu");
        graph.addEdge("sibiu", "Fagaras");
        graph.addEdge("Pitesti", "Rimnicu");
        graph.addEdge("Pitesti", "Bucharest");
        graph.addEdge("Bucharest", "Giurgiu");
        graph.addEdge("Fagaras", "Bucharest");
        graph.addEdge("Bucharest", "Urziceni");
        graph.addEdge("Urziceni", "Hirsova");
        graph.addEdge("Hirsova", "Eforie");
        graph.addEdge("Urziceni", "Vaslui");
        graph.addEdge("Vaslui", "Iasi");
        graph.addEdge("Iasi", "Neamt");

        List<String> path = graph.canReach("Arad", "Bucharest");

        System.out.println("Using Breadth First Search");
        System.out.println(path);
    }
}
