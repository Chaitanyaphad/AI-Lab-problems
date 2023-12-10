// Astar Algorithm is nothing but the Best First Search + cost to reach the path.
// here cost is edge cost

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class AStar {
    Map<String, Map<String, Integer>> graph;
    Map<String, Integer> heuristics;

    public AStar(){
        this.graph = new HashMap<>();
        this.heuristics = new HashMap<>();
    }

    public void addNode(String city, int distance){
        graph.put(city, new HashMap<>());
        heuristics.put(city, distance);
    }

    public void addEdge(String city1, String city2, int distance){
        graph.get(city1).put(city2, distance);
    }

    public List<String> solution(String start, String goal){

        Set<String> visited = new HashSet<>();
        Map<String, String> parentMap = new HashMap<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(new NodeComparator());

        visited.add(start);
        parentMap.put(start, null);
        queue.add(new Node(start, 0));

        while (!queue.isEmpty()) {
            Node cur = queue.poll();

            if(cur.city.equals(goal)){
                return way(parentMap, cur.city);
            }

            for(Map.Entry<String, Integer> entry : graph.get(cur.city).entrySet()){

                String city = entry.getKey();
                if(!visited.contains(city)){
                    visited.add(city);
                    parentMap.put(city, cur.city);
                    queue.add(new Node(city, heuristics.get(city) + graph.get(cur.city).get(city)));
                }
            }
        }

        return null;
    }

    public static List<String> way(Map<String, String> parentMap, String city){

        List<String> path = new ArrayList<>();
        path.add(city);

        while(parentMap.get(city) != null){
            String parent = parentMap.get(city);
            path.add(parent);
            city = parent;
        }

        Collections.reverse(path);
        return path;
    }

    private static class Node{
        String city;
        int cost;

        public Node(String city, int cost){
            this.city = city;
            this.cost = cost;
        }
    }

    public static class NodeComparator implements Comparator<Node>{

        @Override
        public int compare(Node node1, Node node2){
            return Integer.compare(node1.cost, node2.cost);
        }
    }
    public static void main(String[] args) {
        AStar graph = new AStar();

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

        graph.addEdge("Arad", "Zerind", 75);
        graph.addEdge("Zerind", "Oradea", 71);
        graph.addEdge("Oradea", "Sibiu", 151);
        graph.addEdge("Arad", "Timisoara", 118);
        graph.addEdge("Arad", "Sibiu", 140);
        graph.addEdge("Sibiu", "Fagaras", 99);
        graph.addEdge("Timisoara", "Lugoj", 111);
        graph.addEdge("Lugoj", "Mehadia", 70);
        graph.addEdge("Mehadia", "Drobeta", 75);
        graph.addEdge("Drobeta", "Craiova", 120);
        graph.addEdge("Rimnicu", "Craiova", 146);
        graph.addEdge("Sibiu", "Rimnicu", 80);
        graph.addEdge("Sibiu", "Fagaras", 99);
        graph.addEdge("Rimnicu", "Pitesti", 97);
        graph.addEdge("Pitesti", "Bucharest", 101);
        graph.addEdge("Bucharest", "Giurgiu", 90);
        graph.addEdge("Fagaras", "Bucharest", 211);
        graph.addEdge("Bucharest", "Urziceni", 85);
        graph.addEdge("Urziceni", "Hirsova", 98);
        graph.addEdge("Hirsova", "Eforie", 86);
        graph.addEdge("Urziceni", "Vaslui", 142);
        graph.addEdge("Vaslui", "Iasi", 92);
        graph.addEdge("Iasi", "Neamt", 87);

        System.out.println("Using A* Algorithm");
        System.out.println(graph.solution("Arad", "Bucharest"));
    }
}
