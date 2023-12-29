import java.util.Arrays;
import java.util.Scanner;

public class nQueen {

    public static void main(String[] args) {
        System.out.println("Enter the number");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.close();

        int[][] board = new int[n][n];

        if (solution(board, 0)) {
            for (int[] row : board) {
                System.out.println(Arrays.toString(row));
            }
        } else {
            System.out.println("No solution");
        }
    }

    // Recursive function to solve the N-Queens problem
    public static boolean solution(int[][] board, int row) {

        
        if (row == board.length) return true;                       // Base case: All queens are placed, return true

        
        for (int col = 0; col < board[row].length; col++) {         // Try placing a queen in each column of the current row

            
            if (isSafe(board, row, col)) {                          // Check if placing a queen at this position is safe

                board[row][col] = 1;                                // Place the queen

                if (solution(board, row + 1)) {                     // Recur to place queens in the remaining rows
                    return true;
                }

                board[row][col] = 0;                                // Backtrack if placing the queen does not lead to a solution
            }
        }

        return false;                                               // No solution found for the current row
    }

    // Function to check if placing a queen at the given position is safe
    public static boolean isSafe(int[][] board, int row, int col) {
        
        for (int i = 0; i < row; i++) {                             // Check for queens in the same column
            if (board[i][col] == 1) {
                return false;
            }
        }

        // Check for queens in the left diagonal
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        // Check for queens in the right diagonal
        for (int i = row, j = col; i >= 0 && j < board.length; i--, j++) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        return true;                                                // Placing a queen at this position is safe
    }
}
