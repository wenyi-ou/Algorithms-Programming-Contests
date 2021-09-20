import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class RoomFinding {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int n = Integer.parseInt(in.readLine());
        for (int i = 1; i <= n; i++) {
            String[] numParts = in.readLine().split(" ");
            int s = Integer.parseInt(numParts[0]);
            int f = Integer.parseInt(numParts[1]);

            int max = 0;
            int min = Integer.MAX_VALUE;
            ArrayList<ArrayList<Integer>> stations = new ArrayList<>();

            for (int j = 0; j < s; j++) {
                String[] stationParts = in.readLine().split(" ");
                int start = Integer.parseInt(stationParts[0]);
                int end = Integer.parseInt(stationParts[1]);
                ArrayList<Integer> range = new ArrayList<>();
                range.add(start);
                range.add(end);
                stations.add(range);
                if (start < min) min = start;
                if (end > max) max = end;
            }

            System.out.println("Case #" + i + ":");
            for (int j = 0; j < f; j++) {
                int lineNumber = Integer.parseInt(in.readLine());
                System.out.println(find(stations, lineNumber, min, max));
            }
            in.readLine();
        }
        in.close();
        reader.close();
    }

    static long findSmaller(ArrayList<ArrayList<Integer>> stations, long m) {
        long res = 0;
        for (ArrayList<Integer> range : stations) {
            if (m > range.get(1)) {
                long count = range.get(1) - range.get(0) + 1;
                res += count;
            } else if (m >= range.get(0)) {
                long count = m - range.get(0);
                res += count;
            }
        }
        return res;
    }

    static long findSmallerEqual(ArrayList<ArrayList<Integer>> stations, long m) {
        long res = 0;
        for (ArrayList<Integer> range : stations) {
            if (m > range.get(1)) {
                long count = range.get(1) - range.get(0) + 1;
                res += count;
            } else if (m >= range.get(0)) {
                long count = m - range.get(0) + 1;
                res += count;
            }
        }
        return res;
    }

    private static long find(ArrayList<ArrayList<Integer>> stations, int lineNumber, long l, long r) {
        if (l <= r) {
            long m = (l + r) / 2;

            long smaller = findSmaller(stations, m);
            long smallerEqual = findSmallerEqual(stations, m);

            if (lineNumber > smaller && lineNumber <= smallerEqual) {
                return m;
            } else if (lineNumber <= smaller) {
                return find(stations, lineNumber, l, m - 1);
            } else if (lineNumber > smallerEqual) {
                return find(stations, lineNumber, m + 1, r);
            }
        }
        return -1;
    }
}