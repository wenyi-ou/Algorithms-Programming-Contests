import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Fallingwater {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            String[] params = in.readLine().split(" ");
            int n = Integer.parseInt(params[0]);
            int x = Integer.parseInt(params[1]);
            int y = Integer.parseInt(params[2]);
            Point source = new Point(x, y);
            ledges = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                params = in.readLine().split(" ");
                int x1 = Integer.parseInt(params[0]);
                int y1 = Integer.parseInt(params[1]);
                params = in.readLine().split(" ");
                int x2 = Integer.parseInt(params[0]);
                int y2 = Integer.parseInt(params[1]);

            }
            System.out.println("Case #" + caseNr + ": ");
            in.readLine();
        }
        in.close();
        reader.close();
    }

    static ArrayList<ArrayList<Point>> ledges;

    //point p lies on segment (a,b)
    static boolean onSegment(Point a, Point p, Point b) {
        if (((a.x <= p.x && p.x <= b.x) || (b.x <= p.x && p.x <= a.x)) &&
                ((a.y <= p.y && p.y <= b.y) || (b.y <= p.y && p.y <= a.y))) {
            return true;
        }
        return false;
    }

    //returns true if the line segments (p1,q1) and (p2,q2) intersect
    static boolean doIntersect(Point p1, Point q1, Point p2, Point q2) {

        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        if (o1 != o2 && o3 != o4) {
            return true;
        }

        if (o1 == 0 && onSegment(p1, p2, q1)) {
            return true;
        }

        if (o2 == 0 && onSegment(p1, q2, q1)) {
            return true;
        }

        if (o3 == 0 && onSegment(p2, p1, q2)) {
            return true;
        }

        if (o4 == 0 && onSegment(p2, q1, q2)) {
            return true;
        }

        return false;
    }

    static Point computeIntersection(Point a, Point b, Point c, Point d) {
        double px = ((b.x - a.x) * (c.x * d.y - d.x * c.y) - (d.x - c.x) * (a.x * b.y - b.x * a.y)) /
                ((b.x - a.x) * (d.y - c.y) - (b.y - a.y) * (d.x - c.x));
        double py = ((b.y - a.y) * (c.x * d.y - d.x * c.y) - (d.y - c.y) * (a.x * b.y - b.x * a.y)) /
                ((b.x - a.x) * (d.y - c.y) - (b.y - a.y) * (d.x - c.x));
        return new Point(px, py);
    }

    static int orientation(Point p, Point q, Point r) {
        double val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);

        if (val == 0) return 0;
        return (val > 0) ? 1 : 2;
    }
}

class Point implements Comparable<Point> {
    double x, y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Point p) {
        double diff = this.y - p.y;
        if (diff > 0)
            return -1;
        else if (diff < 0)
            return 1;
        else
            return 0;
    }
}