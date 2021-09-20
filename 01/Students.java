import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Students {
    public static void main(String[] args) throws IOException {
        InputStreamReader r = new InputStreamReader(System .in);
        BufferedReader in = new BufferedReader (r);
        int n = Integer.parseInt(in.readLine());
        for (int i = 1; i <= n; i++) {
            int num = Integer.parseInt(in.readLine());
            System.out.println("Case #" + i + ": ");
            for (int j = 1; j <= num; j++) {
                String str = in.readLine();
                str = str.replace("entin", "ierende");
                str = str.replace("enten", "ierende");
                str = str.replace("ent", "ierender");
                System.out.println(str);
            }
        }
        in.close();
        r.close();
    }
}