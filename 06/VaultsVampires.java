import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class VaultsVampires {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);
        int caseNum = Integer.parseInt(in.readLine());
        for (int caseNr = 1; caseNr <= caseNum; caseNr++) {
            String[] paramLine = in.readLine().split(" ");
            int n = Integer.parseInt(paramLine[0]);//least number of points Lea has to get
            String[] dices = paramLine[1].split("\\+");
            ArrayList<Integer> diceRanges = new ArrayList<>();
            HashMap<Integer, Probability> probs = new HashMap<>();
            for (String dice : dices) {
                //set of a dice with b sides each
                String[] line = dice.split("d");
                int a = Integer.parseInt(line[0]);
                for (int j = 0; j < a; j++) {
                    diceRanges.add(Integer.parseInt(line[1]));
                }
            }
            for (int i = 1; i <= diceRanges.get(0); i++) {
                probs.put(i, new Probability(new BigInteger("1"), new BigInteger(String.valueOf(diceRanges.get(0)))));
            }
            for (int i = 1; i < diceRanges.size(); i++) {
                int range = diceRanges.get(i);
                HashMap<Integer, Probability> probsNew = new HashMap<>();
                for (int toAdd = 1; toAdd <= range; toAdd++){
                    for (Integer oldValue : probs.keySet()) {
                        Integer newValue = oldValue + toAdd;
                        Probability oldProb = probs.get(oldValue);
                        BigInteger nomToAdd = oldProb.nom;
                        BigInteger denToAdd = oldProb.den.multiply(new BigInteger(String.valueOf(range)));
                        if (probsNew.containsKey(newValue)) {
                            Probability currentProb = probsNew.get(newValue);
                            probsNew.replace(newValue, new Probability(
                                    currentProb.nom.multiply(denToAdd).add(nomToAdd.multiply(currentProb.den)),
                                    currentProb.den.multiply(denToAdd)));
                        } else {
                            probsNew.put(newValue, new Probability(nomToAdd, denToAdd));
                        }
                    }
                }
                probs = probsNew;
            }
            //if (probs.size() == 44)
                //System.out.println(probs.values().stream().map(p -> p.nom.multiply(new BigInteger("2400").divide(p.den))).reduce(new BigInteger("0"), BigInteger::add));
            Probability res = new Probability(new BigInteger("0"), new BigInteger("1"));
            for (Integer value : probs.keySet()) {
                //System.out.println(value + ": " + probs.get(value).nom + "/" + probs.get(value).den);
                if (value >= n) {
                    BigInteger a = res.nom;
                    BigInteger b = res.den;
                    BigInteger c = probs.get(value).nom;
                    BigInteger d = probs.get(value).den;
                    res = new Probability(a.multiply(d).add(b.multiply(c)), b.multiply(d));
                }
            }
            System.out.println("Case #" + caseNr + ": " + res.nom + "/" + res.den);
        }
        in.close();
        reader.close();
    }
}

class Probability {
    BigInteger nom;
    BigInteger den;

    public Probability(BigInteger nom, BigInteger den) {
        BigInteger gcd = nom.gcd(den);
        this.nom = nom.divide(gcd);
        this.den = den.divide(gcd);
    }
}