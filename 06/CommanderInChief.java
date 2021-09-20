import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommanderInChief {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            int n = Integer.parseInt(in.readLine());//the number of armies Lea commands
            String[] paramLine = in.readLine().split(" ");
            int[] sizes = new int[n];
            for (int i = 0; i < n; i++) {
                sizes[i] = Integer.parseInt(paramLine[i]);
            }
            int acc = gcd(sizes[0], sizes[1]);
            for (int i = 2; i < n; i++) {
                acc = gcd(acc, sizes[i]);
            }

            System.out.println("Case #" + caseNr + ": " + acc);
            in.readLine();
        }
        in.close();
        reader.close();
    }

    private static int gcd(int a, int b) {
        if(b == 0)
            return a;
        return gcd(b, a % b);
    }
}