import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Pathing {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            String[] params = in.readLine().split(" ");
            int w = Integer.parseInt(params[0]);//width of the grid
            int h = Integer.parseInt(params[1]);//height of the grid
            int n = Integer.parseInt(params[2]);//number of impassable locations
            for (int i = 0; i < n; i++) {
                params = in.readLine().split(" ");
                int xi = Integer.parseInt(params[0]);
                int yi = Integer.parseInt(params[1]);
                int wi = Integer.parseInt(params[2]);//width of the grid - right
                int hi = Integer.parseInt(params[2]);//height of the grid - down
            }
            params = in.readLine().split(" ");
            int startX = Integer.parseInt(params[0]);
            int startY = Integer.parseInt(params[1]);
            params = in.readLine().split(" ");
            int targetX = Integer.parseInt(params[0]);
            int targetY = Integer.parseInt(params[1]);



            System.out.println("Case #" + caseNr + ": ");
            in.readLine();
        }
        in.close();
        reader.close();
    }
}
