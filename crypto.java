import java.util.HashMap;
import java.util.Map;

public class crypto {

    // Function to convert a string to a corresponding integer based on the mapping
    private static int getNumber(String s, Map<Character, Integer> map) {
        String num = "";
        for (int i = 0; i < s.length(); i++) {
            num += map.get(s.charAt(i));
        }
        return Integer.parseInt(num);
    }

    // Recursive function to solve the cryptarithmetic puzzle using backtracking
    private static void solve(Map<Character, Integer> map, String[] words, String result, String unique, boolean[] used, int i) {

        // If all characters have been assigned a digit, check if the equation is satisfied
        if (i == unique.length()) {

            int sum = 0;

            for (String s : words) {
                sum += getNumber(s, map);
            }

            int r = getNumber(result, map);

            if (sum == r) {

                // Print the mapping if the equation is satisfied
                for (int j = 0; j <= 25; j++) {

                    char ch = (char) ('A' + j);

                    if (map.containsKey(ch)) {

                        System.out.println(ch + " -> " + map.get(ch) + " ");
                    }
                }
                System.out.println();
            }
            return;
        }

        char ch = unique.charAt(i);

        // Try assigning each digit from 0 to 9 to the current character
        for (int j = 0; j <= 9; j++) {

            if (ch == result.charAt(0) && j == 0) {
                continue; // Skip if the first character of the result is assigned 0
            }

            if (!used[j]) {

                map.put(ch, j);
                used[j] = true;

                solve(map, words, result, unique, used, i + 1); // Recur to the next character

                map.put(ch, -1); // Backtrack
                used[j] = false;
            }
        }
    }

    public static void main(String[] args) {

        String[] words = {"SEND", "MORE"};
        String result = "MONEY";

        Map<Character, Integer> map = new HashMap<>();
        String unique = "";

        // Create a unique set of characters from words and result
        for (String s : words) {
            for (int i = 0; i < s.length(); i++) {

                char ch = s.charAt(i);

                if (!map.containsKey(ch)) {

                    map.put(ch, -1);
                    unique += ch;
                }
            }
        }

        for (int i = 0; i < result.length(); i++) {

            char ch = result.charAt(i);

            if (!map.containsKey(ch)) {
                
                map.put(ch, -1);
                unique += ch;
            }
        }

        boolean[] used = new boolean[10];

        // Solve the cryptarithmetic puzzle
        solve(map, words, result, unique, used, 0);
    }
}
