import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Hiking {
    public static void main(String[] args) throws IOException {
        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader (r);
        int caseNum = Integer.parseInt(in.readLine());
        for (int i = 1; i <= caseNum; i++) {
            String[] paramLine = in.readLine().split(" ");
            int n = Integer.parseInt(paramLine[0]);//number of intersections
            int m = Integer.parseInt(paramLine[1]);//number of hiking trails
            int[] dist = new int[n];
            PriorityQueue<Intersection> queue = new PriorityQueue<>(Comparator.comparingInt(t -> t.dist));
            ArrayList<PriorityQueue<Trail>> trails = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                dist[j] = 2000;
                queue.add(new Intersection(j));
                trails.add(new PriorityQueue<>(Comparator.comparingInt(t -> t.length)));
            }
            for (int j = 0; j < m; j++) {
                String[] path = in.readLine().split(" ");
                int a = Integer.parseInt(path[0]) - 1;
                int b = Integer.parseInt(path[1]) - 1;
                int li = Integer.parseInt(path[2]);
                trails.get(a).add(new Trail(b, li));
                trails.get(b).add(new Trail(a, li));
            }
            dist[0] = 0;
            while (!queue.isEmpty()) {
                Intersection current = queue.poll();
                for (Trail t : trails.get(current.n)) {
                    int dNew = current.dist + t.length;
                    if (dNew < dist[t.dest]) {
                        dist[t.dest] = dNew;
                        Intersection w = new Intersection(t.dest, t.length);
                        queue.remove(w);
                        queue.add(new Intersection(t.dest, dNew));
                    }
                }
            }
            System.out.println("Case #" + i + ": " + dist[n-1]);
            in.readLine();
        }
        in.close();
        r.close();
    }
}

class Intersection {
    int n;
    int dist;

    public Intersection(int num) {
        n = num;
        if (num == 0) {
            dist = 0;
        } else{
            dist = 2000;
        }
    }

    public Intersection(int num, int d) {
        n = num;
        dist = d;
    }
}

class Trail {
    int dest;
    int length;

    public Trail(int d, int l) {
        dest = d;
        length = l;
    }
}