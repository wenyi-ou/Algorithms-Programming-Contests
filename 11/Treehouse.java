import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

//Reference: https://www.geeksforgeeks.org/find-lca-in-binary-tree-using-rmq/
public class Treehouse {
    static int n;//number of branching points of the tree
    static ArrayList<ArrayList<Integer>> branches;
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            n = Integer.parseInt(in.readLine());
            branches = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                String[] params = in.readLine().split(" ");
                int c = Integer.parseInt(params[0]);
                ArrayList<Integer> bs = new ArrayList<>();
                for (int j = 1; j <= c; j++) {
                    int bj = Integer.parseInt(params[j]) - 1;
                    bs.add(bj);
                }
                branches.add(bs);
            }

            int[] depths = new int[n];
            depths[0] = 0;
            Stack<Integer> stack = new Stack<>();
            stack.push(0);
            while (!stack.isEmpty()) {
                int curr = stack.pop();
                for (int suc : branches.get(curr)) {
                    depths[suc] = depths[curr] + 1;
                    stack.push(suc);
                }
            }

            size = 2 * n - 1;
            euler = new int[size];
            levels = new int[size];
            f_occur = new int[size];
            Arrays.fill(f_occur, -1);
            fill = 0;
            sc = new St_class();
            visited = new boolean[n];
            Arrays.fill(visited, false);
            eulerTour(0, 0);
            sc.st = constructST(size);

            String[] params = in.readLine().split(" ");
            int v = Integer.parseInt(params[0]);
            int[] stops = new int[v + 1];
            stops[0] = 0;
            for (int i = 1; i <= v; i++) {
                int vi = Integer.parseInt(params[i]) - 1;
                stops[i] = vi;
            }

            int ret = 0;
            for (int i = 0; i < v; i++) {
                int from = stops[i];
                int to = stops[i + 1];
                int lca = findLCA(from, to);
                ret += depths[from] + depths[to] - 2 * depths[lca];
            }

            System.out.println("Case #" + caseNr + ": " + ret);
            in.readLine();
        }
        in.close();
        reader.close();
    }

    static int findLCA(int a, int b) {
        if (f_occur[a] > f_occur[b]) {
            int temp = a;
            a = b;
            b = temp;
        }
        int l = f_occur[a];
        int r = f_occur[b];
        int index = getRMQ(sc, size, l, r);
        return euler[index];
    }

    static int getRMQ(St_class st, int n, int qs, int qe) {
        if (qs < 0 || qe > n - 1 || qs > qe)
            return -1;
        return rmqUtil(0, 0, n - 1, qs, qe, st);
    }
    static int rmqUtil(int index, int ss, int se, int qs, int qe, St_class st) {
        if (qs <= ss && qe >= se)
            return st.stt[index];
        else if (se < qs || ss > qe)
            return -1;
        int mid = (ss + se) / 2;
        int q1 = rmqUtil(2 * index + 1, ss, mid, qs, qe, st);
        int q2 = rmqUtil(2 * index + 2, mid + 1, se, qs, qe, st);
        if (q1 == -1)
            return q2;
        else if (q2 == -1)
            return q1;
        return (levels[q1] < levels[q2]) ? q1 : q2;
    }

    static int size;//length of the euler tour
    static int[] euler;//route of the euler tour
    static int[] levels;//depths of nodes in the tour
    static int[] f_occur;//storage of first occurrence
    static boolean[] visited;//whether the node is already visited
    static int fill;//variable to fill euler and level arrays
    static void eulerTour(int node, int level) {
        visited[node] = true;
        euler[fill] = node;
        levels[fill] = level;
        if (f_occur[node] == -1) {
            f_occur[node] = fill;
        }
        fill++;
        for (int next : branches.get(node)) {
            if (!visited[next]) {
                eulerTour(next, level + 1);
                euler[fill] = node;
                levels[fill] = level;
                fill++;
            }
        }
    }

    static St_class sc;
    static class St_class {
        int st;
        int[] stt;
    }
    static int constructST(int n) {
        int x = (int) Math.ceil(Math.log(n) / Math.log(2));
        int max_size = (int) (2 * Math.pow(2, x) - 1);
        sc.stt = new int[max_size];
        constructSTUtil(0, 0, n - 1, levels, sc);
        return sc.st;
    }
    static void constructSTUtil(int si, int ss, int se, int[] arr, St_class st) {
        if (ss == se)
            st.stt[si] = ss;
        else {
            int mid = (ss + se) / 2;
            constructSTUtil(si * 2 + 1, ss, mid, arr, st);
            constructSTUtil(si * 2 + 2, mid + 1, se, arr, st);
            if (arr[st.stt[2 * si + 1]] < arr[st.stt[2 * si + 2]])
                st.stt[si] = st.stt[2 * si + 1];
            else
                st.stt[si] = st.stt[2 * si + 2];
        }
    }
}