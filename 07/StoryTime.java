import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

//Reference: https://www.techiedelight.com/find-all-possible-topological-orderings-of-dag/

public class StoryTime {
    static int chapCount;
    static int[] characterIndex;
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            String[] params = in.readLine().split(" ");
            int n = Integer.parseInt(params[0]);//number of characters
            int m = Integer.parseInt(params[1]);//number of dependencies
            int[] numChaps = new int[n];
            ArrayList<ArrayList<Integer>> dependencies = new ArrayList<>();
            chapCount = 0;
            characterIndex = new int[13];
            Arrays.fill(characterIndex, -1);
            for (int i = 0; i < n; i++) {
                int num = Integer.parseInt(in.readLine());
                numChaps[i] = num;
                for (int j = 0; j < num; j++) {
                    dependencies.add(new ArrayList<>());
                    if (j >= 1) {
                        dependencies.get(chapCount + j - 1).add(chapCount + j);
                        characterIndex[chapCount + j - 1] = chapCount + j;
                    }
                }
                chapCount += num;
            }
            for (int i = 0; i < m; i++) {
                params = in.readLine().split(" ");
                int char1 = Integer.parseInt(params[0]) - 1;
                int chap1 = Integer.parseInt(params[1]) - 1;
                int char2 = Integer.parseInt(params[2]) - 1;
                int chap2 = Integer.parseInt(params[3]) - 1;
                int count1 = 0;
                for (int j = 0; j < char1; j++) {
                    count1 += numChaps[j];
                }
                int count2 = 0;
                for (int j = 0; j < char2; j++) {
                    count2 += numChaps[j];
                }
                dependencies.get(count1 + chap1).add(count2 + chap2);
            }
            System.out.println("Case #" + caseNr + ": " + solve(dependencies));
            in.readLine();
        }
        in.close();
        reader.close();
    }

    private static int solve(ArrayList<ArrayList<Integer>> adj) {
        int[] in_degree = new int[adj.size()];
        Arrays.fill(in_degree, 0);
        boolean[] visited = new boolean[adj.size()];
        Arrays.fill(visited, false);
        ArrayList<Integer> sorted = new ArrayList<>();
        for (ArrayList<Integer> chaps : adj) {
            for (int c : chaps) {
                in_degree[c]++;
            }
        }
        return sort(adj, sorted, in_degree, visited, 0);
    }

    private static int sort(ArrayList<ArrayList<Integer>> adj, ArrayList<Integer> sorted, int[] in_degree, boolean[] visited, int depth) {
        if (sorted.size() == chapCount) {
            return 1;
        }
        ArrayList<Integer> current = new ArrayList<>();
        for (int i = 0; i < chapCount; i++) {
            if (in_degree[i] == 0 && !visited[i]) {
                current.add(i);
            }
        }
        if (current.isEmpty()) {
            return 0;
        }
        int count = 0;
        for (int c : current) {
            if (sorted.size() > 0 && c == characterIndex[sorted.get(depth-1)]) {
                continue;
            }
            visited[c] = true;
            for (int i : adj.get(c)) {
                in_degree[i]--;
            }
            sorted.add(c);
            count += sort(adj, sorted, in_degree, visited, depth+1);
            visited[c] = false;
            for (int i : adj.get(c)) {
                in_degree[i]++;
            }
            sorted.remove(depth);
        }
        return count;
    }
}