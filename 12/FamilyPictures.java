import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FamilyPictures {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            int n = Integer.parseInt(in.readLine());

            System.out.println("Case #" + caseNr + ": ");
            in.readLine();
        }
        in.close();
        reader.close();
    }
}