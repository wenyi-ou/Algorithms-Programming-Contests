import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class CurrencyExchange {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int i = 1; i <= caseNum; i++) {
            String[] paramLine = in.readLine().split(" ");
            int n = Integer.parseInt(paramLine[0]);//number of currencies
            int m = Integer.parseInt(paramLine[1]);//number of possible exchanges between currencies
            ArrayList<ArrayList<Rate>> rates = new ArrayList<>();
            double[] ds = new double[n];
            for (int j = 0; j < n; j++) {
                rates.add(new ArrayList<>());
                ds[j] = Integer.MAX_VALUE;
            }
            for (int j = 0; j < m; j++) {
                String[] line = in.readLine().split(" ");
                int f = Integer.parseInt(line[0]) - 1;
                int t = Integer.parseInt(line[1]) - 1;
                double r = Double.parseDouble(line[2]);
                rates.get(f).add(new Rate(t, Math.log(r)));
            }

            //Bellman-Ford
            ds[0] = 0;
            Queue<Integer> queue = new LinkedList<>();
            Queue<Integer> queue2 = new LinkedList<>();
            queue.add(0);
            for (int j = 0; j < n; j++) {
                while (!queue.isEmpty()) {
                    int v = queue.poll();
                    for (Rate wr : rates.get(v)) {
                        if (ds[v] + wr.rate < ds[wr.to]) {
                            ds[wr.to] = ds[v] + wr.rate;
                            if (!queue2.contains(wr.to)) {
                                queue2.add(wr.to);
                            }
                        }
                    }
                }
                Queue<Integer> temp = new LinkedList<>(queue);
                queue = new LinkedList<>(queue2);
                queue2 = new LinkedList<>(temp);
            }
            if (!queue.isEmpty()) {
                System.out.println("Case #" + i + ": Jackpot");
            } else if (ds[n-1] == Integer.MAX_VALUE) {
                System.out.println("Case #" + i + ": impossible");
            } else {
                System.out.println("Case #" + i + ": " +  Math.exp(ds[n-1]));
            }
            in.readLine();
        }
        in.close();
        reader.close();
    }
}

class Rate {
    int to;
    double rate; //log value

    public Rate(int t, double r) {
        to = t;
        rate = r;
    }
}