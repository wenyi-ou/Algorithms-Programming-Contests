import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class StreetLights {
    static int l, n, d;
    static ArrayList<Integer> pos;

    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            String[] params = in.readLine().split(" ");
            l = Integer.parseInt(params[0]);//length of the main street
            n = Integer.parseInt(params[1]);//number of street lights on main street
            d = Integer.parseInt(params[2]);//the radius of the light cone of each street light
            pos = new ArrayList<>();
            String[] posStr = in.readLine().split(" ");
            for (int i = 0; i < n; i++) {
                pos.add(Integer.parseInt(posStr[i]));
            }
            Collections.sort(pos);
            boolean feasible = n > 0 && d > 0 && d >= pos.get(0);
            int count = 0;
            if (feasible) {
                int l0 = 0;
                for (int i = 0; i < n - 1 && l0 + d >= pos.get(i) && l0 < l; i++) {
                    if (l0 < pos.get(i + 1) - d) {
                        l0 = pos.get(i) + d;
                        count++;
                    }
                }
                if (l0 < l && l0 >= pos.get(n - 1) - d) {
                    l0 = pos.get(n - 1) + d;
                    count++;
                }
                feasible = l0 >= l;
            }
            if (feasible)
                System.out.println("Case #" + caseNr + ": " + count);
            else
                System.out.println("Case #" + caseNr + ": impossible");
            in.readLine();
        }
        in.close();
        reader.close();
    }
}