import java.util.Scanner;

public class Customer {

    static Scanner scn = new Scanner(System.in);

    public static String createcustomer() {
        System.out.println("Name of the customer:");
        String name = scn.nextLine();
        System.out.println("Email of the customer:");
        String email = scn.nextLine();
        return "INSERT INTO kunden (name, email) VALUES ('" + name + "', '" + email + "');";
    }

    public static String customerorder() {
        System.out.println("with name [n] / with id [id]");
        if (scn.nextLine().equals("n")) {
            System.out.println("Customer name:");
            String k_name = scn.nextLine();
            return "SELECT k.name, a.bezeichnung, a.preis, b.anzahl FROM bestellungen AS b " +
                    "INNER JOIN kunden AS k ON k.id = b.id_kunde " +
                    "INNER JOIN artikel AS a ON a.id = b.id_artikel " +
                    "WHERE " + k_name + " = k.name;";
        } else {
            System.out.println("Customer id:");
            int c_id = Integer.parseInt(scn.nextLine());
            return "SELECT k.name, a.bezeichnung, a.preis, b.anzahl FROM bestellungen AS b " +
                    "INNER JOIN kunden AS k ON k.id = b.id_kunde " +
                    "INNER JOIN artikel AS a ON a.id = b.id_artikel " +
                    "WHERE " + c_id + " = k.id;";
        }
    }
}
