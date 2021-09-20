import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

//Reference: https://iq.opengenus.org/box-stacking-problem/
public class PackingCases {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            String[] params = in.readLine().split(" ");
            int h = Integer.parseInt(params[0]);//height the tower should reach
            int n = Integer.parseInt(params[1]);//number of case types
            Box[] boxSizes = new Box[3*n];
            int[] maxHeights = new int[3 * n];
            for (int i = 0; i < n; i++) {
                params = in.readLine().split(" ");
                int[] xyz = new int[]{
                        Integer.parseInt(params[0]),
                        Integer.parseInt(params[1]),
                        Integer.parseInt(params[2])};
                Arrays.sort(xyz);
                boxSizes[3*i] = new Box(xyz[0], xyz[1], xyz[2]);
                maxHeights[3*i] = xyz[2];
                boxSizes[3*i + 1] = new Box(xyz[0], xyz[2], xyz[1]);
                maxHeights[3*i + 1] = xyz[1];
                boxSizes[3*i + 2] = new Box(xyz[1], xyz[2], xyz[0]);
                maxHeights[3*i + 2] = xyz[0];
            }
            Arrays.sort(boxSizes);

            int max = -1;
            for (int i = 0; i < boxSizes.length; i++) {
                Box currBox = boxSizes[i];
                int currMax = 0;
                for (int j = 0; j < i; j++) {
                    Box usedBox = boxSizes[j];
                    if (currBox.x < usedBox.x && currBox.y < usedBox.y) {
                        currMax = Math.max(currMax, maxHeights[j]);
                    }
                }
                maxHeights[i] = currMax + currBox.z;
                max = Math.max(max, maxHeights[i]);
            }

            if (max >= h)
                System.out.println("Case #" + caseNr + ": yes");
            else
                System.out.println("Case #" + caseNr + ": no");
            in.readLine();
        }
        in.close();
        reader.close();
    }
}

class Box implements Comparable<Box> {
    int x, y, z;
    int area;

    public Box (int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.area = x * y;
    }

    @Override
    public int compareTo(Box b) {
        return b.area - this.area;
    }
}