import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Meteorite {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            String[] params = in.readLine().split(" ");
            //coordinates of the calculated impact site
            int x = Integer.parseInt(params[0]);
            int y = Integer.parseInt(params[1]);
            //number of sides that her parents’ property has
            int n = Integer.parseInt(params[2]);
            ArrayList<Side> sides = new ArrayList<>();
            for (int i =0; i < n; i++) {
                params = in.readLine().split(" ");
                int x1 = Integer.parseInt(params[0]);
                int y1 = Integer.parseInt(params[1]);
                int x2 = Integer.parseInt(params[2]);
                int y2 = Integer.parseInt(params[3]);
                sides.add(new Side(x1, y1, x2, y2));
            }

            int count = 0;
            for (Side s : sides) {
                //System.out.println("count = " + count);
                //System.out.println("side: (" + s.x1 + ", " + s.y1 + ") (" + s.x2 + ", " + s.y2 + ")");
                if (s.y1 == s.y2) continue;
                if (s.x1 == s.x2) {
                    if (x <= s.x1 && y < Math.max(s.y1, s.y2) && y >= Math.min(s.y1, s.y2))
                        count++;
                    continue;
                }
                if (s.y1 < s.y2) {
                    if (y >= s.y2 || y < s.y1)
                        continue;
                    if (y == s.y1) {
                        if (x <= s.x1)
                            count++;
                        continue;
                    }
                    if (x == s.x1) {
                        if (s.x1 < s.x2)
                            count++;
                        continue;
                    }
                    if ((y-s.y1)*(s.x2-s.x1) > (s.y2-s.y1)*(x-s.x1))
                        count++;
                } else {
                    if (y >= s.y1 || y < s.y2)
                        continue;
                    if (y == s.y2) {
                        if (x <= s.x2)
                            count++;
                        continue;
                    }
                    if (x == s.x2){
                        if (s.x2 < s.x1)
                            count++;
                        continue;
                    }
                    if ((y-s.y2)*(s.x1-s.x2) > (s.y1-s.y2)*(x-s.x2))
                        count++;
                }
            }
            //System.out.println("count = " + count);

            if (count % 2 == 0)
                System.out.println("Case #" + caseNr + ": too bad");
            else
                System.out.println("Case #" + caseNr + ": jackpot");
            in.readLine();
        }
        in.close();
        reader.close();
    }
}

class Side{
    int x1, y1, x2, y2;

    public Side (int a, int b, int c, int d) {
        x1 = a;
        y1 = b;
        x2 = c;
        y2 = d;
    }
}