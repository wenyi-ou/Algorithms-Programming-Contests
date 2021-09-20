import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Trapped {
    static int[][] cave;
    static int totalTools;
    static int foundTools;
    static boolean feasible;

    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            String[] params = in.readLine().split(" ");
            int width = Integer.parseInt(params[0]);
            int height = Integer.parseInt(params[1]);
            cave = new int[height + 2][width + 2];
            totalTools = 0;
            foundTools = 0;
            feasible = false;

            int startX = 0, startY = 0;
            for (int i = 1; i < height + 1; i++) {
                String line = in.readLine();
                for (int j = 0; j < line.length(); j++) {
                    char c = line.charAt(j);
                    if (c == 'T') {
                        totalTools++;
                        cave[i][j + 1] = 2;
                    } else if (c == 'L') {
                        startX = i;
                        startY = j + 1;
                        cave[i][j + 1] = 1;
                    } else if (c == '#') {
                        cave[i][j + 1] = 3;
                    }
                }
            }
            for (int i = 0; i < cave.length; i++) {
                cave[i][0] = 5;
                cave[i][cave[0].length - 1] = 5;
            }
            for (int i = 0; i < cave[0].length; i++) {
                cave[0][i] = 5;
                cave[cave.length - 1][i] = 5;
            }

            /*
            0 - a normal walkable field (_)
            1 - Lea’s starting position (L)
            2 - a position of a tool (T)
            3 - a non-walkable field or wall (#)
            4 - a visited position
            5 - borders
             */
            solve(startX, startY);

            System.out.print("Case #" + caseNr + ": ");
            if (feasible) {
                System.out.println("yes");
            } else {
                System.out.println("no");
            }
            in.readLine();
        }
        in.close();
        reader.close();
    }

    static void solve(int x, int y) {
        if (cave[x][y] == 2) {
            foundTools++;
            if (foundTools == totalTools) {
                feasible = true;
                return;
            } else {
                next(x, y);
                //backtracking
                cave[x][y] = 2;
                foundTools--;
            }
        } else {
            next(x, y);
            cave[x][y] = 0;
        }
    }

    static int[] dx = new int[]{0, 0, -1, 1};
    static int[] dy = new int[]{1, -1, 0, 0};
    private static void next(int x, int y) {
        cave[x][y] = 4;
        for (int i = 0; i < 4; i++) {
            int next = cave[x + dx[i]][y + dy[i]];
            if (!feasible) {
                if (next == 0 || next == 2)
                    solve(x + dx[i], y + dy[i]);
            }
        }
    }
}