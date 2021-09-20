import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Candies {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            int n = Integer.parseInt(in.readLine());//number of Lea’s friends
            String[] paramLine = in.readLine().split(" ");
            int[] numCandies = new int[n];
            for (int i = 0; i < n; i++) {
                numCandies[i] = Integer.parseInt(paramLine[i]);
            }

            //calculate all possible combinations of friends
            int n2 = (int) Math.pow(2, n);
            long[] sums = new long[n2];
            for (int i = 0; i < n2; i++) {
                long sum = 1;
                String combString = Integer.toBinaryString(i);
                for (int j = 0; j < combString.length(); j++) {
                    if (combString.charAt(j) == '1') {
                        sum += numCandies[n-combString.length()+j];
                    }
                }
                sums[i] = sum;
            }

            //calculate lcm of the array sums
            long lcm = 1;
            int divisor = 2;
            while (true) {
                int counter = 0;
                boolean divisible = false;
                for (int i = 0; i < n2; i++) {
                    if (sums[i] == 1) {
                        counter++;
                    }
                    if (sums[i] % divisor == 0) {
                        divisible = true;
                        sums[i] /= divisor;
                    }
                }
                if (divisible) {
                    lcm *= divisor;
                } else {
                    divisor++;
                }
                if (counter == n2) {
                    break;
                }
            }
            System.out.println("Case #" + caseNr + ": " + lcm);
            in.readLine();
        }
        in.close();
        reader.close();
    }
}