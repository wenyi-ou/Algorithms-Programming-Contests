import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Surveillance {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            int n = Integer.parseInt(in.readLine());//number of cameras
            Camera[] cs = new Camera[n];
            for (int i = 0; i < n; i++) {
                String[] params = in.readLine().split(" ");
                double x = Double.parseDouble(params[0]);
                double y = Double.parseDouble(params[1]);
                cs[i] = new Camera(x, y);
            }

            double area = 0;
            double r1 = Double.MAX_VALUE;
            double r2 = Double.MAX_VALUE;
            for (int i = 1; i < n; i++) {
                double d = Math.sqrt(Math.pow(cs[i].x - cs[0].x, 2)
                        + Math.pow(cs[i].y - cs[0].y, 2));
                r1 = Math.min(r1, d);
            }
            for (int i = 1; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    double dh = Math.sqrt(Math.pow(cs[i].x - cs[j].x, 2)
                            + Math.pow(cs[i].y - cs[j].y, 2)) / 2;
                    r2 = Math.min(r2, dh);
                }
            }
            double r = Math.min(r1, r2);
            double curr = Math.PI * Math.max(Math.pow(r1, 2) //only first camera is (literally) on
                    , Math.pow(r1 - r, 2) + (n-1) * Math.pow(r, 2));
            area = Math.max(area, curr);

            System.out.println("Case #" + caseNr + ": " + area);
            in.readLine();
        }
        in.close();
        reader.close();
    }
}

class Camera{
    double x, y;

    public Camera(double a, double b) {
        x = a;
        y = b;
    }
}
