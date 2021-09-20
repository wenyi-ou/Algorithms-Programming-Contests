import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class Keyboards {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            String[] paramLine = in.readLine().split(" ");
            BigInteger[]  ms = new BigInteger[2];
            BigInteger[] rs = new BigInteger[2];
            rs[0] = new BigInteger(paramLine[0]);//numbers of special keys
            ms[0] = new BigInteger(paramLine[1]);//number of keys per column
            rs[1] = new BigInteger(paramLine[2]);
            ms[1] = new BigInteger(paramLine[3]);
            BigInteger n = new BigInteger(paramLine[4]);//minimal acceptable number of keys

            BigInteger gcd = ms[0].gcd(ms[1]);
            if (rs[1].subtract(rs[0]).remainder(gcd).compareTo(new BigInteger("0")) != 0) {
                System.out.println("Case #" + caseNr + ": impossible");
            } else {
                //k0*ms[0] + rs[0] = k1*ms[1] + rs[1]
                //k0*p0 - k1*p1 = (rs[1]-rs[0])/gcd
                BigInteger p0 = ms[0].divide(gcd);
                BigInteger p1 = ms[1].divide(gcd);
                //eea[0]*p0 + eea[1]*p1 = 1
                BigInteger[] eea = extendedEuclidean(p0, p1);
                BigInteger k0 = eea[0].multiply(rs[1].subtract(rs[0])).divide(gcd);
                BigInteger res = k0.multiply(ms[0]).add(rs[0]);
                BigInteger m = ms[0].multiply(ms[1]).divide(gcd);
                while (res.compareTo(n) >= 0) {
                    res = res.subtract(m);
                } while (res.compareTo(n) < 0 || res.compareTo(rs[0]) < 0 || res.compareTo(rs[1]) < 0) {
                    res = res.add(m);
                }
                System.out.println("Case #" + caseNr + ": " + res);
            }
        }
        in.close();
        reader.close();
    }

    private static BigInteger[] extendedEuclidean(BigInteger a, BigInteger b) {
        BigInteger s = new BigInteger("0");
        BigInteger sp = new BigInteger("1");
        BigInteger t = new BigInteger("1");
        BigInteger tp = new BigInteger("0");
        BigInteger r = b;
        BigInteger rp = a;
        while (r.compareTo(new BigInteger("0")) != 0) {
            BigInteger q = rp.divide(r);
            BigInteger tmp = r;
            r = rp.subtract(q.multiply(r));
            rp = tmp;
            tmp = s;
            s = sp.subtract(q.multiply(s));
            sp = tmp;
            tmp = t;
            t = tp.subtract(q.multiply(t));
            tp = tmp;
        }
        return new BigInteger[]{sp, tp};
    }
}