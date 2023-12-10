// We are trying to find a way from Arad
// to bukarest using depth First Search
// here only queue of BSF is replaced with stack

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class DFS {

    // Using HashMap to store the edges of the graph
    Map<String, List<String>> graph;

    public DFS(){
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
            System.out.print(entry.getKey() + ": ");
            for(String neighbor : entry.getValue()){
                System.out.print(neighbor + " ");
            }
            System.out.println();
        }
    }

    // checking if there exists any path from source to destination anf if there then returning the path
    public List<String> solution(String source, String destination){

        Set<String> visited = new HashSet<>();
        Map<String, String> parentMap = new HashMap<>();
        Stack<String> stack = new Stack<>();

        stack.push(source);
        visited.add(source);
        parentMap.put(source, null);

        while (!stack.isEmpty()) {
            String cur = stack.pop();

            if(cur == destination){
                return reconstructPath(parentMap, cur);
            }

            Iterator<String> i = graph.get(cur).iterator();
            while (i.hasNext()) {
                String city = i.next();

                if(!visited.contains(city)){
                    stack.add(city);
                    visited.add(city);
                    parentMap.put(city, cur);
                }
            }
        }
        
        return null;
    }

    // Constructing path from source to destination
    public List<String> reconstructPath(Map<String, String> parentMap, String goal){

        String cur = goal;
        List<String> path = new ArrayList<>();
        path.add(cur);

        while(parentMap.get(cur) != null){
            String parent = parentMap.get(cur);
            path.add(parent);
            cur = parent;
        }

        Collections.reverse(path);

        return path;
    }

    public static void main(String[] args) {
        DFS graph = new DFS();

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

        List<String> path = graph.solution("Arad", "Bucharest");
        System.out.println("Using Depth First Search");
        System.out.println(path);
    }
}
