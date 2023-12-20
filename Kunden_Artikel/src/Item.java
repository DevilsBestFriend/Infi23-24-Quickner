import java.util.Scanner;

public class Item {
    static Scanner scn = new Scanner(System.in);

    public static String createitem() {
        System.out.println("Name of the item:");
        String name = scn.nextLine();
        System.out.println("Price of the item:");
        String price = scn.nextLine();
        return "INSERT INTO artikel (bezeichnung, preis) VALUES ('" + name + "', " + Double.parseDouble(price) + ");";
    }

    public static String oderitem() {
        System.out.println("ID from item:");
        int id_i = Integer.parseInt(scn.nextLine());
        System.out.println("ID customer");
        int id_c = Integer.parseInt(scn.nextLine());
        System.out.println("How manny:");
        int anzahl = Integer.parseInt(scn.nextLine());
        return "INSERT INTO Bestellungen (id_kunde, id_artikel, anzahl) VALUES(" +
                id_c + ", " + id_i + ", " + anzahl + ");";
    }
}
