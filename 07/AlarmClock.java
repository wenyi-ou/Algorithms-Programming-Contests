import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class AlarmClock {
    static void initialize() {
        hexDigits = new int[]{
                0b1011111,
                0b0000011,
                0b1110110,
                0b1110011,
                0b0101011,
                0b1111001,
                0b1111101,
                0b1000011,
                0b1111111,
                0b1111011
        };

        sources = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            sources.add(new ArrayList<>());
        }

        sources.get(0).add(0);
        sources.get(0).add(8);

        sources.get(1).add(0);
        sources.get(1).add(1);
        sources.get(1).add(3);
        sources.get(1).add(4);
        sources.get(1).add(7);
        sources.get(1).add(8);
        sources.get(1).add(9);

        sources.get(2).add(2);
        sources.get(2).add(8);

        sources.get(3).add(3);
        sources.get(3).add(8);
        sources.get(3).add(9);

        sources.get(4).add(4);
        sources.get(4).add(8);
        sources.get(4).add(9);

        sources.get(5).add(5);
        sources.get(5).add(6);
        sources.get(5).add(8);
        sources.get(5).add(9);

        sources.get(6).add(6);
        sources.get(6).add(8);

        sources.get(7).add(0);
        sources.get(7).add(3);
        sources.get(7).add(7);
        sources.get(7).add(8);
        sources.get(7).add(9);

        sources.get(8).add(8);

        sources.get(9).add(8);
        sources.get(9).add(9);
    }

    static int n;
    static int[] hexDigits;
    static ArrayList<ArrayList<Integer>> sources;
    static ArrayList<Time> shown;

    public static void main(String[] args) throws IOException {
        initialize();
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            shown = new ArrayList<>();
            n = Integer.parseInt(in.readLine());
            for (int i = 0; i < n; i++) {
                String line = in.readLine();
                Time s = new Time(Character.getNumericValue(line.charAt(0)),
                        Character.getNumericValue(line.charAt(1)),
                        Character.getNumericValue(line.charAt(3)),
                        Character.getNumericValue(line.charAt(4)));
                shown.add(s);
            }
            ArrayList<Time> sol = solve();
            System.out.println("Case #" + caseNr + ":");
            if (sol.isEmpty()) {
                System.out.println("none");
            } else {
                sol.sort((t1, t2) -> {
                    if (t1.digit1 != t2.digit1)
                        return t1.digit1 - t2.digit1;
                    if (t1.digit2 != t2.digit2)
                        return t1.digit2 - t2.digit2;
                    if (t1.digit3 != t2.digit3)
                        return t1.digit3 - t2.digit3;
                    return t1.digit4 - t2.digit4;
                });
                for (Time t : sol) {
                    System.out.println(t.toString());
                }
            }
            in.readLine();
        }
        in.close();
        reader.close();
    }

    static ArrayList<Time> solve() {
        ArrayList<Time> startPossibilities = new ArrayList<>();
        Time start = shown.get(0);
        for(int i1 : sources.get(start.digit1)) {
            if (i1 >= 3) continue;
            for(int i2 : sources.get(start.digit2)) {
                if (i1 == 2 && i2 >= 4) continue;
                for(int i3 : sources.get(start.digit3)) {
                    if (i3 >= 6) continue;
                    for(int i4 : sources.get(start.digit4)) {
                        startPossibilities.add(new Time(i1, i2, i3, i4));
                    }
                }
            }
        }
        ArrayList<Time> valid = new ArrayList<>();
        for (Time possibleStart : startPossibilities) {
            boolean isValid = false;
            for (int d1 = 0; d1 <= 0b1111111; d1++) {
                if (isValid) break;
                if ((d1 & hexDigits[possibleStart.digit1]) != hexDigits[start.digit1])
                    continue;
                for (int d2 = 0; d2 <= 0b1111111; d2++) {
                    if (isValid) break;
                    if ((d2 & hexDigits[possibleStart.digit2]) != hexDigits[start.digit2])
                        continue;
                    for (int d3 = 0; d3 <= 0b1111111; d3++) {
                        if (isValid) break;
                        if ((d3 & hexDigits[possibleStart.digit3]) != hexDigits[start.digit3])
                            continue;
                        for (int d4 = 0; d4 <= 0b1111111; d4++) {
                            if (isValid) break;
                            if ((d4 & hexDigits[possibleStart.digit4]) != hexDigits[start.digit4])
                                continue;
                            Time digits = new Time(d1, d2, d3, d4);
                            int satisfied = 0;
                            Time current = possibleStart;
                            for (int j = 0; j < n; j++) {
                                if (match(digits, current, shown.get(j))) {
                                    satisfied++;
                                } else {
                                    break;
                                }
                                current = current.nextTime();
                            }
                            if (satisfied == n) {
                                isValid = true;
                                valid.add(possibleStart);
                            }
                        }
                    }
                }
            }
        }
        return valid;
    }

    private static boolean match(Time digits, Time current, Time shown) {
        if ((digits.digit1 & hexDigits[current.digit1]) != hexDigits[shown.digit1])
            return false;
        if ((digits.digit2 & hexDigits[current.digit2]) != hexDigits[shown.digit2])
            return false;
        if ((digits.digit3 & hexDigits[current.digit3]) != hexDigits[shown.digit3])
            return false;
        return (digits.digit4 & hexDigits[current.digit4]) == hexDigits[shown.digit4];
    }
}

class Time {
    int digit1;
    int digit2;
    int digit3;
    int digit4;

    Time (int d1, int d2, int d3, int d4) {
        digit1 = d1;
        digit2 = d2;
        digit3 = d3;
        digit4 = d4;
    }

    Time nextTime () {
        if (digit3 == 5 && digit4 == 9) {
            if (digit1 == 2 && digit2 == 3) {
                return new Time(0, 0, 0, 0);
            }
            if (digit2 == 9) {
                return new Time(digit1 + 1, 0, 0, 0);
            }
            return new Time(digit1, digit2 + 1, 0, 0);
        }
        if (digit4 == 9) {
            return new Time(digit1, digit2, digit3 + 1, 0);
        }
        return new Time(digit1, digit2, digit3, digit4 + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Time)) return false;
        Time time = (Time) o;
        return digit1 == time.digit1 && digit2 == time.digit2 && digit3 == time.digit3 && digit4 == time.digit4;
    }

    @Override
    public int hashCode() {
        return Objects.hash(digit1, digit2, digit3, digit4);
    }

    @Override
    public String toString() {
        return digit1 + "" + digit2 + ":" + digit3 + "" + digit4;
    }
}