import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class CityRoads {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int i = 1; i <= caseNum; i++) {
            String[] nums = in.readLine().split(" ");
            int n = Integer.parseInt(nums[0]);//number of intersections
            int m = Integer.parseInt(nums[1]);//number of one-way(directed) roads
            int l = Integer.parseInt(nums[2]);//number of two-way(undirected) roads.
            ArrayList<ArrayList<Integer>> connections = new ArrayList<>();
            for (int j = 0 ; j < n; j++) {
                connections.add(new ArrayList<>());
            }
            int[] order = new int[n];
            int[] pres = new int[n];
            for (int j = 0; j < n; j++) {
                order[j] = Integer.MAX_VALUE;
                pres[j] = 0;
            }
            for (int j = 0 ; j < m; j++) {
                String[] line = in.readLine().split(" ");
                int a = Integer.parseInt(line[0]) - 1;
                int b = Integer.parseInt(line[1]) - 1;
                connections.get(a).add(b);
                pres[b]++;
            }
            ArrayList<Road> twoWays = new ArrayList<>();
            for (int j = 0 ; j < l; j++) {
                String[] line = in.readLine().split(" ");
                int a = Integer.parseInt(line[0]) - 1;
                int b = Integer.parseInt(line[1]) - 1;
                twoWays.add(new Road(a, b));
            }

            Queue<Integer> queue = new LinkedList<>();//no pres or not connected(!)
            int num = 1;
            for (int j = 0; j < n; j++) {
                if (pres[j] == 0) {
                    queue.offer(j);
                }
            }
            while (!queue.isEmpty()) {
                int current = queue.poll();
                order[current] = num;
                num++;
                for (int k = 0; k < connections.get(current).size(); k++) {
                    int suc = connections.get(current).get(k);
                    pres[suc]--;
                    if (pres[suc] == 0) {
                        queue.offer(suc);
                    }
                }
            }
            boolean cyclic = false;
            for (int j = 0; j < n; j++) {
                if (order[j] == Integer.MAX_VALUE) {
                    cyclic = true;
                    break;
                }
            }

            if (cyclic) {
                System.out.println("Case #" + i + ": no");
            } else {
                System.out.println("Case #" + i + ": yes");
                for (Road r : twoWays) {
                    int a = r.start + 1;
                    int b = r.end + 1;
                    if (order[r.start] <= order[r.end]) {
                        System.out.println(a + " " + b);
                    } else {
                        System.out.println(b + " " + a);
                    }
                }
            }
            in.readLine();
        }
        in.close();
        reader.close();
    }
}

class Road {
    int start;
    int end;

    public Road(int u, int v) {
        start = u;
        end = v;
    }
}