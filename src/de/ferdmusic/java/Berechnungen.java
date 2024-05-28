package src.de.ferdmusic.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Berechnungen {

    double a;
    double b;
    double c;
    List<String> erklaerungen;

    public Berechnungen() {
        erklaerungen = new ArrayList<>();
        getQuadratischeFormel();
        nullstellenBerechnen();
        for (String erklaerung : erklaerungen) {
            System.out.println(erklaerung);
        }
    }

    public void getQuadratischeFormel() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bitte gebe die quadratische Formel in der Form ax^2+bx+c ein");
        System.out.println("Bitte gebe den Wert für a ein");
        double af = scanner.nextDouble();
        System.out.println("Bitte gebe den Wert für b ein");
        double bf = scanner.nextDouble();
        System.out.println("Bitte gebe den Wert für c ein");
        double cf = scanner.nextDouble();
        System.out.println("Die eingegebene quadratische Formel ist " + af + "x^2+" + bf + "x+" + cf);
        System.out.println("Ist das korrekt? (y/n)");
        String bestaetigung = scanner.next();
        if (bestaetigung.equals("y") || bestaetigung.equals("Y") || bestaetigung.equals("1")) {
            System.out.println("Danke, wir werden ab jetzt mit der Formel weiterarbeiten :)");
            a = af;
            b = bf;
            c = cf;
        } else {
            System.out.println("Bitte geben Sie die quadratische Formel erneut ein");
            getQuadratischeFormel();
        }
    }

    public void nullstellenBerechnen() {
        erklaerungen.add("Gegeben ist die quadratische Gleichung: " + a + "x^2 + " + b + "x + " + c + " = 0");

        if (a != 1) {
            erklaerungen.add("Normieren der Gleichung, indem wir durch " + a + " teilen:");
            b /= a;
            c /= a;
            erklaerungen.add("Die normierte Gleichung lautet: x^2 + " + b + "x + " + c + " = 0");
        } else {
            erklaerungen.add("Da a = 1, ist die Gleichung bereits normiert: x^2 + " + b + "x + " + c + " = 0");
        }

        double p = b;
        double q = c;
        double h = p / 2;
        double hQuad = h * h;
        erklaerungen.add("Quadratische Ergänzung: x^2 + " + p + "x = (x + " + h + ")^2 - " + hQuad);
        erklaerungen.add("Ersatz der Gleichung: (x + " + h + ")^2 - " + hQuad + " + " + q + " = 0");


        double rechts = hQuad - q;
        erklaerungen.add("Umstellen der Gleichung: (x + " + h + ")^2 = " + rechts);

        if (rechts < 0) {
            erklaerungen.add("Da die rechte Seite negativ ist, gibt es keine reellen Nullstellen.");
        } else {
            double sqrtRechts = Math.sqrt(rechts);
            double nullstelle1 = -h + sqrtRechts;
            double nullstelle2 = -h - sqrtRechts;

            if (rechts == 0) {
                erklaerungen.add("Da die rechte Seite null ist, gibt es eine doppelte Nullstelle bei x = " + nullstelle1);
            } else {
                erklaerungen.add("Da die rechte Seite positiv ist, gibt es zwei Nullstellen:");
                erklaerungen.add("Nullstelle 1: x = " + nullstelle1);
                erklaerungen.add("Nullstelle 2: x = " + nullstelle2);
            }
        }
    }

    public static void main(String[] args) {
        new Berechnungen();
    }
}
