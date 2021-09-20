import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Relativity {
    public static void main(String[] args) throws IOException {
        InputStreamReader r = new InputStreamReader(System .in);
        BufferedReader in = new BufferedReader (r);
        int n = Integer.parseInt(in.readLine());
        long c = 299792458;
        long c_sqr = c * c;
        for (int i = 1; i <= n; i++) {
            int m = Integer.parseInt(in.readLine());
            System.out.println("Case #" + i + ": " + c_sqr * m);
        }
        in.close();
        r.close();
    }
}