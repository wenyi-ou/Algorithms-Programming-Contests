import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class SoupDelivery_Hash {
    static int n, m;
    static long totalSum;
    static HashMap<Integer, Integer> fixedCost;
    static HashMap<Integer, HashMap<Integer, Integer>> deliveryCost;
    static HashMap<Integer, HashSet<Integer>> serves;
    static HashSet<Integer> used;

    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            String[] params = in.readLine().split(" ");
            n = Integer.parseInt(params[0]);//number of available production locations
            m = Integer.parseInt(params[1]);//M is the number of customers
            fixedCost = new HashMap<>();
            deliveryCost = new HashMap<>();
            serves = new HashMap<>();
            used = new HashSet<>();
            totalSum = 0;
            String[] str = in.readLine().split(" ");
            for (int i = 1; i <= n; i++) {
                int c = Integer.parseInt(str[i-1]);
                fixedCost.put(i, c);
                deliveryCost.put(i, new HashMap<>());
                serves.put(i, new HashSet<>());
            }
            used.add(1);
            totalSum += fixedCost.get(1);
            for (int i = 1; i <= n; i++) {
                str = in.readLine().split(" ");
                for (int j = 1; j <= m; j++) {
                    int d = Integer.parseInt(str[j-1]);
                    deliveryCost.get(i).put(j, d);
                    if (i == 1) {
                        serves.get(1).add(j);
                        totalSum += d;
                    }
                }
            }
            optimize();
            System.out.println("Case #" + caseNr + ": " + totalSum);
            for (int i = 1; i <= n; i++) {
                if (used.contains(i)) {
                    System.out.print(i);
                    HashSet<Integer> set = new HashSet<>(serves.get(i));
                    for (int nr : set) {
                        System.out.print(" " + nr);
                    }
                    System.out.println();
                }
            }
            in.readLine();
        }
        in.close();
        reader.close();
    }

    static void optimize() {
        long improved = 1;
        int curr = 1;
        long diff = 0;
        while (improved > 0) {
            improved = 0;
            for (int i = curr; i <= n; i++) {
                if (used.contains(i)) {
                    diff = removeStation(i);
                    if (diff < 0) {
                        improved = -diff;
                        curr = i;
                    } else {
                        diff = replaceStation(i);
                        if (diff < 0) {
                            improved = -diff;
                            curr = i;
                        }
                    }
                } else if (!used.contains(i)) {
                    diff = addStation(i);
                    if (diff < 0) {
                        improved = -diff;
                        curr = i;
                    }
                }
            }
            for (int i = 1; i <= curr - 1; i++) {
                if (used.contains(i)) {
                    diff = removeStation(i);
                    if (diff < 0) {
                        improved = -diff;
                        curr = i;
                    } else {
                        diff = replaceStation(i);
                        if (diff < 0) {
                            improved = -diff;
                            curr = i;
                        }
                    }
                } else if (!used.contains(i)) {
                    diff = addStation(i);
                    if (diff < 0) {
                        improved = -diff;
                        curr = i;
                    }
                }
            }
            curr++;
            if (curr > n)
                curr = 1;
        }
    }

    static long addStation(int added) {
        long diff = fixedCost.get(added);
        HashMap<Integer, HashSet<Integer>> toBeMoved = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            if (used.contains(i)) {
                HashSet<Integer> oldCustomers = new HashSet<>();
                HashSet<Integer> customers = new HashSet<>(serves.get(i));
                for (int c : customers) {
                    int oldCost = deliveryCost.get(i).get(c);
                    int newCost = deliveryCost.get(added).get(c);
                    if (oldCost > newCost) {
                        diff += newCost - oldCost;
                        oldCustomers.add(c);
                    }
                }
                if (oldCustomers.size() == customers.size()) {
                    diff -= fixedCost.get(i);
                    oldCustomers.add(-1);
                }
                toBeMoved.put(i, oldCustomers);
            }
        }
        if (diff < 0) {
            totalSum += diff;
            used.add(added);
            for (Map.Entry<Integer, HashSet<Integer>> pair : toBeMoved.entrySet()) {
                int f = pair.getKey();
                HashSet<Integer> customers = new HashSet<>(pair.getValue());
                if (customers.contains(-1)) {
                    customers.remove(-1);
                    used.remove(f);
                }
                serves.get(f).removeAll(customers);
                serves.get(added).addAll(customers);
            }
            return diff;
        }
        return 0;
    }

    static long removeStation(int removed) {
        long diff = -fixedCost.get(removed);
        HashSet<Integer> customers = new HashSet<>(serves.get(removed));
        HashMap<Integer, HashSet<Integer>> modifications = new HashMap<>();
        for (int f : used) {
            if (f != removed) {
                modifications.put(f, new HashSet<>());
            }
        }
        for (int c : customers) {
            long min = Long.MAX_VALUE;
            int servedBy = -1;
            for (int f : used) {
                if (f != removed) {
                    long newCost = deliveryCost.get(f).get(c);
                    if (newCost < min) {
                        min = newCost;
                        servedBy = f;
                    }
                }
            }
            if (servedBy != -1) {
                modifications.get(servedBy).add(c);
                diff += min - deliveryCost.get(removed).get(c);
            } else
                return 0;
        }
        if (diff < 0) {
            totalSum += diff;
            used.remove(removed);
            serves.get(removed).clear();
            for (Map.Entry<Integer, HashSet<Integer>> integerHashSetEntry : modifications.entrySet()) {
                int f = integerHashSetEntry.getKey();
                serves.get(f).addAll(integerHashSetEntry.getValue());
            }
            return diff;
        }
        return 0;
    }

    static long replaceStation(int replaced) {
        long diff = -fixedCost.get(replaced);
        HashSet<Integer> customers = new HashSet<>(serves.get(replaced));
        for (int c : customers) {
            diff -= deliveryCost.get(replaced).get(c);
        }
        long minReplacement = Long.MAX_VALUE;
        int replacement = -1;
        for (int i = 1; i <= n; i++) {
            if (!used.contains(i)) {
                long tmp = fixedCost.get(i);
                for (int c : customers) {
                    tmp += deliveryCost.get(i).get(c);
                }
                if (tmp < minReplacement) {
                    replacement = i;
                    minReplacement = tmp;
                }
            }
        }
        if (replacement != -1) {
            diff += minReplacement;
            if (diff < 0) {
                totalSum += diff;
                used.remove(replaced);
                used.add(replacement);
                serves.get(replacement).addAll(customers);
                serves.get(replaced).clear();
            }
            return diff;
        }
        return 0;
    }
}