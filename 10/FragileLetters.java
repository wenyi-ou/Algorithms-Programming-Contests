import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FragileLetters {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            int n = Integer.parseInt(in.readLine());//number of vertices
            Vertex[] vertices = new Vertex[n];
            double minX = Double.MAX_VALUE;
            int minIndex = 0;
            for (int i = 0; i < n; i++) {
                String[] params = in.readLine().split(" ");
                double x = Double.parseDouble(params[0]);
                double y = Double.parseDouble(params[1]);
                vertices[i] = new Vertex(x, y);
                if (x < minX || (x == minX && y < vertices[minIndex].y)) {
                    minX = x;
                    minIndex = i;
                }
            }

            //determines the centroid
            double area = 0.0, cx = 0.0, cy = 0.0;
            for (int i = 0; i < n; i++) {
                Vertex a = vertices[i];
                Vertex b = vertices[(i + 1) % n];
                double temp = a.x * b.y - b.x * a.y;
                area += temp;
                cx += (a.x + b.x) * temp;
                cy += (a.y + b.y) * temp;
            }
            area /= 2;
            cx /= 6 * area;
            cy /= 6 * area;
            Vertex centroid = new Vertex(cx, cy);

            int count = 0;
            loop: for (int i = 0; i < n; i++) {
                Vertex v1 = vertices[i];
                Vertex v2 = vertices[(i + 1) % n];
                int ccw = ccw(v1, v2, centroid);
                for (int j = 0; j < n; j++) {
                    if (j == i || j == (i + 1) % n)
                        continue;
                    if (ccw(v1, v2, vertices[j]) != ccw)
                        continue loop;
                }
                //check whether centroid is above the lowermost edge
                double ds12 = distSquare(v1, v2);
                double ds1c = distSquare(v1, centroid);
                double ds2c = distSquare(v2, centroid);
                if (ds12 + ds2c >= ds1c && ds12 + ds1c >= ds2c)
                    count++;
            }

            System.out.println("Case #" + caseNr + ": " + count);
            in.readLine();
        }
        in.close();
        reader.close();
    }

    static double distSquare(Vertex a, Vertex b) {
        return Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2);
    }

    static int ccw(Vertex p, Vertex q, Vertex r) {
        double val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        if (val > 0) //clockwise
            return 1;
        if (val < 0) //counterclockwise
            return 2;
        return 0; //co-linear
    }
}

class Vertex{
    double x, y;

    public Vertex(double a, double b) {
        this.x = a;
        this.y = b;
    }
}