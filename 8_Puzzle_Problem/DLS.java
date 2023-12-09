// Depth Limited Search (DLS) is nothing but the DFS + Limit
// Here, Depth which DLS explores is restricted with Limit 
// to avoid infinite state problem

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import java.util.Set;

public class DLS {

    static int[][] moves = {{-1, 0}, {0, 1}, {0, -1}, {1, 0}}; //  down left right up 

    // Function for find empty position (position of 0);
    public static int[] FindEmptypos(int[][] board){

        for (var i = 0; i < board[0].length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if(board[i][j] == 0){
                    return new int[]{i, j};
                }
            }
            
        }
        return null;
    }

    // Function for copy board
    public static int[][] Copy(int[][] board){

        int[][] newboard = new int[3][3];

        for (int i = 0; i < newboard.length; i++) {
            for (int j = 0; j < newboard.length; j++) {
                newboard[i][j] = board[i][j];
            }
        }
        return newboard;
    }

    // Function for check whether the given state is equal to goal state or not.
    public static boolean isEqual(int[][] cur, int[][] goal){
        for (int i = 0; i < goal.length; i++) {
            for (int j = 0; j < goal.length; j++) {
                if(cur[i][j] != goal[i][j]) return false;
            }
        }
        return true;
    }

    // Function for finding solution
    public static List<String> solve(int[][] initial, int[][] goal, int limit){

        Set<int[][]> visited = new HashSet<>();    // for keeping the track of visited states
        Stack<state> stack = new Stack<>();
        Map<int[][], int[][]> parentmap = new HashMap<>();  //for keeping track of child and parent states.

        visited.add(initial);
        state matrix = new state(initial, 0);
        stack.add(matrix);
        parentmap.put(initial, null);  // parent of initial state will be null;

        while (!stack.isEmpty()) {

            state cur = stack.pop();

            if(isEqual(cur.matrix, goal)) return way(parentmap, cur.matrix);

            // Finding empty positions of current board
            int[] emptypos = FindEmptypos(cur.matrix);

            // Checking if the further states can be explored or not
            if(cur.depth < limit){
                for (int[] move : moves) {
                
                    // Making positions for new board
                    int[] newpos = new int[2];
                    newpos[0] = emptypos[0] + move[0];
                    newpos[1] = emptypos[1] + move[1];

                    if(newpos[0] >= 0 && newpos[0] < 3 && newpos[1] >= 0 && newpos[1] < 3){
                        int[][] newBoard = Copy(cur.matrix);
                        newBoard[newpos[0]][newpos[1]] = 0;
                        newBoard[emptypos[0]][emptypos[1]] = cur.matrix[newpos[0]][newpos[1]];

                        // Checking if new state is  already visited or not.
                        if(!visited.contains(newBoard)){
                            stack.add(new state(newBoard, cur.depth + 1));
                            parentmap.put(newBoard, cur.matrix);
                        }
                    }
                }
            }
        }
        return null;
    }

    public static List<String> way(Map<int[][], int[][]> parentMap, int[][] curr){

        List<String> path = new ArrayList<>();

        while (parentMap.get(curr) != null) {

            int[][] parent = parentMap.get(curr);
            int[] parentpos = FindEmptypos(parent);
            int[] currpos = FindEmptypos(curr);

            if(parentpos[0] - currpos[0] == 1) path.add("U");
            if(parentpos[0] - currpos[0] == -1) path.add("D");
            if(parentpos[1] - currpos[1] == 1) path.add("L");
            if(parentpos[1] - currpos[1] == -1) path.add("R");

            curr = parent;
        }

        Collections.reverse(path);
        return path;
    }

    // object state to handle board and it's depth
    private static class state{
        int[][] matrix = new int[3][3];
        int depth;

        public state(int[][] matrix, int depth){
            this.matrix = matrix;
            this.depth = depth;
        }
    }

    public static List<String> solution(int[][] initial, int[][] goal, int limit){

        for (int i = 1; i <= limit; i++) {

            if(solve(initial, goal, i) != null){

                return solve(initial, goal, i);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        int[][] initial = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };

        int[][] goal = {
            {0, 1, 2},
            {4, 5, 3},
            {7, 8, 6}
        };

        System.out.println(solution(initial, goal, 4));
    }
}
