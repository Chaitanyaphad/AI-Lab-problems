// Iterative Deepning Depth First Search (IDDFS)
// is nothing but the DLS in for loop in which runs
// from 0 to limit of DLS

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class IDDFS {
    Map<String, List<String>> graph; 
    
    public IDDFS(){
        this.graph = new HashMap<>();
    }

    public static class node{
        String name;
        int limit;

        public node(String name, int limit){
            this.name = name;
            this.limit = limit;
        }
    }

    public void addNode(String city){
        graph.put(city, new ArrayList<String>());
    }

    public void addEdge(String city1, String city2){
        graph.get(city1).add(city2);
        graph.get(city2).add(city1);
    }

    public boolean solution(String city, String target, int limit){

        for (int i = 1; i < limit; i++) {
            if(dls(city, target, limit, new HashMap<>())){
                return true;
            }
        }

        return false;
    }

    public boolean dls(String city, String target, int limit, Map<String, String> parentMap){

        Set<String> visited = new HashSet<>();
        Stack<node> stack = new Stack<>();

        visited.add(city);
        parentMap.put(city, null);
        stack.add(new node(city, limit));

        while (!stack.isEmpty()) {
            node cur = stack.pop();

            if(cur.name.equals(target)) return true;

            Iterator<String> i = graph.get(cur.name).iterator();

            while (i.hasNext()) {
                String n = i.next();

                if (!visited.contains(n) && cur.limit - 1 > 0) {
                    visited.add(n);
                    parentMap.put(n, cur.name);
                    stack.add(new node(n, cur.limit-1));
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {

        IDDFS graph = new IDDFS();

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
        graph.addEdge("Sibiu", "Fagaras");
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

        if(graph.solution("Arad", "Bucharest", 10)){
            System.out.println(true);
        } else {
            System.out.println(false);
        }
    }
}
