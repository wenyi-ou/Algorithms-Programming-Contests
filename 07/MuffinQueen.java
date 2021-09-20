import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class MuffinQueen {
    static int n, m;
    static boolean[] recipes;
    static ArrayList<ArrayList<Integer>> prefs;

    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            String[] params = in.readLine().split(" ");
            m = Integer.parseInt(params[0]);//number of bakers
            n = Integer.parseInt(params[1]);//number of judges
            prefs = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                prefs.add(new ArrayList<>());
                String[] judgePrefs = in.readLine().split(" ");
                int index = 0;
                while (Integer.parseInt(judgePrefs[index]) != 0) {
                    prefs.get(i).add(Integer.parseInt(judgePrefs[index]));
                    index++;
                }
            }
            recipes = new boolean[m];
            Arrays.fill(recipes, true);
            System.out.print("Case #" + caseNr + ": ");
            if (solve(0)) {
                System.out.println("yes");
            } else {
                System.out.println("no");
            }
            in.readLine();
        }
        in.close();
        reader.close();
    }

    private static boolean solve (int depth) {
        if (depth == m) {
            return isFeasible();
        }

        recipes[depth] = true;
        if (solve(depth + 1))
            return true;

        recipes[depth] = false;
        if (solve(depth + 1))
            return true;

        return false;
    }

    private static boolean isFeasible () {
        for (int i = 0; i < n; i++) {
            boolean feasible = false;
            for (int p : prefs.get(i)) {
                if ((p > 0 && recipes[p-1]) || (p < 0 && !recipes[-p-1])){
                    feasible = true;
                    break;
                }
            }
            if (!feasible)
                return false;
        }
        return true;
    }
}