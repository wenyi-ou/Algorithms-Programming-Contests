import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class DrawingLots {
    public static void main(String[] args) throws IOException {
        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader (r);
        int n = Integer.parseInt(in.readLine());
        for (int i = 1; i <= n; i++) {
            String[] numParts = in.readLine().split(" ");
            num = Integer.parseInt(numParts[0]);
            cost = Integer.parseInt(numParts[1]);
            values = new int[num];
            String[] valueParts = in.readLine().split(" ");
            for (int j = 0; j < num; j++) {
                values[j] = Integer.parseInt(valueParts[j]);
            }

            double prob = find(0, 1);
            if (prob > 1) {
                prob = 1;
            }
            String probOut =String.format("%.10f", prob);
            System.out.println("Case #" + i + ": " + probOut);
            in.readLine();
        }
        in.close();
        r.close();
    }

    private static int[] values;
    private static int num;
    private static int cost;

    private static double find(double l, double r) {
        double m = (r+l)/2;
        if (l < r) {
            if (expectedPayoff(m) >= 0.000001) {
                return find(l, m);
            } else if (expectedPayoff(m) <= -0.000001) {
                return find(m, r);
            }
        }
        return m;
    }

    private static double expectedPayoff(double prob) {
        double payoff = 0.0;
        for (int i = 1; i <= num; i++) {
            payoff += values[i-1] * Math.pow(prob, i);
        }
        return payoff - cost;
    }
}
