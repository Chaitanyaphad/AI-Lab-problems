// this is a classic problem in the domain of artificial intelligence and 
// computer science, often used in introductory courses on problem-solving 
// and search algorithms. The problem involves transporting missionaries 
// and cannibals across a river using a boat under certain constraints.

// The objective is to move all missionaries and cannibals from one side of 
// the river to the other without violating specific rules. The rules typically 
// include constraints on the number of missionaries and cannibals in the boat 
// and ensuring that the number of cannibals on either side of the river does 
// not exceed the number of missionaries.
import java.util.*;

public class Problem {

    static int[][] moves = {{1, 1},    // {1 missionaries, 1 cannibals}
                            {1, 0},    // {1 missionaries, 0 cannibals} 
                            {0, 2},    // {0 missionaries, 2 cannibals}
                            {0, 1},    // {0 missionaries, 1 cannibals}
                            {2, 0}};   // {2 missionaries, 0 cannibals}      

    private static class state{ 

        int LM;                        // Missionaries at left side
        int LC;                        // Cannibles at left side
        boolean LB;                    // Boat at left side
        int RM;                        // Missionaries at right side
        int RC;                        // Canibles at right side
        boolean RB;                    // Boat at right side

        public state(int LM, int LC, boolean LB, int RM, int RC, boolean RB){
            this.LM = LM;
            this.LC = LC;
            this.LB = LB;
            this.RM = RM;
            this.RC = RC;
            this.RB = RB;
        }
    }
    
    // Function for check whether the given state is equal to goal state or not.
    public static boolean isEqual(state cur, state goal){

        if(cur.LM != goal.LM) return false;
        if(cur.LC != goal.LC) return false;
        if(cur.LB != goal.LB) return false;
        if(cur.RM != goal.RM) return false;
        if(cur.RC != goal.RC) return false;
        if(cur.RB != goal.RB) return false;

        return true;
    }

    // Function for creating path of solution
    public static List<String> way(Map<state, state> parentMap,  state cur){

        List<String> path = new ArrayList<>();
        List<state> list = new ArrayList<>();

        while (parentMap.get(cur) != null) {

            state parent = parentMap.get(cur);
            list.add(cur);

            if((parent.LC - cur.LC == 1 && parent.LM - cur.LM == 1) || (parent.RC-cur.RC==1 && parent.RM-cur.RM==1)) path.add("1M 1C");
            else if(parent.LC - cur.LC == 2 || parent.RC - cur.RC == 2) path.add("0M 2C"); 
            else if(parent.LM - cur.LM == 2 || parent.RM - cur.RM == 2) path.add("2M 0C");
            else if(parent.LM - cur.LM == 1 || parent.RM - cur.RM == 1) path.add("1M 0C");
            else if(parent.LC - cur.LC == 1 || parent.RC - cur.RC == 1) path.add("0M 1C");
            
            cur = parent;
        }
        Collections.reverse(path);
        Collections.reverse(list);

        for (state s : list) {
            System.out.println(s.LM + " " + s.LC + " " + s.LB + " " + s.RM + " " + s.RC + " " + s.RB);
        }
        return path;
    }
    
    // Function for finding solution
    public static List<String> solve(state initial, state goal){

        Set<state> visited = new HashSet<>();                // for keeping the track of visited states
        Queue<state> queue = new LinkedList<>();
        Map<state, state> parentMap = new HashMap<>();      // for keeping track of child and parent states.

        queue.add(initial);
        visited.add(initial);
        parentMap.put(initial, null);                 // parent of initial state will be null;

        while (!queue.isEmpty()) {

            state cur = queue.poll();
            
            if(isEqual(cur, goal)) return way(parentMap, cur);

            if(cur.LB){ 
                // if cur.LB is true means boat is on the left side, means boat will sail to the right side
                // that means something will get minus from left side and gets add to the right side

                for (int[] move : moves) {

                    int newLM = cur.LM - move[0];
                    int newLC = cur.LC - move[1];
                    int newRM = cur.RM + move[0];
                    int newRC = cur.RC + move[1];

                    // no of missionaries and cannibles on both side should never be less than negative
                    if(newLM >= 0 && newLC >= 0 && newRC >= 0 && newRM >= 0){

                        // no of Missionaries should either be zero or the greater than or equal to that side of cannibles
                        if((newLM == 0 || newLM >= newLC) && (newRM == 0 || newRM >= newRC)){

                            // in new state, boat should be at the right side. 
                            state newstate = new state(newLM, newLC, false, newRM, newRC, true);

                            if(!visited.contains(newstate)){

                                visited.add(newstate);
                                queue.add(newstate);
                                parentMap.put(newstate, cur);
                            }
                        }
                    }
                }
            } 
            else {

                // if cur.LB is false  means boat is on the right side, means boat will sail to the left side
                // that means something will get minus from right side and gets add to the left side

                for(int[] move : moves) {

                    int newLM = cur.LM + move[0];
                    int newLC = cur.LC + move[1];
                    int newRM = cur.RM - move[0];
                    int newRC = cur.RC - move[1];

                   // no of missionaries and cannibles on both side should never be less than negative
                    if(newLM >= 0 && newLC >= 0 && newRC >= 0 && newRM >= 0){

                        // no of Missionaries should either be zero or the greater than or equal to that side of cannibles
                        if((newLM == 0 || newLM >= newLC) && (newRM == 0 || newRM >= newRC)){

                            // in new state, boat should be at the left side. 
                            state newstate = new state(newLM, newLC, true, newRM, newRC, false);

                            if(!visited.contains(newstate)){

                                visited.add(newstate);
                                queue.add(newstate);
                                parentMap.put(newstate, cur);
                            }
                        }
                    }
                }
            }
        }
    return null;
    }

    public static void main(String[] args) {
        state initial = new state(3, 3, true, 0, 0, false);
        state goal = new state(0,0,false, 3, 3, true);
        System.out.println(solve(initial, goal));
    }
}
