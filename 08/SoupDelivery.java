import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class SoupDelivery {
    static int n, m;
    static int[] fixedCosts;
    static ArrayList<ArrayList<Integer>> deliveryCosts;
    static ArrayList<ArrayList<Integer>> customers;//station -> served customers

    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            String[] params = in.readLine().split(" ");
            n = Integer.parseInt(params[0]);//number of available production locations
            m = Integer.parseInt(params[1]);//M is the number of customers
            fixedCosts = new int[n];
            deliveryCosts = new ArrayList<>();
            String[] str = in.readLine().split(" ");
            for (int i = 0; i < n; i++) {
                fixedCosts[i] = Integer.parseInt(str[i]);
            }
            for (int i = 0; i < n; i++) {
                str = in.readLine().split(" ");
                ArrayList<Integer> current = new ArrayList<>();
                for (int j = 0; j < m; j++)
                    current.add(Integer.parseInt(str[j]));
                deliveryCosts.add(current);
            }
            customers = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                customers.add(new ArrayList<>());
            }

            for (int i = 0; i < m; i++) {
                customers.get(0).add(i);
            }
            optimize();

            System.out.println("Case #" + caseNr + ": " + computeCost());
            for (int i = 0; i < customers.size(); i++) {
                if(customers.get(i).isEmpty()) continue;
                System.out.print(i + 1);
                Collections.sort(customers.get((i)));
                for (int c : customers.get(i)) {
                    int customer = c + 1;
                    System.out.print(" " + customer);
                }
                System.out.println();
            }
            in.readLine();
        }
        in.close();
        reader.close();
    }

    static long computeCost() {
        long total = 0;
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).isEmpty()) continue;
            total += fixedCosts[i];
            for (int c : customers.get(i)) {
                total += deliveryCosts.get(i).get(c);
            }
        }
        return total;
    }

    static void optimize() {
        boolean changed = true;
        while (changed) {
            long diff = 0;
            for (int i = 0; i < n; i++) {
                if (customers.get(i).isEmpty()) {
                    diff = addStation(i);
                    //if (diff < 0)
                        //System.out.println( "added " + i + " diff = " + diff);
                } else {
                    if (customers.get(i).size() != m) {
                        diff = removeStation(i);
                        //if (diff < 0)
                            //System.out.println( "removed " + i + " diff = " + diff);
                        if (diff >= 0) {
                            diff = replaceStation(i);
                            //if (diff < 0)
                                //System.out.println( "replaced " + i + " diff = " + diff);
                        }
                    }
                }
                changed = diff < 0;
            }
        }
    }

    static long addStation(int added) {
        long diff = fixedCosts[added];
        ArrayList<ArrayList<Integer>> toBeMoved = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (int c : customers.get(i)) {
                int newCost = deliveryCosts.get(added).get(c);
                int oldCost = deliveryCosts.get(i).get(c);
                if (newCost < oldCost) {
                    temp.add(c);
                    diff = diff - oldCost + newCost;
                }
            }
            toBeMoved.add(temp);
        }
        if (diff < 0) {
            for (int i = 0; i < toBeMoved.size(); i++) {
                for (int c : toBeMoved.get(i)) {
                    ArrayList<Integer> temp = new ArrayList<>(customers.get(i));
                    customers.get(i).clear();
                    for (int j : temp) {
                        if (j != c) {
                            customers.get(i).add(j);
                        }
                    }
                    customers.get(added).add(c);
                }
            }
            return diff;
        }
        return 0;
    }

    static long removeStation(int removed) {
        long diff = -fixedCosts[removed];
        ArrayList<ArrayList<Integer>> toBeMoved = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            toBeMoved.add(new ArrayList<>());
        }
        for (int r : customers.get(removed)) {
            int oldCost = deliveryCosts.get(removed).get(r);
            int minCost = Integer.MAX_VALUE;
            int minStation = 0;
            for (int i = 0; i < n; i++) {
                if (i == removed || customers.get(i).isEmpty()) continue;
                int newCost = deliveryCosts.get(i).get(r);
                if (newCost < minCost) {
                    minCost = newCost;
                    minStation = i;
                }
            }
            diff = diff - oldCost + deliveryCosts.get(minStation).get(r);
            toBeMoved.get(minStation).add(r);
        }
        if (diff < 0) {
            customers.get(removed).clear();
            for (int i = 0; i < toBeMoved.size(); i++) {
                for (int c : toBeMoved.get(i)) {
                    customers.get(i).add(c);
                }
            }
            return diff;
        }
        return 0;
    }

    static long replaceStation(int replaced) {
        long oldCost = computeCost();
        long minCost = Integer.MAX_VALUE;
        int minStation = 0;
        for (int i = 0; i < customers.size(); i++) {
            //i : replacement
            if (i == replaced || !customers.get(i).isEmpty()) continue;
            long cost = 0;
            for (int j = 0; j < customers.size(); j++) {
                //j : stations in the replaced combination
                if (i != j && (j == replaced || customers.get(j).isEmpty())) continue;
                cost += fixedCosts[j];
            }
            for (int c : customers.get(replaced)) {
                int minSingleCost = Integer.MAX_VALUE;
                for (int j = 0; j < customers.size(); j++) {
                    if (i != j && (j == replaced || customers.get(j).isEmpty())) continue;
                    int currCost = deliveryCosts.get(j).get(c);
                    if (currCost < minSingleCost)
                        minSingleCost = currCost;
                }
                cost += minSingleCost;
            }
            if (cost < minCost) {
                minCost = cost;
                minStation = i;
            }
        }
        if (minCost < oldCost) {
            for (int c : customers.get(replaced)) {
                int minSingleCost = Integer.MAX_VALUE;
                int minSingleStation = 0;
                for (int i = 0; i < customers.size(); i++) {
                    if (i != minStation && (i == replaced || customers.get(i).isEmpty())) continue;
                    int currCost = deliveryCosts.get(i).get(c);
                    if (currCost < minSingleCost) {
                        minSingleCost = currCost;
                        minSingleStation = i;
                    }
                }
                customers.get(minSingleStation).add(c);
            }
            customers.get(replaced).clear();
            return computeCost() - oldCost;
        } else
            return 0;
    }
}