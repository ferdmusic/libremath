package src.de.ferdmusic.java;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        System.out.println("Bitte gebe die Überschrift ein \n");
        String ueberschrift = cin();
        System.out.println("Bitte gebe den Text ein \n");
        String text = cin();

        FileHandler fh = new FileHandler(ueberschrift, text);
//        String test = fh.createFolder(ueberschrift);
//        System.out.println(test);
//        fh.copyTemplate();
//        fh.createContent(fh.getZielordner(),ueberschrift,text);
    }

    public static String cin() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
