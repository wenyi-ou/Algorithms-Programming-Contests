import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Arrays;

public class ChessTournament {
    public static void main(String[] args) throws IOException {
        InputStreamReader r = new InputStreamReader(System .in);
        BufferedReader in = new BufferedReader (r);
        int n = Integer.parseInt(in.readLine());
        for (int i = 1; i <= n; i++) {
            System.out.println("Case #" + i + ":");
            int num = Integer.parseInt(in.readLine());
            int[][] players = new int[num][5];
            for (int j = 0; j < num; j++) {
                String[] str = in.readLine().split(" ");
                for (int k = 0; k < 5; k++) {
                    players[j][k] = Integer.parseInt(str[k]);
                }
            }
            players = sortPlayers(players);
            players = sortTeams(players);
            for (int j = 0; j < num; j++) {
                for (int k = 0; k < 5; k++) {
                    System.out.print(players[j][k]);
                    if (k < 4){
                        System.out.print(" ");
                    } else {
                        System.out.print("\n");
                    }
                }

            }
            in.readLine();
        }
        in.close();
        r.close();
    }

    private static int[][] sortPlayers(int[][] players) {
        int num = players.length;
        for (int i = 0; i < num; i++) {
            //sort the players of the team in ascending order
            Arrays.sort(players[i]);
            //reverse the order of the team
            int[] temp = new int[5];
            for (int j = 0; j < 5; j++) {
                temp[j] = players[i][4-j];
            }
            players[i] = temp;
            //now each single team is sorted in descending order
        }
        return players;
    }

    private static int[][] sortTeamsViaSinglePlayer(int[][] players, int index) {
        int num = players.length;
        //sort the teams based on the player ranked [index] using bubble sort
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num-1; j++) {
                if (players[j][index] < players[j+1][index]) {
                    //swap the teams with index j and j+1
                    int[] temp = players[j];
                    players[j] = players[j+1];
                    players[j+1] = temp;
                }
            }
        }
        return players;
    }

    private static int[][] sortTeams(int[][] players) {
        for (int i = 4; i >= 0; i--) {
            players = sortTeamsViaSinglePlayer(players, i);
        }
        return players;
    }
}