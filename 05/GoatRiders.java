import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class GoatRiders {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int i = 1; i <= caseNum; i++) {
            String[] paramLine = in.readLine().split(" ");
            int n = Integer.parseInt(paramLine[0]);//size of the goat riders territory
            int k = Integer.parseInt(paramLine[1]);//number of goat riders
            int d = Integer.parseInt(paramLine[2]);//number of nights the goat riders have to survive
            int[][] heights = new int[n][n];
            ArrayList<ArrayList<Integer>> pos = new ArrayList<>();
            int[] level = new int[d];
            for (int j = 0; j < n; j++) {
                String[] line = in.readLine().split(" ");
                for (int j2 = 0; j2 < n; j2++) {
                    heights[j][j2] = Integer.parseInt(line[j2]);
                }
            }
            for (int j = 0; j < k; j++) {
                String[] line = in.readLine().split(" ");
                int r = Integer.parseInt(line[0]) - 1;
                int c = Integer.parseInt(line[1]) - 1;
                //on day 0 there is a goat rider in row r and column c
                pos.add(new ArrayList<>());
                pos.get(j).add(r);
                pos.get(j).add(c);
            }
            for (int j = 0; j < k; j++) {
                level[j] = Integer.parseInt(in.readLine()) - 1;
                //during the night from day j-1 to day j the snow will rise or fall to level
            }
            HashMap<Connection, Integer> flow = new HashMap<>();
            State source = new State(n, n, true, -2);
            State sink = new State(n + 1, n + 1, false, d);
            int[][] direction = {{0, -1}, {-1, 0}, {1, 0}, {0, 1}, {0, 0}};
            while(true) {
                Stack<State> dfs = new Stack<>();
                HashMap<State, Boolean> visited = new HashMap<>();
                HashMap<State, Parent> parent = new HashMap<>();
                dfs.push(source);
                boolean success = false;
                while (!dfs.empty()) {
                    State current = dfs.pop();
                    if (visited.get(current))
                        continue;
                    visited.replace(current, true);
                    if (current == sink) {
                        success = true;
                        break;
                    } else if (current.day == -2) {
                        for (int j = 0; j < k; j++) {
                            State next = new State(pos.get(i).get(0), pos.get(i).get(1), false, current.day + 1);
                            if (!visited.get(current) && flow.get(new Connection(current, next)) == 0) {
                                dfs.push(next);
                                parent.put(next, new Parent(current, true));
                            }
                        }
                    } else if (current.day == -1 && !current.out) {
                        State next = new State(pos.get(i).get(0), pos.get(i).get(1), true, current.day);
                        if (!visited.get(next) && flow.get(new Connection(current, next)) == 0) {
                            dfs.push(next);
                            parent.put(next, new Parent(current, true));
                        }
                        if (flow.get(new Connection(source, sink)) == 1) {
                            dfs.push(source);
                            parent.put(source, new Parent(current, false));
                        }
                    } else if (current.day == d - 1 && current.out) {
                        if (flow.get(new Connection(current, sink)) == 0) {
                            dfs.push(sink);
                            parent.put(sink, new Parent(current, true));
                        }
                        State back = new State(pos.get(i).get(0), pos.get(i).get(1), false, current.day);
                        if (!visited.get(back) && flow.get(new Connection(back, current)) == 1) {
                            dfs.push(back);
                            parent.put(back, new Parent(current, false));
                        }
                    } else if (!current.out) {
                        State next = new State(pos.get(i).get(0), pos.get(i).get(1), true, current.day);
                        if (!visited.get(next) && flow.get(new Connection(current, next)) == 0) {
                            dfs.push(next);
                            parent.put(next, new Parent(current, true));
                        }
                        for (int j = 0; j < 5; j++) {
                            int u = current.x + direction[j][0];
                            int v = current.y + direction[j][1];
                            if (u>=0 && v>=0 && u<n && v<n && (current.day <= 0 || heights[u][v] > level[current.day - 1])) {
                                State back = new State(u, v, true, current.day - 1);
                                if (!visited.get(back) && flow.get(new Connection(back, current)) == 1) {
                                    dfs.push(back);
                                    parent.put(back, new Parent(current, false));
                                }
                            }
                        }
                    } else {
                        for (int j = 0; j < 5; j++) {
                            int u = current.x + direction[j][0];
                            int v = current.y + direction[j][1];
                            if (u>=0 && v>=0 && u<n && v<n && (current.day <= -2 || heights[u][v] > level[current.day + 1])) {
                                State next = new State(u, v, false, current.day + 1);
                                if (!visited.get(next) && flow.get(new Connection(current, next)) == 0) {
                                    dfs.push(next);
                                    parent.put(next, new Parent(current, true));
                                }
                            }
                        }
                        State back = new State(current.x, current.y, false, current.day);
                        if (!visited.get(back) && flow.get(new Connection(back, current)) == 1) {
                            dfs.push(back);
                            parent.put(back, new Parent(current, false));
                        }
                    }
                }
                if (!success)
                    break;
                State to = sink;
                while (true) {
                    Parent info = parent.get(to);
                    State from = info.s;
                    boolean forEdge = info.v;
                    if (forEdge) {
                        flow.replace(new Connection(from, to), flow.get(new Connection(from, to)) + 1);
                    } else {
                        flow.replace(new Connection(from, to), flow.get(new Connection(from, to)) - 1);
                    }
                    to = from;
                    if (from==source)
                        break;
                }
            }
            int count = 0;
            for (int j = 0; j < n; j++) {
                for (int j2 = 0; j2 < n; j2++) {
                    count += flow.get(new Connection(new State(j, j2, true, d - 1), sink));
                }
            }
            System.out.println("Case #" + i + ": " + count);
            in.readLine();
        }
        in.close();
        reader.close();
    }
}

class State {
    int x;
    int y;
    boolean out;
    int day;

    public State (int x, int y, boolean out, int day) {
        this.x = x;
        this.y = y;
        this.out = out;
        this.day = day;
    }
}

class Parent {
    State s;
    boolean v;

    public Parent (State s, boolean v) {
        this.s = s;
        this.v = v;
    }
}

class Connection {
    State s1;
    State s2;

    public Connection (State x, State y) {
        this.s1 = x;
        this.s2 = y;
    }
}