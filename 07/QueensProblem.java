import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class QueensProblem {
    static int n;
    static int[][] grid;
    static boolean[] occupied;

    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            n = Integer.parseInt(in.readLine());
            grid = new int[n][n];
            occupied = new boolean[n];
            Arrays.fill(occupied, false);
            for (int i = 0; i < n; i++) {
                String line = in.readLine();
                for (int j = 0; j < n; j++) {
                    if (line.charAt(j) != '.') {
                        grid[i][j] = 1;
                        occupied[j] = true;
                    }
                }
            }
            System.out.println("Case #" + caseNr + ":");
            if (isValid() && solved(0)) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (grid[i][j] == 0) {
                            System.out.print(".");
                        } else {
                            System.out.print("x");
                        }
                    }
                    System.out.println();
                }
            } else {
                System.out.println("impossible");
            }
            in.readLine();
        }
        in.close();
        reader.close();
    }

    static boolean solved(int col) {
        if (col >= n)
            return true;
        if (!occupied[col]) {
            for (int i = 0; i < n; i++) {
                occupied[col] = true;
                grid[i][col] = 1;
                if (isValid()) {
                    if (solved(col + 1)) {
                        return true;
                    }
                }
                occupied[col] = false;
                grid[i][col] = 0;
            }
        } else {
            if (solved(col + 1))
                return true;
        }
        return false;
    }

    static boolean isValid() {
        for (int i = 0; i < n; i++) {
            boolean row = false;
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    if (row)
                        return false;
                    row = true;
                }
            }

            boolean column = false;
            for (int j = 0; j < n; j++) {
                if (grid[j][i] == 1) {
                    if (column)
                        return false;
                    column = true;
                }
            }

            boolean diaUpLeft = false;
            for (int j = 0; n-1-j>= 0 && i-j>=0; j++) {
                if (grid[n-1-j][i-j] == 1) {
                    if (diaUpLeft) {
                        return false;
                    }
                    diaUpLeft = true;
                }
            }

            boolean diaUpRight = false;
            for (int j = 0; n-1-j>=0 && i+j<n; j++) {
                if (grid[n-1-j][i+j] == 1) {
                    if (diaUpRight) {
                        return false;
                    }
                    diaUpRight = true;
                }
            }

            boolean diaDownLeft = false;
            for (int j = 0; i-j >= 0; j++) {
                if (grid[j][i-j] == 1) {
                    if (diaDownLeft) {
                        return false;
                    }
                    diaDownLeft = true;
                }
            }

            boolean diaDownRight = false;
            for (int j = 0; i + j < n; j++) {
                if (grid[j][i+j] == 1) {
                    if (diaDownRight) {
                        return false;
                    }
                    diaDownRight = true;
                }
            }
        }
        return true;
    }
}