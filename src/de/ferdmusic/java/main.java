package src.de.ferdmusic.java;

import java.util.Scanner;

public class main {



    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
//        Berechnungen berechnungen = new Berechnungen();

        System.out.println("Bitte gebe die Überschrift ein \n");
        String ueberschrift = cin();
        System.out.println("Bitte gebe den Text ein \n");
        String text = cin();

        FileHandler fh = new FileHandler(ueberschrift, text);
    }

    public static String cin() {
        return scanner.nextLine();
    }
}