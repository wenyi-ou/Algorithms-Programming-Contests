import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;

public class Singularity {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            String[] params = in.readLine().split(" ");
            int num = Integer.parseInt(params[0]);//number of people that should have this letter (including Lea)
            int m = Integer.parseInt(params[1]);//number of pairs of people who know each other’s address
            //initializing union find
            parent = new int[num];
            size = new int[num];
            for (int j = 0; j < num; j++) {
                parent[j] = j;
                size[j] = 1;
            }
            //Kruskal's algorithm
            ArrayList<Edge> edgesCurrent = new ArrayList<>();
            ArrayList<Edge> edgesSorted = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                params = in.readLine().split(" ");
                int a = Integer.parseInt(params[0]) - 1;
                int b = Integer.parseInt(params[1]) - 1;
                int cost = Integer.parseInt(params[2]);
                edgesSorted.add(new Edge(a, b, cost));
            }
            edgesSorted.sort(Comparator.comparingInt(e -> e.cost));
            for (Edge e : edgesSorted) {
                if (find(e.start) != find(e.end)) {
                    union(e.start, e.end);
                    edgesCurrent.add(e);
                }
            }
            edgesCurrent.sort((e1, e2) -> {
                if (e1.start == e2.start) {
                    return e1.end - e2.end;
                }
                return e1.start - e2.start;
            });

            System.out.print("Case #" + caseNr + ": ");
            if (edgesCurrent.size() != num - 1)
                System.out.println("impossible");
            else {
                int sum = edgesCurrent.stream().reduce(0, (acc, curr) -> acc + curr.cost, Integer::sum);
                System.out.println(sum);
            }
            in.readLine();
        }
        in.close();
        reader.close();
    }

    private static int[] parent;
    private static int[] size;

    private static int find(int a) {
        //find root
        int root = a;
        int par;
        while (true) {
            par = parent[root];
            if (par == root) {
                break;
            }
            root = par;
        }
        //Compress path
        int current = a;
        int next_elem;
        while (current != root) {
            next_elem = parent[current];
            parent[current] = root;
            current = next_elem;
        }
        return root;
    }

    private static void union(int a, int b) {
        a = find(a);
        b = find(b);
        if (a == b) {
            //a and b are already merged
            return;
        }

        //weighted union: update smaller component
        int a_size = size[a];
        int b_size = size[b];
        if (a_size < b_size) {
            int temp = a;
            a = b;
            b = temp;
        }
        parent[b] = a;
        //update size accordingly
        size[a] = a_size + b_size;
    }
}

class Edge {
    int start, end, cost;

    Edge(int a, int b, int cost) {
        this.start = a;
        this.end = b;
        this.cost = cost;
    }
}