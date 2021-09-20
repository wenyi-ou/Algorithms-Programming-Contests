import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//reference: https://www.geeksforgeeks.org/multiplicative-inverse-under-modulo-m/

public class BreakIn {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            String[] paramLine = in.readLine().split(" ");
            int n = Integer.parseInt(paramLine[0]);//number of digits on the display
            int y = Integer.parseInt(paramLine[1]);//number entered on the keypad Y

            System.out.println("Case #" + caseNr + ": " + modularInverse(y, (long) Math.pow(10, n)));
        }
        in.close();
        reader.close();
    }

    //Using extended Euclidean algorithm to find x so that ax + my = 1 (a and m are co-prime)
    private static long modularInverse (long a, long m) {
        if (m == 1) {
            return 0;
        }
        long m_backup = m;

        long x = 1;
        long y = 0;
        while (a > 1) {
            long q = a / m;
            long t = m;

            m = a % m;
            a = t;
            t = y;

            y = x - q * y;
            x = t;
        }

        if (x < 0) {
            x += m_backup;
        }
        return x;
    }
}