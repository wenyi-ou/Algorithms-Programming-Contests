import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Bracelets {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            char[] b1 = in.readLine().toCharArray();
            char[] b2 = in.readLine().toCharArray();

            int l = b1.length;
            int maxLength = 0;
            for (int i = 0; i < l; i++) {
                maxLength = Math.max(maxLength, getLCS(b1, b2));
                b1 = shift(b1);
            }
            char[] b1r = new char[l];
            for(int i = 0; i < l; i++) {
                b1r[i] = b1[l-1-i];
            }
            maxLength = Math.max(maxLength, getLCS(b1r, b2));
            for (int i = 0; i < l - 1; i++) {
                b1r = shift(b1r);
                maxLength = Math.max(maxLength, getLCS(b1r, b2));
            }

            System.out.println("Case #" + caseNr + ": " + maxLength);
            in.readLine();
        }
        in.close();
        reader.close();
    }

    private static char[] shift(char[] x) {
        char[] ret = new char[x.length];
        for (int i = 0; i < x.length - 1; i++) {
            ret[i] = x[i+1];
        }
        ret[x.length - 1] = x[0];
        return ret;
    }

    private static int getLCS(char[] a, char[] b) {
        int l1 = a.length;
        int l2 = b.length;
        int table[][] = new int[l1 + 1][l2 + 1];
        for (int i = 1; i <= l1; i++) {
            for (int j = 1; j <= l2; j++) {
                if (a[i - 1] == b[j - 1]) {
                    table[i][j] = table[i - 1][j - 1] + 1;
                } else {
                    table[i][j] = Math.max(table[i - 1][j], table[i][j - 1]);
                }
            }
        }
        return table[a.length][b.length];
    }
}