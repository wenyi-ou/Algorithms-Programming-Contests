import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

//Reference: https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/
public class AzrieliTowers {
    static Stone[] stones;
    static int n;

    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            n = Integer.parseInt(in.readLine());
            //number of cornerstone coordinates in the particular set
            stones = new Stone[n];
            for (int i = 0; i < n; i++) {
                String[] params = in.readLine().split(" ");
                int x = Integer.parseInt(params[0]);
                int y = Integer.parseInt(params[1]);
                stones[i] = new Stone(x, y);
            }

            boolean feasible = false;
            Rectangle rec = new Rectangle(null, null, null, null);
            Triangle tri = new Triangle(null, null, null);
            loop: for (int a = 0; a < n; a++) {
                for (int b = 0; b < n; b++) {
                    if (b == a) continue;
                    for (int c = 0; c < n; c++) {
                        if (c == a || c == b) continue;
                        for (int d = 0; d < n; d++) {
                            if (d == a || d == b || d == c) continue;
                            rec = new Rectangle(stones[a], stones[b], stones[c], stones[d]);
                            if (isRec(rec)) {
                                Triangle currTri = findTri(a, b, c, d);
                                if (currTri != null) {
                                    tri = currTri;
                                    feasible = true;
                                    break loop;
                                }
                            }
                        }
                    }
                }
            }

