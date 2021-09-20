import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

//Reference: https://www.topcoder.com/thrive/articles/Greedy%20is%20Good - BioScore
public class Dolphins {
    static int n, m;//number of human and mouse DNA sequences
    static ArrayList<String> humans, mice;

    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            String[] paramLine = in.readLine().split(" ");
            n = Integer.parseInt(paramLine[0]);
            m = Integer.parseInt(paramLine[1]);
            humans = new ArrayList<>();
            mice = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                humans.add(in.readLine());
            }
            for (int i = 0; i < m; i++) {
                mice.add(in.readLine());
            }

            int[] values = new int[10];
            for (String human : humans) {
                for (String mouse : mice) {
                    for (int k = 0; k < mouse.length(); k++) {
                        char c1 = human.charAt(k);
                        char c2 = mouse.charAt(k);
                        //AA, CC, GG, TT, AC, AG, AT, CG, CT, GT
                        if (c1 == 'A' && c2 == 'A') values[0]++;
                        if (c1 == 'C' && c2 == 'C') values[1]++;
                        if (c1 == 'G' && c2 == 'G') values[2]++;
                        if (c1 == 'T' && c2 == 'T') values[3]++;
                        if (c1 == 'A' && c2 == 'C') values[4]++;
                        if (c1 == 'C' && c2 == 'A') values[4]++;
                        if (c1 == 'A' && c2 == 'G') values[5]++;
                        if (c1 == 'G' && c2 == 'A') values[5]++;
                        if (c1 == 'A' && c2 == 'T') values[6]++;
                        if (c1 == 'T' && c2 == 'A') values[6]++;
                        if (c1 == 'C' && c2 == 'G') values[7]++;
                        if (c1 == 'G' && c2 == 'C') values[7]++;
                        if (c1 == 'C' && c2 == 'T') values[8]++;
                        if (c1 == 'T' && c2 == 'C') values[8]++;
                        if (c1 == 'G' && c2 == 'T') values[9]++;
                        if (c1 == 'T' && c2 == 'G') values[9]++;
                    }
                }
            }


            ArrayList<Integer> count = new ArrayList<>();
            PriorityQueue<Integer> nonDiagonal = new PriorityQueue<>(Collections.reverseOrder());
            for (int i = 4; i < 10; i++)
                nonDiagonal.add(values[i]);
            for (int i = 0; i < 4; i++)
                count.add(values[i]);
            for (int i = 0; i < 6; i++)
                count.add(nonDiagonal.poll());

            int[] score = new int[10];
            int sol = Integer.MIN_VALUE;
            for (score[0] = 0; score[0] <= 10; score[0]++) {
                for (score[1] = 0; score[1] <= 10; score[1]++) {
                    for (score[2] = 0; score[2] <= 10; score[2]++) {
                        for (score[3] = 0; score[3] <= 10; score[3]++) {
                            int diagonal = score[0] + score[1] + score[2] + score[3];
                            if (diagonal % 2 == 0) {
                                score[4] = 10; score[5] = 10;
                                score[6] = 10 - diagonal / 2;
                                score[7] = -10; score[8] = -10; score[9] = -10;
                                int sum = 0;
                                for (int i = 0; i < 10; i++) {
                                    sum += score[i] * count.get(i);
                                }
                                if (sum > sol) sol = sum;
                            }
                        }
                    }
                }
            }
            System.out.println("Case #" + caseNr + ": " + sol);
            in.readLine();
        }
        in.close();
        reader.close();
    }
}