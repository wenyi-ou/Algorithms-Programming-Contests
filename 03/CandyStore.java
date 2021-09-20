import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

//idea: finding the longest path in the graph, then determine the middle node of that branch
//reference: https://stackoverflow.com/questions/4020122/finding-center-of-the-tree

public class CandyStore {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int i = 1; i <= caseNum; i++) {
            int n = Integer.parseInt(in.readLine());
            parent = new int[n];
            connections = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                connections.add(new ArrayList<>());
            }
            for (int j = 0; j < n-1; j++) {
                String[] line = in.readLine().split(" ");
                int a = Integer.parseInt(line[0]) - 1;
                int b = Integer.parseInt(line[1]) - 1;
                connections.get(a).add(b);
                connections.get(b).add(a);
            }

            int[] temp  = longestFrom(longestFrom(0)[0]);
            int node = temp[0];
            int d = temp[1] / 2;
            for (int j = 0; j < d; j++) {
                node = parent[node];
            }
            node++;
            System.out.println("Case #" + i + ": " + node);
            in.readLine();
        }
        in.close();
        reader.close();
    }

    static ArrayList<ArrayList<Integer>> connections;
    static int[] parent;

    static int[] longestFrom(int origin) {
        int[] dist = new int[connections.size()];//store the distance to origin
        for (int i = 0; i < connections.size(); i++) {
            dist[i] = -1;
        }
        Queue<Integer> queue = new LinkedList<>();
        queue.add(origin);
        dist[origin] = 0;
        while (!queue.isEmpty()) {
            int current = queue.poll();
            for(int suc : connections.get(current)) {
                if(dist[suc] < 0) {
                    queue.offer(suc);
                    dist[suc] = dist[current] + 1;
                    parent[suc] = current;
                }
            }
        }
        int maxDist = 0;
        int maxNode = 0;
        for(int i = 0; i < connections.size(); i++) {
            if(dist[i] > maxDist) {
                maxDist = dist[i];
                maxNode = i;
            }
        }
        int[] ret = new int[] {maxNode, maxDist};
        return ret;
    }
}