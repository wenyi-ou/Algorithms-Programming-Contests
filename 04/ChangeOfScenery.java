import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ChangeOfScenery {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int i = 1; i <= caseNum; i++) {
            String[] paramLine = in.readLine().split(" ");
            int n = Integer.parseInt(paramLine[0]);//number of junctions
            int m = Integer.parseInt(paramLine[1]);//number of streets and footpaths
            int k = Integer.parseInt(paramLine[2]);//number of junctions passed
            String[] lineK = in.readLine().split(" ");
            int[] oldWay = new int[k];
            for (int j = 0; j < k; j++) {
                oldWay[j] = Integer.parseInt(lineK[j]) - 1;
            }
            int[] dist = new int[n];
            int[] prev = new int[n];
            ArrayList<ArrayList<Way>> ways = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                dist[j] = Integer.MAX_VALUE;
                prev[j] = -1;
                ways.add(new ArrayList<>());
            }
            for (int j = 0; j < m; j++) {
                String[] line = in.readLine().split(" ");
                int a = Integer.parseInt(line[0]) - 1;
                int b = Integer.parseInt(line[1]) - 1;
                int l = Integer.parseInt(line[2]);
                ways.get(a).add(new Way(j, b, l));
                ways.get(b).add(new Way(j, a, l));
            }
            dist[0] = 0;
            PriorityQueue<Junction> queue = new PriorityQueue<>(Comparator.comparingInt(j -> j.dist));
            queue.offer(new Junction(0, 0));
            while (!queue.isEmpty()) {
                Junction v = queue.poll();
                for (Way wr : ways.get(v.n)) {
                    Junction w = new Junction(wr.to, dist[wr.to]);
                    if (dist[v.n] + wr.length < dist[wr.to]) {
                        dist[wr.to] = dist[v.n] + wr.length;
                        queue.remove(w);
                        queue.add(new Junction(wr.to, dist[wr.to]));
                        prev[wr.to] = wr.index;
                    }
                }
            }
            boolean success = false;
            check: for (int j = 1; j < k; j++) {
                for (Way w : ways.get(oldWay[j])) {
                    if (w.index != prev[oldWay[j]]) {
                        if (dist[oldWay[j]] == w.length + dist[w.to]) {
                            success = true;
                            break check;
                        }
                    }
                }
            }
            if (success) {
                System.out.println("Case #" + i + ": yes");
            } else {
                System.out.println("Case #" + i + ": no");
            }
            in.readLine();
        }
        in.close();
        reader.close();
    }
}

class Junction {
    int n;
    int dist;

    public Junction (int num, int d) {
        n = num;
        dist = d;
    }
}

class Way {
    int index;
    int to;
    int length;

    public Way (int i, int t, int l) {
        index = i;
        to = t;
        length = l;
    }
}
