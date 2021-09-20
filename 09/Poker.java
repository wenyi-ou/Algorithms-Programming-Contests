import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Poker {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            int n = Integer.parseInt(in.readLine());//amount of tournaments
            Tournament[] ts = new Tournament[n];
            for (int i = 0; i < n; i++) {
                String[] params = in.readLine().split(" ");
                int a = Integer.parseInt(params[0]);//tournament starts on day a
                int b = Integer.parseInt(params[1]);//ends on day b
                int p = Integer.parseInt(params[2]);//has a prize of p for the winner
                ts[i] = new Tournament(a, b, p);
            }
            Arrays.sort(ts);

            int max = -1;
            int[] maxPrizes = new int[n];
            for (int i = 0; i < n; i++) {
                Tournament curr = ts[i];
                int currMax = 0;
                for (int j = 0; j < i; j++) {
                    Tournament before = ts[j];
                    if (curr.start > before.end) {
                        currMax = Math.max(currMax, maxPrizes[j]);
                    }
                }
                maxPrizes[i] = currMax + curr.prize;
                max = Math.max(max, maxPrizes[i]);
            }

            System.out.println("Case #" + caseNr + ": " + max);
            in.readLine();
        }
        in.close();
        reader.close();
    }
}

class Tournament implements Comparable<Tournament> {
    int start, end, prize;

    public Tournament (int a, int b, int p) {
        this.start = a;
        this.end = b;
        this.prize = p;
    }

    @Override
    public int compareTo(Tournament t) {
        return this.start - t.start;
    }
}