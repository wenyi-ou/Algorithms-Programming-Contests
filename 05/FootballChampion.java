import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

/*
Reference: https://en.wikipedia.org/wiki/Maximum_flow_problem#Baseball_elimination
 */
public class FootballChampion {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int i = 1; i <= caseNum; i++) {
            String[] paramLine = in.readLine().split(" ");
            int n0 = Integer.parseInt(paramLine[0]);//number of teams
            int m0 = Integer.parseInt(paramLine[1]);//number of matches
            int[] wins = new int[n0];//number of wins team i already has
            ArrayList<ArrayList<Integer>> matches = new ArrayList<>();//matches not yet played
            int[] remaining = new int[n0];
            int maxWins = 0;
            boolean[] eliminated = new boolean[n0];//store possibility of winning
            String[] line = in.readLine().split(" ");
            for (int j = 0; j < n0; j++) {
                int w = Integer.parseInt(line[j]);
                wins[j] = w;
                if (w > maxWins) {
                    maxWins = w;
                }
                eliminated[j] = false;
                matches.add(new ArrayList<>());
            }
            for (int j = 0; j < m0; j++) {
                line = in.readLine().split(" ");
                int a = Integer.parseInt(line[0]) - 1;
                int b = Integer.parseInt(line[1]) - 1;
                if (a > b) {
                    matches.get(b).add(a);
                } else {
                    matches.get(a).add(b);
                }
                remaining[a] ++;
                remaining[b] ++;
            }

            for (int j = 0; j < n0; j++) {
                if (wins[j] + remaining[j] < maxWins) {
                    eliminated[j] = true;
                }
            }
            for (int currentTeam = 0; currentTeam < n0; currentTeam++) {
                for (int j = 0; j < n0; j++) {
                    if (eliminated[j] &&
                            wins[currentTeam] + remaining[currentTeam] <= wins[j] + remaining[j]) {
                        eliminated[currentTeam] = true;
                        break;
                    }
                }
                if(eliminated[currentTeam]) continue;

                //construct flow
                int gameCount = n0*(n0-1)/2;
                int n = gameCount + n0 + 2;
                int[][] network = new int[n][n];
                int[][] residual = new int[n][n];
                //source - game nodes
                for (int j = 0; j < n0; j++) {
                    if (j != currentTeam) {
                        for (int k : matches.get(j)) {
                            if (k != currentTeam) {
                                int currentGame = gameCount - (n0-j)*(n0-j-1)/2 + k - j; //!!!
                                network[0][currentGame] ++;
                                network[currentGame][1 + gameCount + j] = Integer.MAX_VALUE;
                                network[currentGame][1 + gameCount + k] = Integer.MAX_VALUE;
                            }
                        }
                    }
                }
                //team nodes - sink
                for (int j = 0; j < n0; j++) {
                    if (j != currentTeam) {
                        //already won + winning games : team j <= current team  -->  current team wins maximal
                        network[1 + gameCount + j][n - 1] = wins[currentTeam] + remaining[currentTeam] - wins[j];
                    }
                }
                for (int j = 0; j < n; j++) {
                    //System.out.print(residual[j][k] + " ");
                    System.arraycopy(network[j], 0, residual[j], 0, n);
                    //System.out.println();
                }
                int[] parent = new int[n];
                int maxFlow = 0;
                while (bfs(residual, n - 1, parent)) {
                    int pathMax = Integer.MAX_VALUE;
                    for (int j = n - 1; j > 0; j = parent[j]) {
                        int current = parent[j];
                        pathMax = Math.min(pathMax, residual[current][j]);
                    }
                    for (int j = n - 1; j > 0; j = parent[j]) {
                        int current = parent[j];
                        residual[current][j] -= pathMax;
                        residual[j][current] += pathMax;
                    }
                    maxFlow += pathMax;
                }
                //System.out.println("team " + currentTeam + ": " + maxFlow + " ?<? " + Arrays.stream(network[0]).sum());
                if (maxFlow < Arrays.stream(network[0]).sum())
                    eliminated[currentTeam] = true;
            }

            System.out.print("Case #" + i + ":");
            for (int j = 0; j < n0; j++) {
                if (eliminated[j])
                    System.out.print(" no");
                else
                    System.out.print(" yes");
            }
            System.out.println();
            in.readLine();
        }
        in.close();
        reader.close();
    }

    static boolean bfs(int[][] residual, int t, int[] parent) {
        int[] visited = new int[residual.length];
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(0);
        visited[0] = 1;
        parent[0] = -1;
        while (!q.isEmpty()) {
            int current = q.poll();
            for (int i = 0; i < residual.length; i++) {
                if (visited[i] == 0 && residual[current][i] > 0) {
                    if (i == t) {
                        parent[i] = current;
                        return true;
                    }
                    q.offer(i);
                    parent[i] = current;
                    visited[i] = 1;
                }
            }
        }
        return false;
    }
}