import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class MakingChange {
    static int n, c;
    static HashMap<Integer, Integer> sol;
    static int[] coins;

    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            String[] params = in.readLine().split(" ");
            n = Integer.parseInt(params[0]);//number of coin and note values
            c = Integer.parseInt(params[1]);//amount of money that must be spent
            coins = new int[n];
            sol = new HashMap<>();
            params = in.readLine().split(" ");
            for (int i = 0; i < n; i++) {
                int tmp = Integer.parseInt(params[i]);
                coins[i] = tmp;
                sol.put(tmp, 0);
            }

            int[] values = new int[c + 1];
            int[] used = new int[c + 1];
            for (int i = 1; i < c + 1; i++) {
                values[i] = Integer.MAX_VALUE;
                for (int coin : coins) {
                    if (i - coin < 0) continue;
                    if (values[i - coin] + 1 < values[i]) {
                        values[i] = values[i - coin] + 1;
                        used[i] = coin;
                    }
                }
            }
            while (c > 0) {
                sol.put(used[c], sol.get(used[c]) + 1);
                c -= used[c];
            }

            System.out.println("Case #" + caseNr + ":");
            for (int i = 0; i < n; i++) {
                System.out.print(sol.get(coins[i]));
                if (i < n - 1) System.out.print(" ");
            }
            System.out.println();
            in.readLine();
        }
        in.close();
        reader.close();
    }
}