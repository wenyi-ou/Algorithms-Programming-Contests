import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class Diplomacy {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System .in);
        BufferedReader in = new BufferedReader(reader);
        int n = Integer.parseInt(in.readLine());
        for (int i = 1; i <= n; i++) {
            String[] numParts = in.readLine().split(" ");
            int numCountries = Integer.parseInt(numParts[0]);
            int numInteractions = Integer.parseInt(numParts[1]);

            ArrayList<ArrayList<Relation>> crs = new ArrayList<>();
            for (int j = 0; j < numCountries; j++) {
                crs.add(new ArrayList<>());
            }

            for (int  j = 0; j < numInteractions; j++) {
                String[] relationParts = in.readLine().split(" ");
                String relation = relationParts[0];
                int c1 = Integer.parseInt(relationParts[1]) - 1;
                int c2 = Integer.parseInt(relationParts[2]) - 1;
                if (relation.equals("F")) {
                    crs.get(c1).add(new Relation(c2, true));
                    crs.get(c2).add(new Relation(c1, true));
                } else if (relation.equals("A")) {
                    crs.get(c1).add(new Relation(c2, false));
                    crs.get(c2).add(new Relation(c1, false));
                } else {
                    throw new RuntimeException("Unsupported relation!");
                }
            }

            boolean[] visited = new boolean[numCountries];
            visited[0] = true;
            for (int j = 1; j < numCountries; j++) {
                visited[j] = false;
            }

            ArrayList<Relation> relations = new ArrayList<>();
            relations.add(new Relation(0, true));

            int alliance = 0;
            while (relations.size() > 0) {
                int c1 = relations.get(0).country;
                boolean isFriend1 = relations.get(0).isFriend;
                relations.remove(0);
                if (isFriend1) alliance++;
                for (int j = 0; j < crs.get(c1).size(); j++) {
                    int c2 = crs.get(c1).get(j).country;
                    boolean isFriend2 = crs.get(c1).get(j).isFriend;
                    if (!visited[c2]) {
                        visited[c2] = true;
                        if (isFriend1 == isFriend2) {
                            relations.add(new Relation(c2, true));
                        } else {
                            relations.add(new Relation(c2, false));
                        }
                    }
                }
            }

            if (alliance > numCountries/2) {
                System.out.println("Case #" + i + ": yes");
            } else{
                System.out.println("Case #" + i + ": no");
            }
            in.readLine();
        }
        in.close();
        reader.close();
    }
}

class Relation {
    int country;
    boolean isFriend;

    Relation (int c, boolean f) {
        country = c;
        isFriend = f;
    }
}