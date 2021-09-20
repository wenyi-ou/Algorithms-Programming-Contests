import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class MarryRich {
    public static void main(String[] args) throws IOException {
        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader (r);
        int n = Integer.parseInt(in.readLine());
        for (int i = 1; i <= n; i++) {
            String nums = in.readLine();
            String[] numParts = nums.split(" ");
            int a = Integer.parseInt(numParts[0]);
            int b = Integer.parseInt(numParts[1]);
            int c = Integer.parseInt(numParts[2]);

            //initializing union find
            parent = new int[a];
            size = new int[a];
            for (int j = 0; j < a; j++) {
                parent[j] = j;
                size[j] = 1;
            }

            //read amount of money
            int[] money = new int[a-1];
            String moneyLine = in.readLine();
            String[] moneyParts = moneyLine.split(" ");
            for (int j = 0; j < a - 1; j++) {
                money[j] = Integer.parseInt(moneyParts[j]);
            }

            //read family relations
            for (int j = 0; j < b; j++) {
                String relationLine = in.readLine();
                String[] relationParts = relationLine.split(" ");
                union(Integer.parseInt(relationParts[0]) - 1, Integer.parseInt(relationParts[1]) - 1);
            }

            //read marriages
            boolean[] married = new boolean[a];
            for (int j = 0; j < c; j++) {
                String marriageLine = in.readLine();
                String[] marriageParts = marriageLine.split(" ");
                married[Integer.parseInt(marriageParts[0]) - 1] = true;
                married[Integer.parseInt(marriageParts[1]) - 1] = true;
                union(Integer.parseInt(marriageParts[0]) - 1, Integer.parseInt(marriageParts[1]) - 1);
            }

            //find the richest
            int m = -1;
            int p = -1;
            for (int j = 0; j < a - 1; j++) {
                if (!married[j] && (find(j) != find(a - 1))) {
                    if (money[j] > m) {
                        m = money[j];
                        p = j;
                    }
                }
            }
            if (m == -1) {
                System.out.println("Case #" + i + ": impossible");
            } else {
                System.out.println("Case #" + i + ": " + money[p]);
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