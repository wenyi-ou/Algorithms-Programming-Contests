import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class LibraryHell {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int i = 1; i <= caseNum; i++) {
            String[] nums = in.readLine().split(" ");
            int n = Integer.parseInt(nums[0]);//total number of packages
            int k = Integer.parseInt(nums[1]);//number of packages to keep working
            int r = Integer.parseInt(nums[2]);//number of packages to remove
            int d = Integer.parseInt(nums[3]);//number of package dependency records
            String[] ksLine = in.readLine().split(" ");//to be preserved
            ArrayList<Integer> preserve = new ArrayList<>();
            for (int j = 0; j < k; j++) {
                preserve.add(Integer.parseInt(ksLine[j]) - 1);
            }
            String[] rsLine = in.readLine().split(" ");//to be removed
            boolean[] remove = new boolean[n];
            for (int j = 0; j < n; j++) {
                remove[j] = false;
            }
            for (int j = 0; j < r; j++) {
                remove[Integer.parseInt(rsLine[j]) - 1] = true;
            }
            ArrayList<ArrayList<Integer>> dependency = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                dependency.add(new ArrayList<>());
            }
            for (int j = 0; j < d; j++) {
                String[] dLine = in.readLine().split(" ");
                int ui = Integer.parseInt(dLine[0]) - 1;
                int di = Integer.parseInt(dLine[1]) - 1;
                dependency.get(ui).add(di);
            }

            boolean cyclic = false;
            Queue<Integer> queue = new LinkedList<>();
            boolean[] marked = new boolean[n];
            for (int j = 0; j < n; j++) {
                marked[j] = false;
            }
            for (Integer j : preserve) {
                queue.offer(j);
                marked[j] = true;
            }
            while (!queue.isEmpty()) {
                int current = queue.poll();
                if (remove[current]) {
                    cyclic = true;
                    break;
                }
                for (int j = 0; j < dependency.get(current).size(); j++) {
                    int di = dependency.get(current).get(j);
                    if (!marked[di]) {
                        queue.offer(di);
                        marked[di] = true;
                    }
                }
            }

            if (cyclic) {
                System.out.println("Case #" + i + ": conflict");
            } else {
                System.out.println("Case #" + i + ": ok");
            }
            in.readLine();
        }
        in.close();
        reader.close();
    }
}