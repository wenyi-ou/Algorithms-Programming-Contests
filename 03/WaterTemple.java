import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.*;

public class WaterTemple {
    static int n;//amount of rooms in the water temple
    static ArrayList<PriorityQueue<Hallway>> hallways;//sorted for each room, larger level first
    static int[] controls;
    static int[] roomLevels;//store highest possible level of ways to the room. initial -1 (not possible)
    static boolean[] explored;

    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int i = 1; i <= caseNum; i++) {
            String[] nums = in.readLine().split(" ");
            n = Integer.parseInt(nums[0]);//amount of rooms in the water temple
            int m = Integer.parseInt(nums[1]);//amount of hallways connecting the rooms
            int k = Integer.parseInt(nums[2]);//amount of rooms that are control rooms
            int l = Integer.parseInt(nums[3]);//the initial water level
            hallways = new ArrayList<>();
            controls = new int[n];
            roomLevels = new int[n];
            explored = new boolean[n];
            for (int j = 0; j < n; j++) {
                hallways.add(new PriorityQueue<>((h1, h2) -> h2.level - h1.level));
                /*for simplicity of implementation, regard room that is not control room as
                 control room that can only lower the water to initial water level */
                controls[j] = l;
                roomLevels[j] = -1;
                explored[j] = false;
            }
            for (int j = 0; j < m; j++) {
                String[] line = in.readLine().split(" ");
                int a = Integer.parseInt(line[0]) - 1;
                int b = Integer.parseInt(line[1]) - 1;
                int li = Integer.parseInt(line[2]);//least water level for hallway between a and b
                hallways.get(a).add(new Hallway(b, li));
                hallways.get(b).add(new Hallway(a, li));
            }
            for (int j = 0; j < k; j++) {
                String[] line = in.readLine().split(" ");
                int a = Integer.parseInt(line[0]) - 1;//control room
                int d = Integer.parseInt(line[1]);//highest level drained
                controls[a] = d;
            }

            int actualLevel = l;//current water level
            PriorityQueue<Hallway> queue = new PriorityQueue<>((h1, h2) -> h2.level - h1.level);
            queue.add(new Hallway(0, actualLevel));
            int lowestPossible = l;//lowest possible water level determined by control rooms
            while (!queue.isEmpty()) {
                Hallway current = queue.poll();
                if (!explored[current.dest]) {
                    if (current.level >= lowestPossible) {
                        explored[current.dest] = true;
                        if (current.level < actualLevel) {
                            actualLevel = current.level;//a control room is used
                        }
                        if (controls[current.dest] < lowestPossible) {
                            lowestPossible = controls[current.dest];
                        }
                        for (Hallway h : hallways.get(current.dest)) {
                            if (!explored[h.dest]) {
                                if (h.level > roomLevels[h.dest]) {
                                    roomLevels[h.dest] = h.level;
                                    queue.offer(h);
                                }
                            }
                        }
                    }
                }
            }


            boolean finished = true;
            for (int j = 0; j < n; j++) {
                if (!explored[j]) {
                    finished = false;
                    break;
                }
            }
            if (finished) {
                System.out.println("Case #" + i + ": " + actualLevel);
            } else {
                System.out.println("Case #" + i + ": impossible");
            }
            in.readLine();
        }
        in.close();
        reader.close();
    }
}

class Hallway {
    int dest;
    int level;

    public Hallway(int d, int l) {
        dest = d;
        level = l;
    }
}