import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Objects;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class EscapingTheParadox {
    static int n;
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            String[] params = in.readLine().split(" ");
            n = Integer.parseInt(params[0]);//number of graves
            int m = Integer.parseInt(params[1]);//number of tunnels
            int g = Integer.parseInt(params[2]);//the grave where Lea’s future self starts
            ArrayList<PriorityQueue<Tunnel>> tunnelsNow = new ArrayList<>();
            ArrayList<PriorityQueue<Tunnel>> tunnelsFuture = new ArrayList<>();
            int[] objects = new int[n + 1];
            int[][] distances = new int[n+1][n+1];
            params = in.readLine().split(" ");
            for (int i = 0; i < n; i++) {
                objects[i + 1] = Integer.parseInt(params[i]);
            }
            for (int i = 0; i < n + 1; i++) {
                tunnelsNow.add(new PriorityQueue<>());
                tunnelsFuture.add(new PriorityQueue<>());
            }
            for (int i = 0; i < m; i++) {
                params = in.readLine().split(" ");
                int x = Integer.parseInt(params[0]);
                int y = Integer.parseInt(params[1]);
                int l = Integer.parseInt(params[2]);
                if (distances[x][y] == 0 || distances[x][y] > l)
                    distances[x][y] = l;
                if (distances[y][x] == 0 || distances[y][x] > l)
                    distances[y][x] = l;
                if (x > y)
                    tunnelsNow.get(x).add(new Tunnel(x, y, l));
                else if (x < y)
                    tunnelsNow.get(y).add(new Tunnel(y, x, l));
                else
                    continue;
                tunnelsFuture.get(x).add(new Tunnel(x, y, l));
                tunnelsFuture.get(y).add(new Tunnel(y, x, l));
            }

            int[] distFuture = dijkstra(tunnelsFuture, g);
            int[] distNow = dijkstra(tunnelsNow, n);

            if (distNow[0] >= distFuture[0])
                System.out.println("Case #" + caseNr + ": impossible");
            else {
                int[] maxObj = new int[n + 1];
                Arrays.fill(maxObj, 0);
                maxObj[n] = objects[n];

                int[][] cache = new int[n + 1][distFuture[0]];
                for (int i = 0; i < n + 1; i++) {
                    Arrays.fill(cache[i], -1);
                }
                cache[n][0] = objects[n];

                boolean[] visited = new boolean[n + 1];
                visited[n] = true;

                for (int i = n; i > 0; i--) {
                    if (!visited[i]) continue;
                    for (int j = 0; j < i; j++) {
                        if (distances[i][j] == 0) continue;
                        for (int k = 0; k < distFuture[0]; k++) {
                            if (cache[i][k] == -1) continue;
                            if (k + distances[i][j] < distFuture[0]) {
                                visited[j] = true;
                                int currDist = k + distances[i][j];
                                int currMax = objects[j] + cache[i][k];
                                cache[j][currDist] = Math.max(cache[j][currDist], currMax);
                                maxObj[j] = Math.max(maxObj[j], cache[j][currDist]);
                            }
                        }
                    }
                }
                System.out.println("Case #" + caseNr + ": " + maxObj[0]);
            }
            in.readLine();
        }
        in.close();
        reader.close();
    }

    static int[] dijkstra(ArrayList<PriorityQueue<Tunnel>> tunnels, int src) {
        int[] dist = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[src] = 0;

        PriorityQueue<Grave> queue = new PriorityQueue<>();
        queue.add(new Grave(src, 0));

        while (!queue.isEmpty()) {
            Grave curr = queue.poll();
            for (Tunnel tunnel : tunnels.get(curr.index)) {
                if (dist[curr.index] + tunnel.length < dist[tunnel.to]) {
                    queue.remove(new Grave(tunnel.to, dist[tunnel.to]));
                    dist[tunnel.to] = dist[curr.index] + tunnel.length;
                    queue.add(new Grave(tunnel.to, dist[tunnel.to]));
                }
            }
        }
        return dist;
    }
}

class Grave implements Comparable<Grave> {
    int index, dist;

    public Grave(int a, int b) {
        this.index = a;
        this.dist = b;
    }

    @Override
    public int compareTo(Grave g) {
        return this.dist - g.dist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Grave)) return false;
        Grave grave = (Grave) o;
        return index == grave.index && dist == grave.dist;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, dist);
    }
}

class Tunnel implements Comparable<Tunnel> {
    int from, to, length;

    public Tunnel(int a, int b, int l) {
        this.from = a;
        this.to = b;
        this.length = l;
    }

    @Override
    public int compareTo(Tunnel t) {
        return this.length - t.length;
    }
}