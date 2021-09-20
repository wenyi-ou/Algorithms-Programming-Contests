import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class PartyPlanning {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int i = 1; i <= caseNum; i++) {
            String[] paramLine = in.readLine().split(" ");
            int n = Integer.parseInt(paramLine[0]);//number of tasks
            int[] times = new int[n];
            int[] order = new int[n];
            int[] pres = new int[n];
            ArrayList<ArrayList<Integer>> sucs = new ArrayList<>();
            for (int j = 0; j < n ; j++) {
                order[j] = Integer.MAX_VALUE;
                pres[j] = 0;
                sucs.add(new ArrayList<>());
            }
            for (int j = 0; j < n ; j++) {
                String[] line = in.readLine().split(" ");
                int p = Integer.parseInt(line[0]);//time
                times[j] = p;
                int s = Integer.parseInt(line[1]);//number of direct successors
                for (int k = 2; k < 2 + s; k++) {
                    int suc = Integer.parseInt(line[k]) - 1;//successor
                    sucs.get(j).add(suc);
                    pres[suc] ++;
                }
            }
            Queue<Integer> queue = new LinkedList<>();
            int num = 1;
            for (int j = 0; j < n; j++) {
                if (pres[j] == 0) {
                    if (order[j] == Integer.MAX_VALUE) {
                        queue.offer(j);
                    }
                    while (!queue.isEmpty()) {
                        int v = queue.poll();
                        order[v] = num;
                        num++;
                        for (int w : sucs.get(v)) {
                            pres[w] --;
                            if (pres[w] == 0) {
                                queue.offer(w);
                            }
                        }
                    }
                }
            }
            //For each node v in the DAG check whether the distance to any of its
            //successors can be increased by passing over v.
            int[] timesBefore = new int[n];
            timesBefore[0] = 0;
            for (int j = 1; j < n; j++) {
                timesBefore[j] = Integer.MIN_VALUE;
            }
            for (int j = 1; j <= n; j++) {
                int current;
                for (current = 0; current < n; current++) {
                    if (order[current] == j) {
                        break;
                    }
                }
                for (int k : sucs.get(current)) {
                    int temp = timesBefore[current] + times[current];
                    if (timesBefore[k] < temp) {
                        timesBefore[k] = temp;
                    }
                }
            }
            int res = timesBefore[n-1] + times[n-1];
            System.out.println("Case #" + i + ": " + res);
            in.readLine();
        }
        in.close();
        reader.close();
    }
}