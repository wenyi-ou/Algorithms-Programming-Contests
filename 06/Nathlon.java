import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Nathlon {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            String[] paramLine = in.readLine().split(" ");
            int n = Integer.parseInt(paramLine[0]);//number of games organized
            long k = Long.parseLong(paramLine[1]);//number of friends invited
            HashMap<Integer, Integer> equations = new HashMap<>();
            boolean unfeasible = false;
            for (int i = 0; i < n; i++) {
                String[] line = in.readLine().split(" ");
                int size = Integer.parseInt(line[0]);
                int rest = Integer.parseInt(line[1]);
                if (equations.containsKey(size)) {
                    if (equations.get(size) != rest) {
                        unfeasible = true;
                        break;
                    }
                } else {
                    equations.put(size, rest);
                }
            }
            if (unfeasible) {
                System.out.println("Case #" + caseNr + ": impossible");
            } else if (n == 0) {
                System.out.println("Case #" + caseNr + ": 0");
            } else {
                n = equations.size();
                int[] sizes = new int[n];
                int[] rests = new int[n];
                int j = 0;
                for (Map.Entry e : equations.entrySet()) {
                    sizes[j] = (int) e.getKey();
                    rests[j] = (int) e.getValue();
                    j++;
                }
                long m = 1;
                for (int i = 0; i < n; i++) {
                    m *= sizes[i];
                }
                long[] ms = new long[n];
                for (int i = 0; i < n; i++) {
                    ms[i] = m / sizes[i];
                }

                long[] ts = new long[n];
                for (int i = 0; i < n; i++) {
                    ts[i] = modularInverse(ms[i], sizes[i]);
                }

                long acc = 0;
                for (int i = 0; i < n; i++) {
                    acc += rests[i] * ts[i] * ms[i];
                }
                acc = acc % m;
                if (acc > k) {
                    System.out.println("Case #" + caseNr + ": impossible");
                } else {
                    while (acc <= k) {
                        acc += m;
                    }
                    acc -= m;
                    if (acc > k || acc < 0)
                        System.out.println("Case #" + caseNr + ": impossible");
                    else
                        System.out.println("Case #" + caseNr + ": " + acc);
                }
            }
            in.readLine();
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