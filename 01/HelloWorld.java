import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class HelloWorld {
    public static void main(String[] args) throws IOException {
        InputStreamReader r = new InputStreamReader(System .in);
        BufferedReader in = new BufferedReader (r);
        int n = Integer.parseInt(in.readLine());
        for (int i = 1; i <= n; i++) {
            String name = in.readLine();
            System.out.println("Case #" + i + ": " + "Hello "+ name + "!");
        }
        in.close();
        r.close();
    }
}