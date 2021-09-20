import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Comparator;

public class Networking {
    public static void main(String[] args) throws IOException {
        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader (r);
        int n = Integer.parseInt(in.readLine());
        for (int i = 1; i <= n; i++) {
            System.out.println("Case #" + i + ":");
            int num = Integer.parseInt(in.readLine());
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
            for (int j = 0; j < num; j++) {
                String[] matrixLine = in.readLine().split(" ");
                for (int k = j + 1; k < num; k++) {
                    edgesSorted.add(new Edge(j, k, Integer.parseInt(matrixLine[k])));
                }
            }
            edgesSorted.sort(Comparator.comparingInt(e -> e.length));
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
            for (Edge edge : edgesCurrent) {
                int n1 = edge.start + 1;
                int n2 = edge.end + 1;
                System.out.println(n1 + " " + n2);
            }
            in.readLine();
        }
        in.close();
        r.close();
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
    int start;
    int end;
    int length;

    public Edge(int u, int v, int l) {
        start = u;
        end = v;
        length = l;
    }
}
