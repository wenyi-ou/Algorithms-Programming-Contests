import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StoneGarden {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            String[] params = in.readLine().split(" ");
            int k = Integer.parseInt(params[0]);
            int n = Integer.parseInt(params[1]);
            int m = Integer.parseInt(params[2]);
            double angle = Math.PI / n;
            double sin = Math.sin(angle);
            double cos = Math.cos(angle);
            double r = sin / (1.0 - sin);
            double r_outer = r + 1;
            for (int i = 1; i < m; i++) {
                double tmp = Math.sqrt(r * r + 2 * r * r_outer * cos * sin);
                r_outer = (r_outer * cos + r * sin + tmp) / (cos * cos);
                r = r_outer * sin;
            }
            double l = 2 * n * r + 2 * Math.PI * r;
            System.out.format("%d %.3f %.3f", k, r, l);
            System.out.println();
        }
        in.close();
        reader.close();
    }
}
