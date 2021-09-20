import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.Collections;

public class Woodchucking {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            String[] params = in.readLine().split(" ");
            int n = Integer.parseInt(params[0]);//amount of trees
            int m = Integer.parseInt(params[1]);//amount of disk saws available
            PriorityQueue<Integer> durations = new PriorityQueue<>(Collections.reverseOrder());
            for (int i = 0; i < n; i++) {
                durations.add(Integer.parseInt(in.readLine()));
            }
            PriorityQueue<Long> workloads = new PriorityQueue<>();
            for (int i = 0; i < m; i++) {
                workloads.add(0L);
            }
            for (int d: durations) {
                workloads.offer(workloads.poll() + d);
            }

            long sol = workloads.poll();
            while (!workloads.isEmpty()) {
                sol = workloads.poll();
            }
            System.out.println("Case #" + caseNr + ": " + sol);
            in.readLine();
        }
        in.close();
        reader.close();
    }
}
