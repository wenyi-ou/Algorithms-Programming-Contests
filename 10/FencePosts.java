import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

//Reference: https://www.geeksforgeeks.org/convex-hull-set-1-jarviss-algorithm-or-wrapping/
public class FencePosts {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            int n = Integer.parseInt(in.readLine());//number of fence posts
            Post[] posts = new Post[n];
            int minX = Integer.MAX_VALUE;
            int minIndex = 0;
            for (int i = 0; i < n; i++) {
                String[] params = in.readLine().split(" ");
                int x = Integer.parseInt(params[0]);
                int y = Integer.parseInt(params[1]);
                posts[i] = new Post(x, y);
                if (x < minX || (x == minX && y < posts[minIndex].y)) {
                    minX = x;
                    minIndex = i;
                }
            }

            ArrayList<Integer> hull = new ArrayList<>();
            int p = minIndex;
            int q; //(p, q, i) is counterclockwise for any other point i
            do {
                hull.add(p);
                q = (p + 1) % n;
                for (int i = 0; i < n; i++) {
                    int ccw = (posts[i].y - posts[p].y) * (posts[q].x - posts[i].x)
                            - (posts[i].x - posts[p].x) * (posts[q].y - posts[i].y);
                    if (ccw < 0) //counterclockwise
                        q = i;
                    else if (ccw == 0) { //co-linear
                        double dist1 = Math.sqrt(Math.pow(posts[p].x - posts[i].x, 2)
                                               + Math.pow(posts[p].y - posts[i].y, 2));
                        double dist2 = Math.sqrt(Math.pow(posts[p].x - posts[q].x, 2)
                                               + Math.pow(posts[p].y - posts[q].y, 2));
                        if (dist1 > dist2)
                            q = i;
                    }
                }
                p = q;
            } while (p != minIndex);

            System.out.print("Case #" + caseNr + ":");
            Collections.sort(hull);
            for (int i : hull) {
                int ip = i+1;
                System.out.print(" " + ip);
            }
            System.out.println();
            in.readLine();
        }
        in.close();
        reader.close();
    }
}

class Post implements Comparable<Post>{
    int x, y;

    public Post(int a, int b) {
        x = a;
        y = b;
    }

    @Override
    public int compareTo(Post p) {
        if (this.x == p.x) {
            return this.y - p.y;
        }
        return this.x - p.x;
    }
}
