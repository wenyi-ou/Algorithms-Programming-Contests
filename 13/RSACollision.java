import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

public class RSACollision {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            int n = Integer.parseInt(in.readLine());//number of public keys included in the batch
            ArrayList<BigInteger> keys = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                keys.add(new BigInteger(in.readLine()));
            }
            ArrayList<Pair> factor = new ArrayList<>();
            boolean[] added = new boolean[n];
            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    BigInteger k1 = keys.get(i);
                    BigInteger k2 = keys.get(j);
                    BigInteger gcd = k1.gcd(k2);
                    if (gcd.compareTo(new BigInteger("1")) != 0) {
                        if (!added[i]) {
                            factor.add(new Pair(gcd, k1.divide(gcd)));
                            added[i] = true;
                        }
                        if (!added[j]) {
                            factor.add(new Pair(gcd, k2.divide(gcd)));
                            added[j] = true;
                        }
                    }
                }
            }
            Collections.sort(factor);

            System.out.println("Case #" + caseNr + ":");
            for (Pair p : factor)
                System.out.println(p.a + " " + p.b);
            System.out.println();
            in.readLine();
        }
        in.close();
        reader.close();
    }
}

class Pair implements Comparable<Pair> {
    BigInteger a, b;

    Pair(BigInteger p1, BigInteger p2) {
        if (p1.compareTo(p2) > 0) {
            this.a = p2;
            this.b = p1;
        } else {
            this.a = p1;
            this.b = p2;
        }
    }

    @Override
    public int compareTo(Pair p) {
        if (this.a.compareTo(p.a) == 0)
            return this.b.compareTo(p.b);
        return this.a.compareTo(p.a);
    }
}
