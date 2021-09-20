import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//reference: https://cp-algorithms.com/combinatorics/binomial-coefficients.html

public class SoftSkills {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            String[] paramLine = in.readLine().split(" ");
            int n = Integer.parseInt(paramLine[0]);//number of courses available
            int m = Integer.parseInt(paramLine[1]);//number of courses needed for the certificate
            rs = new long[9];
            for (int i = 0; i < 9; i++) {
                int prime = primes[i];
                rs[i] = getBinomialLucasTheorem(n, m, prime);
            }
            System.out.println("Case #" + caseNr + ": " + chineseRemainder());
        }
        in.close();
        reader.close();
    }

    private static final int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23};
    private static long[] rs;

    private static long getBinomialLucasTheorem (long n, long m, int prime) {
        if (m == 0) {
            return 1;
        }
        return getBinomialLucasTheorem(Math.floorDiv(n, prime), Math.floorDiv(m, prime), prime)
                * getBinomial(n % prime, m % prime) % prime;
    }

    private static long getBinomial (long n, long k) {
        if (k == 0) return 1;
        if (n == 0) return 0;
        return getBinomial(n-1, k-1) + getBinomial(n-1, k);
    }

    private static long chineseRemainder() {
        long m = 1;
        for (int i = 0; i < 9; i++) {
            m *= primes[i];
        }
        long[] ms = new long[9];
        for (int i = 0; i < 9; i++) {
            ms[i] = m / primes[i];
        }
        long[] ts = new long[9];
        for (int i = 0; i < 9; i++) {
            ts[i] = modularInverse(ms[i], primes[i]);
        }
        long acc = 0;
        for (int i = 0; i < 9; i++) {
            acc += rs[i] * ts[i] * ms[i];
        }
        return acc % m;
    }

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