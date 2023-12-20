import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost/Kunden_Artike?" + "user=root&password=root";

        try {
            Database db = new Database(url);

            code(db, args);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(69);
        }
    }

    public static void code(Database db, String[] args) throws SQLException {
        db.executeStatement("CREATE TABLE IF NOT EXISTS Kunden (" +
                "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(255)," +
                "email VARCHAR(255)" +
                ");");
        db.executeStatement("CREATE TABLE IF NOT EXISTS Artikel(" +
                "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                "bezeichnung VARCHAR(255)," +
                "preis decimal(10,2)" +
                ");");
        db.executeStatement("CREATE TABLE IF NOT EXISTS Bestellungen(" +
                "id_kunde INT," +
                "id_artikel INT," +
                "anzahl INT, " +
                "PRIMARY KEY (id_kunde, id_artikel, anzahl)," +
                "CONSTRAINT id_kunde FOREIGN KEY Bestellungen(id_kunde) REFERENCES Kunden(id) ON UPDATE cascade on DELETE restrict, " +
                "CONSTRAINT id_artikel FOREIGN KEY Bestellungen(id_artikel) REFERENCES Artikel(id) ON UPDATE cascade ON DELETE restrict);");
        db.getC().commit();

        String weitermachen = "y";
        Scanner scn = new Scanner(System.in);

        while (weitermachen.equals("y")) {
            System.out.println("create customer[c] | create item[i] | view customer order[co] | order item[oi]");
            String action = scn.nextLine();
            String sql = switch (action) {
                case "c":
                    yield Customer.createcustomer();
                case "i":
                    yield Item.createitem();
                case "co":
                    yield Customer.customerorder();
                case "oi":
                    yield Item.oderitem();
                default:
                    yield null;
            };

            if (action.equals("co")) {
                try {
                    db.executeStatementRST(sql);
                    while (db.getRs().next()) {
                        String name = db.getRs().getString("name");
                        String bezeichnung = db.getRs().getString("bezeichnung");
                        double preis = db.getRs().getDouble("preis");
                        int anzahl = db.getRs().getInt("anzahl");

                        System.out.println("CUSTOMER NAME = " + name);
                        System.out.println("ITEM NAME = " + bezeichnung);
                        System.out.println("PRICE = " + preis);
                        System.out.println("COUNT = " + anzahl);
                        System.out.println();
                    }
                } catch (SQLException e) {
                    System.out.println("Überprüfe deine Eingabe");
                    System.out.println(e.getMessage());
                }
            } else db.executeStatement(sql);
            db.getC().commit();

            System.out.println("continue[y/n]?");
            weitermachen = scn.nextLine();
        }

        db.getRs().close();
        db.getStmt().close();
        db.getC().close();
    }
}