            if (feasible) {
                System.out.println("Case #" + caseNr + ": possible");
                System.out.println(rec.a.x + " " + rec.a.y);
                System.out.println(rec.b.x + " " + rec.b.y);
                System.out.println(rec.c.x + " " + rec.c.y);
                System.out.println(rec.d.x + " " + rec.d.y);
                System.out.println(tri.a.x + " " + tri.a.y);
                System.out.println(tri.b.x + " " + tri.b.y);
                System.out.println(tri.c.x + " " + tri.c.y);
            } else
                System.out.println("Case #" + caseNr + ": impossible");
            in.readLine();
        }
        in.close();
        reader.close();
    }

    static boolean isRec (Rectangle rec) {
        if (rec.a.x == rec.b.x && rec.c.x == rec.d.x)
            return rec.b.y == rec.c.y && rec.a.y == rec.d.y;
        else if (rec.a.x == rec.b.x || rec.c.x == rec.d.x)
            return false;
        if (rec.a.x == rec.d.x && rec.b.x == rec.c.x)
            return rec.a.y == rec.b.y && rec.c.y == rec.d.y;
        else if (rec.a.x == rec.d.x || rec.b.x == rec.c.x)
            return false;

        if ((rec.a.y - rec.b.y) * (rec.c.x - rec.d.x) != (rec.c.y - rec.d.y) * (rec.a.x - rec.b.x))
            return false;
        if ((rec.a.y - rec.d.y) * (rec.b.x - rec.c.x) != (rec.b.y - rec.c.y) * (rec.a.x - rec.d.x))
            return false;
        return (rec.a.y - rec.b.y) * (rec.b.y - rec.c.y) == -(rec.a.x - rec.b.x) * (rec.b.x - rec.c.x);
    }

    static Triangle findTri (int a, int b, int c, int d) {
        Rectangle rec = new Rectangle(stones[a], stones[b], stones[c], stones[d]);
        ArrayList<Side> recSides = new ArrayList<>();
        recSides.add(new Side(rec.a, rec.b));
        recSides.add(new Side(rec.b, rec.c));
        recSides.add(new Side(rec.c, rec.d));
        recSides.add(new Side(rec.d, rec.a));
        for (int i = 0; i < n; i++) {
            if (i == a || i == b || i == c || i == d || isInside(stones[i], recSides))
                continue;
            for (int j = i + 1; j < n; j++) {
                if (j == a || j == b || j == c || j == d || isInside(stones[j], recSides))
                    continue;
                for (int k = j + 1; k < n; k++) {
                    if (k == a || k == b || k == c || k == d || isInside(stones[k], recSides))
                        continue;
                    Triangle tri = new Triangle(stones[i], stones[j], stones[k]);
                    ArrayList<Side> triSides = new ArrayList<>();
                    triSides.add(new Side(tri.a, tri.b));
                    triSides.add(new Side(tri.b, tri.c));
                    triSides.add(new Side(tri.c, tri.a));
                    if (!isInside(stones[a], triSides) && !isInside(stones[b], triSides)
                            && !isInside(stones[c], triSides) && !isInside(stones[d], triSides)
                            && !overlap(recSides, triSides))
                        return tri;
                }
            }
        }
        return null;
    }

    static boolean overlap(ArrayList<Side> recSides, ArrayList<Side> triSides) {
        for (Side s1 : recSides) {
            for (Side s2 : triSides) {
                if (intersect(s1.a, s1.b, s2.a, s2.b))
                    return true;
            }
        }
        return false;
    }

    static boolean intersect(Stone p1, Stone q1, Stone p2, Stone q2) {
        int l11 = ccw(p1, q1, p2);
        int l12 = ccw(p1, q1, q2);
        int l21 = ccw(p2, q2, p1);
        int l22 = ccw(p2, q2, q1);
        if (l11 != l12 && l21 != l22)
            return true;
        if (l11 == 0 && onSegment(p1, p2, q1))
            return true;
        if (l12 == 0 && onSegment(p1, q2, q1))
            return true;
        if (l21 == 0 && onSegment(p2, p1, q2))
            return true;
        return l22 == 0 && onSegment(p2, q1, q2);
    }

    static boolean onSegment(Stone a, Stone p, Stone b) {
        return ((a.x <= p.x && p.x <= b.x) || (b.x <= p.x && p.x <= a.x)) &&
                ((a.y <= p.y && p.y <= b.y) || (b.y <= p.y && p.y <= a.y));
    }

    static int ccw(Stone p, Stone q, Stone r) {
        double val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        if (val > 0) //clockwise
            return 1;
        if (val < 0) //counterclockwise
            return 2;
        return 0; //co-linear
    }

    static boolean isInside(Stone stone, ArrayList<Side> sides) {
        int x = stone.x;
        int y = stone.y;
        int count = 0;
        for (Side s: sides) {
            int x1 = s.a.x;
            int y1 = s.a.y;
            int x2 = s.b.x;
            int y2 = s.b.y;
            if (y1 == y2) continue;
            if (x1 == x2) {
                if (x <= x1 && y < Math.max(y1, y2) && y >= Math.min(y1, y2))
                    count++;
                continue;
            }
            if (y1 < y2) {
                count = checkSlope(x, y, count, x2, y2, x1, y1);
            } else {
                count = checkSlope(x, y, count, x1, y1, x2, y2);
            }
        }
        return count % 2 != 0;
    }

    private static int checkSlope(int x, int y, int count, int x1, int y1, int x2, int y2) {
        if (y >= y1 || y < y2)
            return count;
        if (y == y2) {
            if (x <= x2)
                count++;
            return count;
        }
        if (x == x2){
            if (x2 < x1)
                count++;
            return count;
        }
        if ((y-y2)*(x1-x2) > (y1-y2)*(x-x2))
            count++;
        return count;
    }
}

class Stone implements Comparable<Stone>{
    int x, y;

    public Stone(int a, int b) {
        x = a;
        y = b;
    }

    @Override
    public int compareTo(Stone p) {
        if (this.x == p.x) {
            return this.y - p.y;
        }
        return this.x - p.x;
    }
}

class Side{
    Stone a, b;

    public Side (Stone a, Stone b) {
        this.a = a;
        this.b = b;
    }
}

class Triangle {
    Stone a, b, c;

    public Triangle(Stone a, Stone b, Stone c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
}

class Rectangle {
    Stone a, b, c, d;

    public Rectangle(Stone a, Stone b, Stone c, Stone d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
}
