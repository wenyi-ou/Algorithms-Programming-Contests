import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Customs {
    static int n;
    static ArrayList<ArrayList<Integer>> roads;
    static HashSet<Integer> part1, part2;

    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            n = Integer.parseInt(in.readLine());
            roads = new ArrayList<>();
            part1 = new HashSet<>();
            part2 = new HashSet<>();
            for (int i = 0; i < n; i++) {
                String[] paramLine = in.readLine().split(" ");
                int count = Integer.parseInt(paramLine[0]);
                ArrayList<Integer> rs = new ArrayList<>();
                for (int j = 1; j <= count; j++) {
                    rs.add(Integer.parseInt(paramLine[j]) - 1);
                }
                roads.add(rs);
            }

            part1.add(0);
            for (int i = 1; i < n; i++) {
                part1.add(i);
                int addTo1 = countRoads();

                part1.remove(i);
                part2.add(i);
                int addTo2 = countRoads();

                if (addTo1 > addTo2) {
                    part2.remove(i);
                    part1.add(i);
                }
            }

            List<Object> sol = Arrays.asList(part1.toArray());
            Collections.sort(sol, Comparator.comparingInt(e -> (Integer) e));
            System.out.println("Case #" + caseNr + ":");
            for (int i = 0; i < sol.size(); i++) {
                System.out.print((int) sol.get(i) + 1);
                if (i < sol.size() - 1)
                    System.out.print(" ");
                else
                    System.out.println();
            }
            in.readLine();
        }
        in.close();
        reader.close();
    }

    static int countRoads() {
        int ret = 0;
        for(int c1 : part1) {
            for (int c2 : roads.get(c1)) {
                if (part2.contains(c2) && !part1.contains(c2)) {
                    ret++;
                }
            }
        }
        return ret;
    }
}