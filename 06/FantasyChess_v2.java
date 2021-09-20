import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class FantasyChess_v2 {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            String[] paramLine = in.readLine().split(" ");
            //move of the piece
            BigInteger a = new BigInteger(paramLine[0]);
            BigInteger b = new BigInteger(paramLine[1]);
            //target field
            BigInteger x = new BigInteger(paramLine[2]);
            BigInteger y = new BigInteger(paramLine[3]);

            System.out.println("Case #" + caseNr + ":");
            BigInteger gcd = a.gcd(b);
            if (x.compareTo(new BigInteger("0")) == 0 && y.compareTo(new BigInteger("0")) == 0) {
                System.out.println(a + " " + b + " 0");
            } else if (a.compareTo(new BigInteger("0")) == 0 && b.compareTo(new BigInteger("0")) == 0) {
                System.out.println("impossible");
            } else if (x.mod(gcd).compareTo(new BigInteger("0")) == 0
                    && y.mod(gcd).compareTo(new BigInteger("0")) == 0) {
                boolean feasible = false;
                //directions: (a, b), (a, -b), (b, a), (b, -a)
                BigInteger dir1 = new BigInteger("0");
                BigInteger dir2 = new BigInteger("0");
                BigInteger dir3 = new BigInteger("0");
                BigInteger dir4 = new BigInteger("0");

                if (a.compareTo(new BigInteger("0")) != 0 && b.compareTo(new BigInteger("0")) != 0) {
                    BigInteger[] eea = extendedEuclidean(a.divide(gcd), b.divide(gcd));
                    BigInteger double1, double3;
                    if (x.compareTo(new BigInteger("0")) != 0 && y.compareTo(new BigInteger("0")) != 0) {
                        /* eea[0]*a + eea[1]*b = gcd
                           (dir1+dir2)*a + (dir3+dir4)*b = x
                           (dir3-dir4)*a + (dir1-dir2)*b = y */
                        double1 = eea[0].multiply(x).add(eea[1].multiply(y));
                        double3 = eea[0].multiply(y).add(eea[1].multiply(x));
                        if (double1.mod(new BigInteger("2")).compareTo(new BigInteger("0")) == 0 &&
                                double3.mod(new BigInteger("2")).compareTo(new BigInteger("0")) == 0) {
                            feasible = true;
                            dir1 = double1.divide(new BigInteger("2"));
                            dir2 = eea[0].multiply(x).subtract(dir1);
                            dir3 = double3.divide(new BigInteger("2"));
                            dir4 = eea[1].multiply(x).subtract(dir3);
                        }
                    } else {
                        BigInteger ar = a.divide(gcd);
                        BigInteger br = b.divide(gcd);
                        eea = extendedEuclidean(a, b);
                        /* eea[0]*a + eea[1]*b = gcd
                       (dir1+dir2)*a + (dir3+dir4)*b = x
                       (dir3-dir4)*a + (dir1-dir2)*b = y */
                        BigInteger sum12 = eea[0].multiply(x).divide(gcd);
                        BigInteger sum34 = eea[1].multiply(x).divide(gcd);
                        BigInteger dif12 = eea[1].multiply(y).divide(gcd);
                        BigInteger dif34 = eea[0].multiply(y).divide(gcd);

                        sum12 = sum12.add(sum34.divide(ar).multiply(br));
                        sum34 = sum34.subtract(sum34.divide(ar).multiply(ar));
                        dif12 = dif12.subtract(dif12.divide(ar).multiply(ar));
                        dif34 = dif34.add(dif12.divide(ar).multiply(br));

                        loop: for (int i : new int[]{0, 1}) {
                            for (int j : new int[]{0, 1}) {
                                BigInteger t1 = sum34;
                                BigInteger t2 = sum12;
                                BigInteger t3 = dif12;
                                BigInteger t4 = dif34;
                                if (i == 1) {
                                    t1 = t1.subtract(ar);
                                    t2 = t2.add(br);
                                }
                                if (j == 1) {
                                    t3 = t3.subtract(ar);
                                    t4 = t4.add(br);
                                }
                                if (t2.add(t3).mod(new BigInteger("2")).compareTo(new BigInteger("0")) == 0 &&
                                        t1.add(t4).mod(new BigInteger("2")).compareTo(new BigInteger("0")) == 0 ) {
                                    feasible = true;
                                    sum12 = t2;
                                    sum34 = t1;
                                    dif34 = t4;
                                    dif12 = t3;
                                    break loop;
                                }
                            }
                        }
                        dir1 = sum12.add(dif12).divide(new BigInteger("2"));
                        dir2 = sum12.subtract(dir1);
                        dir3 = sum34.add(dif34).divide(new BigInteger("2"));
                        dir4 = sum34.subtract(dir3);
                    }
                } else if (a.compareTo(new BigInteger("0")) == 0 ) {
                    feasible = x.mod(b).compareTo(new BigInteger("0")) == 0
                            && y.mod(b).compareTo(new BigInteger("0")) == 0;
                    dir1 = y.divide(b);
                    dir3 = x.divide(b);
                } else if (b.compareTo(new BigInteger("0")) == 0 ) {
                    feasible = x.mod(a).compareTo(new BigInteger("0")) == 0
                            && y.mod(a).compareTo(new BigInteger("0")) == 0;
                    dir1 = x.divide(a);
                    dir3 = y.divide(a);
                }
                if (feasible) {
                    System.out.println(a + " " + b + " " + dir1);
                    System.out.println(a + " " + b.negate() + " " + dir2);
                    System.out.println(b + " " + a + " " + dir3);
                    System.out.println(b + " " + a.negate() + " " + dir4);
                } else {
                    System.out.println("impossible");
                }
            } else {
                System.out.println("impossible");
            }
            System.out.println();
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