import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class TournamentConfusion {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            String[] params = in.readLine().split(" ");
            r = Integer.parseInt(params[0]);//number of regions
            n = Integer.parseInt(params[1]);//number of teams per region
            m = Integer.parseInt(params[2]);//number of teams admitted to the final showdown from each region
            d = Integer.parseInt(params[3]);//number of disciplines (at the regional level)
            regionals = new ArrayList<>();
            global = new HashMap<>();
            //teams inside a stage are listed in the ascending order;
            //first n teams belong to the region 1, next n to the regions 2, etc
            for (int region = 0; region < r; region ++) {
                HashMap<Integer, ArrayList<Float>> map = new HashMap<>();
                for (int i = 0; i < n; i++) {
                    params = in.readLine().split(" ");
                    int team = Integer.parseInt(params[1]);//number of the team (from 1 to r * n)
                    ArrayList<Float> scores = new ArrayList<>();
                    for (int j = 2; j < d + 2; j++) {
                        scores.add(Float.parseFloat(params[j]));//team’s d scores (floating-point numbers).
                    }
                    map.put(team, scores);
                }
                regionals.add(map);
            }
            for (int i = 0; i < r * m; i++) {
                params = in.readLine().split(" ");
                int team = Integer.parseInt(params[1]);//number of the team (from 1 to r * m)
                ArrayList<Float> scores = new ArrayList<>();
                for (int j = 2; j < d + 2; j++) {
                    scores.add(Float.parseFloat(params[j]));//team’s d scores (floating-point numbers).
                }
                global.put(team, scores);
            }

            int strangeRegion = 0;
            for (int i = 0; i < r; i++) {
                if (isStrange(i)) {
                    strangeRegion = i + 1;
                    break;
                }
            }

            System.out.println("Case #" + caseNr + ": " + strangeRegion);
            in.readLine();
        }
        in.close();
        reader.close();
    }

    static int r, n, m, d;
    static ArrayList<HashMap<Integer, ArrayList<Float>>> regionals;
    static HashMap<Integer, ArrayList<Float>> global;

    static boolean isStrange(int region) {
        HashMap<Integer, ArrayList<Float>> regionalScores = regionals.get(region);
        HashMap<Integer, ArrayList<Float>> globalScores = new HashMap<>();
        for (Integer team : regionalScores.keySet()) {
            if (global.containsKey(team))
                globalScores.put(team, global.get(team));
        }
        Object[] teams = globalScores.keySet().toArray();

        for (int i = 0; i < teams.length - 1; i++) {
            for (int j = i + 1; j < teams.length; j++) {
                int count = 0;
                for (int dis = 0; dis < d; dis++) {
                    float ir = regionalScores.get(teams[i]).get(dis);
                    float jr = regionalScores.get(teams[j]).get(dis);
                    float ig = globalScores.get(teams[i]).get(dis);
                    float jg = globalScores.get(teams[j]).get(dis);
                    if ((ir - jr) * (ig - jg) < 0)
                        count++;
                }
                if (count == d)
                    return true;
            }
        }
        return false;
    }
}