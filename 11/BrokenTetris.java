import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BrokenTetris {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            System.out.print("Case #" + caseNr + ":");
            String[] params = in.readLine().split(" ");
            int n = Integer.parseInt(params[0]);//width of the playing field
            int k = Integer.parseInt(params[1]);//total number of blocks to spawn
            int[] heights = new int[n];
            int ret = 0;
            for (int i = 0; i < k; i++) {
                params = in.readLine().split(" ");
                //width and height of the block
                int w = Integer.parseInt(params[0]);
                int h = Integer.parseInt(params[1]);
                //offset from the left side of the playing field
                int p = Integer.parseInt(params[2]);
                int max = 0;
                for (int index = p; index < p + w; index++) {
                    max = Math.max(max, heights[index]);
                }
                for (int index = p; index < p + w; index++) {
                    ret = Math.max(ret, max + h);
                    heights[index] = max + h;
                }
                System.out.print(" " + ret);
            }

            System.out.println();
            in.readLine();
        }
        in.close();
        reader.close();
    }
}