import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CatmonGo {
    static int n;//number of locations
    static int k;//number of events to follow
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            String[] params = in.readLine().split(" ");
            n = Integer.parseInt(params[0]);
            k = Integer.parseInt(params[1]);
            int height = (int) Math.ceil(Math.log(n) / Math.log(2));
            int size = (int) Math.pow(2, height + 1) - 1;
            tree = new Node[size];
            build(new int[n], 0, 0, n - 1);

            int ret = 0;
            for (int i = 0; i < k; i++) {
                params = in.readLine().split(" ");
                if (params[0].charAt(0) == 's') {
                    int a = Integer.parseInt(params[1]) - 1;
                    add(0, a, a, 1);
                } else if (params[0].charAt(0) == 'd') {
                    int a = Integer.parseInt(params[1]) - 1;
                    add(0, a, a, -1);
                } else if (params[0].charAt(0) == 'c') {
                    int l = Integer.parseInt(params[1]) - 1;
                    int r = Integer.parseInt(params[2]) - 1;
                    ret += sum(0, l, r);
                    delete(0, l, r);
                } else
                    throw new IOException("event is not supported!");
                //for (int j = 0; j < n; j++)
                //    System.out.print(sum(0,j,j) + " ");
                //System.out.println();
                //System.out.println("ret = "+ ret + " ");
            }

            System.out.println("Case #" + caseNr + ": " + ret);
            in.readLine();
        }
        in.close();
        reader.close();
    }

    static Node[] tree;

    static void build(int[] arr, int index, int l, int r) {
        if (l == r) {
            tree[index] = new Node(l, r, arr[l], false);
            return;
        }
        int m = (l + r) / 2;
        int left_node = 2 * index + 1;
        int right_node = 2 * index + 2;
        build(arr, left_node, l, m);
        build(arr, right_node, m + 1, r);
        tree[index] = new Node(l, r, tree[left_node].v + tree[right_node].v, false);
    }

    static void delete(int index, int l, int r) {
        propagate(index);
        if (l > tree[index].r || r < tree[index].l)
            return;
        if (l <= tree[index].l && r >= tree[index].r) {
            tree[index].v = 0;
            tree[index].lazy = true;
        } else {
            int left_node = 2 * index + 1;
            int right_node = 2 * index + 2;
            delete(left_node, l, r);
            delete(right_node, l, r);
            tree[index].v = tree[left_node].v + tree[right_node].v;
        }
    }

    static void add(int index, int l, int r, int v) {
        propagate(index);
        if (l > tree[index].r || r < tree[index].l)
            return;
        if (l <= tree[index].l && r >= tree[index].r) {
            tree[index].v = Math.max(tree[index].v + v, 0);
        } else {
            int left_node = 2 * index + 1;
            int right_node = 2 * index + 2;
            add(left_node, l, r, v);
            add(right_node, l, r, v);
            tree[index].v = tree[left_node].v + tree[right_node].v;
        }
    }

    static int sum(int index, int l, int r) {
        propagate(index);
        if (l > tree[index].r || r < tree[index].l)
            return 0;
        if (l <= tree[index].l && r >= tree[index].r) {
            return tree[index].v;
        }
        int left_node = 2 * index + 1;
        int right_node = 2 * index + 2;
        return sum(left_node, l, r) + sum(right_node, l, r);
    }

    static void propagate(int index) {
        int left_node = 2 * index + 1;
        int right_node = 2 * index + 2;
        if (tree[index].lazy) {
            tree[index].lazy = false;
            tree[index].v = 0;
            if (left_node < tree.length && tree[left_node] != null)
                tree[left_node].lazy = true;
            if (right_node < tree.length && tree[right_node] != null)
                tree[right_node].lazy = true;
        }
    }
}

class Node {
    int l, r;
    int v;
    boolean lazy;

    public Node (int left, int right, int value, boolean lazy) {
        this.l = left;
        this.r = right;
        this.v = value;
        this.lazy = lazy;
    }
}