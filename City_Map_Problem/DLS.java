// Depth Limited Search (DLS) is nothing but the DFS + Limit
// Here, Depth which DLS explores is restricted with Limit 
// to avoid infinite state problem

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class DLS {

    // Using HashMap to store the edges of the graph
    private Map<String, List<String>> graph;

    public DLS(){
        this.graph = new HashMap<>();
    }

    // class node to store city and depth
    public static class node{

        String name;
        int depth;

        public node(String name, int depth){
            this.name = name;
            this.depth = depth;
        }
    }

    // Adding cities of the graph
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
            System.out.print(entry.getKey() + ": ");

            for (String neighbor : entry.getValue()) {
                System.out.print(neighbor + " ");
            }

            System.out.println();
        }
    }

    // checking if there exists any path from source to destination anf if there then returning the path
    public List<String> solution(String source, String destination, int limit){

        HashSet<String> visited = new HashSet<>();
        HashMap<String, String> parentMap = new HashMap<>();
        Stack<node> stack = new Stack<>();

        parentMap.put(source, null);
        visited.add(source);
        node Node = new node(source, limit);
        stack.add(Node);

        while (!stack.isEmpty()) {
            
            node cur = stack.pop();

            if(cur.name.equals(destination)) return way(parentMap, cur.name);

            Iterator<String> i = graph.get(cur.name).iterator();

            while (i.hasNext()) {
                String n = i.next(); 

                if(!visited.contains(n) && cur.depth-1 >= 0){

                    visited.add(n);
                    parentMap.put(n, cur.name);
                    stack.add(new node(n, cur.depth-1));
                }
            }
        }

        return null;
    }

    public List<String> way(HashMap<String, String> parentMap, String city){

        List<String> path = new ArrayList<>();

        path.add(city);

        while (parentMap.get(city) != null) {
            String parent = parentMap.get(city);
            path.add(parent);
            city = parent;
        }

        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        
        DLS graph = new DLS();

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


        graph.addEdge("Arad", "Zerind");
        graph.addEdge("Zerind", "Oradea");
        graph.addEdge("Oradea", "Sibiu");
        graph.addEdge("Arad", "Timisoara");
        graph.addEdge("Arad", "Sibiu");
        graph.addEdge("Sibiu", "Fagaras");
        graph.addEdge("Fagaras", "Bucharest");
        graph.addEdge("Timisoara", "Lugoj");
        graph.addEdge("Mehadia", "Drobeta");
        graph.addEdge("Lugoj", "Mehadia");
        graph.addEdge("Drobeta", "Craiova");
        graph.addEdge("Craiova", "Rimnicu");
        graph.addEdge("Rimnicu", "Sibiu");
        graph.addEdge("Sibiu", "Fagaras");
        graph.addEdge("Pitesti", "Rimnicu");
        graph.addEdge("Pitesti", "Bucharest");
        graph.addEdge("Bucharest", "Urziceni");
        graph.addEdge("Urziceni", "Hirsova");
        graph.addEdge("Hirsova", "Eforie");
        graph.addEdge("Urziceni", "Vaslui");
        graph.addEdge("Vaslui", "Iasi");
        graph.addEdge("Iasi", "Neamt");

        List<String> path = graph.solution("Arad", "Bucharest", 7);
        System.out.println("Using Depth Limited Search");
        System.out.println(path);
    }
}
