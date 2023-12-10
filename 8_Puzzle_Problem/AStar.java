// Astar Algorithm is nothing but the Best First Search + cost to reach the path.
// here, heuristics is taken as the number of misplaced tiles.
// Other heuristics can be manhattan distance, euclidean distance, etc.
// cost is taken as the depth.
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

    static int[][] moves = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}}; //Up, Left, Right, Down
    
    // Function for calculating heuristics;
    public static int calHeuristics(int[][] state, int[][] goal){
        int count = 0;

        for (int i = 0; i < goal.length; i++) {
            for (int j = 0; j < goal.length; j++) {
                if(state[i][j] != goal[i][j]) count++;
            }
        }
        return count;
    }

    // Function for check whether the given state is equal to goal state or not.
    public static boolean isEqual(int[][] state, int[][] goal){
        for (int i = 0; i < goal.length; i++) {
            for (int j = 0; j < goal.length; j++) {
                if(state[i][j] != goal[i][j]) return false;
            }
        }
        return true;
    }

    // Function for find empty position (position of 0);
    public static int[] FindEmptypos(int[][] board){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if(board[i][j] == 0) return new int[]{i, j};
            }
        }
        return null;
    }

    // Function for copy board
    public static int[][] Copy(int[][] state){
        int[][] board = new int[3][3];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = state[i][j];
            }
        }
        return board;
    }

    // Function for finding solution
    public static List<String> solve(int[][] initial, int[][] goal){

        Set<int[][]> visited = new HashSet<>();    // for keeping the track of visited states

        Map<int[][], int[][]> parentMap = new HashMap<>();  //for keeping track of child and parent states.
        
        PriorityQueue<Node> queue = new PriorityQueue<>(new NodeComparator());


        visited.add(initial);
        parentMap.put(initial, null);
        queue.add(new Node(initial, 0, calHeuristics(initial, goal))); // parent of initial state will be null an depth will be 0;

        while (!queue.isEmpty()) {
            Node curr = queue.poll();

            if(isEqual(curr.state, goal)) return way(parentMap, curr.state);

            for (int[] move : moves) {

                // Finding empty positions of current board
                int[] emptypos = FindEmptypos(curr.state);

                // Making positions for new board
                int[] newpos = {emptypos[0] + move[0], emptypos[1] + move[1]};

                if(newpos[0] >= 0 && newpos[0] < 3 && newpos[1] >= 0 && newpos[1] < 3){
                    int[][] next = Copy(curr.state);
                    next[newpos[0]][newpos[1]] = 0;
                    next[emptypos[0]][emptypos[1]] = curr.state[newpos[0]][newpos[1]];

                    // Checking if new state is  already visited or not.
                    if(!visited.contains(next)){
                        visited.add(next);
                        parentMap.put(next, curr.state);
                        queue.add(new Node(next, curr.depth+1, calHeuristics(next, goal)));
                    }
                }
            }
        }
        List<String> path = new ArrayList<>();
        return path;
    }

    // object Node to store board, depth and it's heuristics
    private static class Node{
        int[][] state;
        int depth;
        int heuristics;

        public Node(int[][] state, int depth, int heuristics){
            this.state = state;
            this.depth = depth;
            this.heuristics = heuristics;
        }
    }

    public static class NodeComparator implements Comparator<Node>{
        @Override
        public int compare(Node node1, Node node2){
            return Integer.compare(node1.heuristics + node1.depth, node2.heuristics + node2.depth);
        }
    }

    // Function for creating path of solution
    public static List<String> way(Map<int[][], int[][]> parentMap, int[][] curr){

        List<String> path = new ArrayList<>();

        while (parentMap.get(curr) != null) {
            int[][] parent = parentMap.get(curr);

            int[] curEmptyPos = FindEmptypos(curr);
            int[] parentEmptyPos = FindEmptypos(parent);

            if(parentEmptyPos[0] - curEmptyPos[0] == 1) path.add("U");
            if(parentEmptyPos[0] - curEmptyPos[0] == -1) path.add("D");
            if(parentEmptyPos[1] - curEmptyPos[1] == 1) path.add("L");
            if(parentEmptyPos[1] - curEmptyPos[1] == -1) path.add("R");

            curr = parent;
        }

        // Reversing path bc while loop ran from goal state to initial state.
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        int[][] initial =  {{2, 8, 3},
                            {1, 6, 4},
                            {7, 0, 5}
                            };

        int[][] goal = {{1, 2, 3},
                        {8, 0, 4},
                        {7, 6, 5}
                        };

        System.out.println(solve(initial, goal));
    }
}
