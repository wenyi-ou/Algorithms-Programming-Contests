import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BeerPressure {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            String[] params = in.readLine().split(" ");
            int n = Integer.parseInt(params[0]);//number of pubs in this trial
            int k = Integer.parseInt(params[1]);//total number of students, both dominant and non-dominant
            Distribution distribution = new Distribution(n);
            params = in.readLine().split(" ");
            int rem = k;
            for (int i = 0; i < n; i++) {
                int curr = Integer.parseInt(params[i]);
                distribution.votes[i] = curr;
                rem -= curr;
            }

            HashMap<Distribution, Double> distributions = new HashMap<>();
            distributions.put(distribution, 1.0);
            for (int count = rem; count > 0; count--) {
                HashMap<Distribution, Double> distributionsNew = new HashMap<>();
                for (Distribution d : distributions.keySet()) {
                    double currProb = distributions.get(d);
                    for (int i = 0; i < n; i++) {
                        double newProb = currProb * d.votes[i] / (k - count);
                        Distribution dNew = new Distribution(Arrays.copyOf(d.votes, n));
                        dNew.votes[i] ++;
                        if (distributionsNew.containsKey(dNew)) {
                            distributionsNew.replace(dNew, distributionsNew.get(dNew) + newProb);
                        } else {
                            distributionsNew.put(dNew, newProb);
                        }
                    }
                }
                distributions = distributionsNew;
            }

            double[] possibilities = new double[n];
            for (Distribution d : distributions.keySet()) {
                int[] votes = d.votes;
                double max = 0;
                ArrayList<Integer> maxIndex = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    if (votes[i] > max) {
                        max = votes[i];
                        maxIndex = new ArrayList<>();
                        maxIndex.add(i);
                    } else if (votes[i] == max) {
                        maxIndex.add(i);
                    }
                }
                for (int index : maxIndex)
                    possibilities[index] += (double) 1 / maxIndex.size() * distributions.get(d);
            }

            System.out.println("Case #" + caseNr + ":");
            for (int i = 1; i <= n; i++) {
                System.out.format("pub %d: %.2f %%\n", i , possibilities[i-1] * 100);
            }
            in.readLine();
        }
        in.close();
        reader.close();
    }
}

class Distribution {
    int[] votes;

    Distribution (int n) {
        votes = new int[n];
    }

    Distribution (int[] votes) {
        this.votes = votes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Distribution)) return false;
        Distribution that = (Distribution) o;
        return Arrays.equals(votes, that.votes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(votes);
    }
}
