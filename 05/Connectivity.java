import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Connectivity {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int i = 1; i <= caseNum; i++) {
            String[] paramLine = in.readLine().split(" ");
            int n0 = Integer.parseInt(paramLine[0]);//number of connection points
            int m0 = Integer.parseInt(paramLine[1]);//number of connections
            int n = 2 * (n0 - 2) + 2;
            int[][] network = new int[n][n];
            int[][] residual = new int[n][n];
            for (int j = 1; j < n0 - 1; j++) {
                //split the intermediate nodes to make sure that each is used only once
                network[2*j - 1][2*j] ++;
                network[2*j][2*j - 1] ++;
            }
            for (int j = 0; j < m0; j++) {
                String[] line = in.readLine().split(" ");
                int a = Integer.parseInt(line[0]) - 1;
                int b = Integer.parseInt(line[1]) - 1;
                //make sure that b is an intermediate node
                if (b == 0 || b == n0 - 1) {
                    int temp = a;
                    a = b;
                    b = temp;
                }
                //a might be source / sink
                if (a == 0) {
                    network[a][2*b-1] ++;
                    network[2*b][a] ++;
                } else if (a == n0 - 1) {
                    network[2*a-1][2*b-1] ++;
                    network[2*b][2*a-1] ++;
                } else {
                    network[2*a][2*b-1] ++;
                    network[2*b][2*a-1] ++;
                }
            }
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    residual[j][k] = network[j][k];
                }
            }
            int[] parent = new int[n];
            int maxFlow = 0;
            while (bfs(residual, 0, n - 1, parent)) {
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
            //print minimum amount of intermediate devices
            System.out.println("Case #" + i + ": " + maxFlow);
            in.readLine();
        }
        in.close();
        reader.close();
    }

    static boolean bfs(int[][] residual, int s, int t, int[] parent) {
        int[] visited = new int[residual.length];
        Queue<Integer> q = new LinkedList<>();
        q.offer(s);
        visited[s] = 1;
        parent[s] = -1;
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