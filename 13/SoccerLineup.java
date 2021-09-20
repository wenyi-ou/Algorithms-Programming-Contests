import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SoccerLineup {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            pos = new ArrayList<>();
            ArrayList<ArrayList<Integer>> tactic = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                pos.add(new ArrayList<>());
                tactic.add(new ArrayList<>());
            }
            for (int player = 1; player <= 11; player++) {
                String s = in.readLine();
                for (char c : s.toCharArray()) {
                    if (c == 'G')
                        pos.get(0).add(player);
                    else if (c == 'D')
                        pos.get(1).add(player);
                    else if (c == 'M')
                        pos.get(2).add(player);
                    else if (c == 'S')
                        pos.get(3).add(player);
                    else
                        throw new IOException("Error: invalid position");
                }
            }
            ret = 0;
            solve(1, tactic);
            System.out.println("Case #" + caseNr + ": " + ret);
            in.readLine();
        }
        in.close();
        reader.close();
    }

    static int ret;
    static ArrayList<ArrayList<Integer>> pos;

    static void solve(int player, ArrayList<ArrayList<Integer>> tactic) {
        if (player > 11) {
            /*
            for (int i = 0; i < 4; i++) {
                System.out.print(i + ":");
                for (Integer p : tactic.get(i)) {
                    System.out.print(" " + p);
                }
                System.out.println();
            }
            System.out.println();
             */
            if (tactic.get(0).size() == 1)
                ret ++;
            return;
        }
        for (int i = 0; i < 4; i++) {
            if (i == 0 && tactic.get(0).size() > 0)
                continue;
            if (!pos.get(i).contains(player))
                continue;
            if (tactic.get(i).contains(13 - player))
                continue;
            ArrayList<ArrayList<Integer>> newTactic = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                newTactic.add(new ArrayList<>(tactic.get(j)));
            }
            newTactic.get(i).add(player);
            solve(player + 1, newTactic);
        }
    }
}