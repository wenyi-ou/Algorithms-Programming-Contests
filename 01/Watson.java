import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Watson {
    public static void main(String[] args) throws IOException {
        InputStreamReader r = new InputStreamReader(System .in);
        BufferedReader in = new BufferedReader (r);
        int n = Integer.parseInt(in.readLine());
        for (int i = 1; i <= n; i++) {
            String str = in.readLine();
            int sol = compute(str);
            System.out.println("Case #" + i + ": " + sol);
        }
        in.close();
        r.close();
    }

    private static int compute(String str) {
        int i = str.length() - 1;
        while (i >= 0 && Character.isDigit(str.charAt(i))) {
            i--;
        }
        int value = Integer.parseInt(str.substring(i+1));
        if (i == -1) {
            return value;
        }else if (str.charAt(i-3) == 'p') {
            //plus
            return compute(str.substring(0, i-3)) + value;
        }else if (str.charAt(i-4) == 'm') {
            //minus
            return compute(str.substring(0, i-4)) - value;
        }else if (str.charAt(i-2) == 'm'){
            //times
            return compute(str.substring(0, i-4)) * value;
        }else {
            //tothepowerof
            return (int) Math.pow(compute(str.substring(0, i-11)), value);
        }
    }
}