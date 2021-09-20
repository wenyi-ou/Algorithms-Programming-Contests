import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class CableCar {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int n = Integer.parseInt(in.readLine());
        for (int i = 1; i <= n; i++) {
            String[] numParts = in.readLine().split(" ");
            int d = Integer.parseInt(numParts[0]);
            int p = Integer.parseInt(numParts[1]);
            int u = Integer.parseInt(numParts[2]);
            int v = Integer.parseInt(numParts[3]);

            //find the maximum minimum distance via binary search
            double l = 0;
            double r = d;
            while (r - l > 0.0001) {
                double m = (l + r) / 2;
                if (feasible(d, p ,u, v, m)) {
                    l = m;
                } else {
                    r = m;
                }
            }

            String distOut =String.format("%.10f", l);
            System.out.println("Case #" + i + ": " + distOut);
        }
        in.close();
        reader.close();
    }

    /**
     * @param d length of the route
     * @param p number of posts
     * @param u begin point of canyon
     * @param v end point of canyon
     * @param x distance between posts that is to be tested
     * @return if this distance is possible for the input
     */
    private static boolean feasible(int d, int p, int u, int v, double x) {
        int numBefore = (int) Math.floor(u / x) + 1;
        int numAfter = (int) Math.floor((d-v) / x) + 1;
        double distBeforeCanyon = u - x * (numBefore - 1);
        double distAfterCanyon = (d-v) - x * (numAfter - 1);
        double distAcrossCanyon = distBeforeCanyon + (v-u) + distAfterCanyon;
        int numPostsRemaining = p - numBefore - numAfter;
        if (numPostsRemaining > 0) {
            //unable to place all the posts
            return false;
        }
        if (numPostsRemaining == 0 && distAcrossCanyon < x) {
            //the distance between the posts before and after the canyon is less than x
            return false;
        }
        return true;
    }
}