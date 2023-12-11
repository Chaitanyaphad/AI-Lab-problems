// The Water Jug Problem is a classic problem in computer science 
// and artificial intelligence that involves using two jugs to measure 
// a specific amount of water. The problem is often framed as a puzzle 
// and goes as follows:

// Problem Statement:

// Suppose you are given two jugs:
// Jug A has a capacity of a liters.
// Jug B has a capacity of b liters.

// The goal is to measure out a specific quantity of water, usually 
// c liters, using these two jugs. You can perform the following operations:

// 1. Fill a jug completely.
// 2. Empty a jug.
// 3. Pour water from one jug into another until the receiving jug is full or the pouring jug is empty.

// here the problem has been solved using Breath First Search Approach  

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class bsf {

    // class to store water of JugA and JugB and parent of class.
    private static class state{
        int jugA;
        int jugB;
        state parent;

        public state(int jugA, int jugB, state parent){
            this.jugA = jugA;
            this.jugB = jugB;
            this.parent = parent;
        }
    }

    // for checking if the amount of water in jugA is equal to target or not
    public static boolean isgoal(state cur, int target){
        if(cur.jugA == target) return true;
        return false;
    }

    // creating path
    public static List<int[]> way(state goal){

        List<int[]> path = new ArrayList<int[]>();

        while (goal != null) {
            path.add(new int[] {goal.jugA, goal.jugB});
            goal = goal.parent;
        }
        Collections.reverse(path);
        return path;
    }

    // Finding solution with Breadth First Search approach
    static List<int[]> solution(int jugA, int jugB, int target){

        state initial = new state(jugA, jugB, null);

        Set<state> visited = new HashSet<>();
        Queue<state> queue = new LinkedList<>();

        queue.add(initial);

        while (!queue.isEmpty()) {
            
            state cur = queue.poll();

            if(cur.jugA == target) return way(cur);

            if(!visited.contains(cur)){

                visited.add(cur);

                //poor water from A to B
                if(cur.jugA > 0){
                    int AmountToPoor = jugB - cur.jugB;
                    int newJugB = cur.jugB + cur.jugA < AmountToPoor ? cur.jugA : AmountToPoor;
                    int newjugA = cur.jugA < AmountToPoor ? 0 : cur.jugA - AmountToPoor;
                    queue.add(new state(newjugA, newJugB, cur));
                }

                //poor water from B to A
                if(cur.jugB > 0){
                    int AmountToPoor = jugA - cur.jugA;
                    int newjugA = cur.jugA + cur.jugB < AmountToPoor ? cur.jugB : AmountToPoor;
                    int newJugB = cur.jugB < AmountToPoor ? 0 : cur.jugB - AmountToPoor;
                    queue.add(new state(newjugA, newJugB, cur));
                }

                // Fill jugA
                queue.add(new state(jugA, cur.jugB, cur));

                // Fill jugB
                queue.add(new state(cur.jugA, jugB, cur));

                //Emmpty jugA
                queue.add(new state(0, cur.jugB, cur));

                // Empty jugB
                queue.add(new state(cur.jugA, 0, cur));
            }
        }

        return null;
    }
    public static void main(String[] args) {

        List<int[]> path = solution(4, 3, 2);

        for (int[] arr : path) {
            System.out.print(Arrays.toString(arr));
            System.out.print("->");
        }
    }
}
