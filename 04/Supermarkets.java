import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Supermarkets {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int i = 1; i <= caseNum; i++) {
            String[] paramLine = in.readLine().split(" ");
            int n = Integer.parseInt(paramLine[0]);//number of cities
            int m = Integer.parseInt(paramLine[1]);//number of roads
            int s = Integer.parseInt(paramLine[2]);//number of supermarkets
            int a = Integer.parseInt(paramLine[3]) - 1;//Lea's city
            int b = Integer.parseInt(paramLine[4]) - 1;//Peter's city
            roads = new ArrayList<>();
            int[] markets = new int[n];
            for (int j = 0; j < n; j++) {
                roads.add(new ArrayList<>());
                markets[j] = Integer.MAX_VALUE;
            }
            for (int j = 0; j < m; j++) {
                String[] line = in.readLine().split(" ");
                int a1 = Integer.parseInt(line[0]) - 1;
                int b1 = Integer.parseInt(line[1]) - 1;
                int l1 = Integer.parseInt(line[2]);
                if (roads.get(a1).stream().noneMatch(r -> r.to == b1) ||
                        roads.get(a1).removeIf(r -> (r.to == b1 && r.dist > l1)))
                    roads.get(a1).add(new Road(b1, l1));
                if (roads.get(b1).stream().noneMatch(r -> r.to == a1) ||
                        roads.get(b1).removeIf(r -> (r.to == a1 && r.dist > l1)))
                    roads.get(b1).add(new Road(a1, l1));
            }
            for (int j = 0; j < s; j++) {
                String[] line = in.readLine().split(" ");
                int c1 = Integer.parseInt(line[0]) - 1;
                int n1 = Integer.parseInt(line[1]);
                if (markets[c1] > n1)
                    markets[c1] = n1;
            }

            queueA = new PriorityQueue<>(Comparator.comparingInt(c -> c.dist));
            queueB = new PriorityQueue<>(Comparator.comparingInt(c -> c.dist));
            distA = new int[n];
            distB = new int[n];
            for (int j = 0; j < n; j++) {
                distA[j] = 1000001;
                queueA.add(new City(j, distA[j]));
                distB[j] = 1000001;
                queueB.add(new City(j, distB[j]));
            }
            distA[a] = 0;
            distB[b] = 0;
            iterate(roads, queueA, distA);
            iterate(roads, queueB, distB);
            if (distA[b] == 1000001 || distB[a] == 1000001) {
                System.out.println("Case #" + i + ": impossible");
            } else {
                int time = Integer.MAX_VALUE;
                for (int j = 0; j < n; j++) {
                    if (markets[j] < Integer.MAX_VALUE && distA[j] < 1000001 && distB[j] < 1000001) {
                        int current = markets[j] + distA[j] + distB[j];
                        if (time > current) {
                            time = current;
                        }
                    }
                }
                if (time < Integer.MAX_VALUE) {
                    System.out.print("Case #" + i + ": " + (int) Math.floor(time/60) + ":");
                    System.out.format("%02d", time%60);
                    System.out.println();
                } else {
                    System.out.println("Case #" + i + ": impossible");
                }
            }
            in.readLine();
        }
        in.close();
        reader.close();
    }

    static ArrayList<ArrayList<Road>> roads;
    static PriorityQueue<City> queueA;
    static PriorityQueue<City> queueB;
    static int[] distA;
    static int[] distB;

    private static void iterate(ArrayList<ArrayList<Road>> roads, PriorityQueue<City> queue, int[] dist) {
        while (!queue.isEmpty()) {
            City v = queue.poll();
            for (Road wr : roads.get(v.n)) {
                City w = new City(wr.to, dist[wr.to]);
                if (dist[v.n] + wr.dist < dist[wr.to]) {
                    dist[wr.to] = dist[v.n] + wr.dist;
                    queue.remove(w);
                    queue.add(new City(wr.to, dist[wr.to]));
                }
            }
        }
    }
}

class City {
    int n;
    int dist;

    public City(int num, int d) {
        n = num;
        dist = d;
    }
}

class Road {
    int to;
    int dist;

    public Road(int t, int d) {
        to = t;
        dist = d;
    }
}