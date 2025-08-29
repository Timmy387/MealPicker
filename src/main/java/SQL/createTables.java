package SQL;

import java.sql.Connection;
import java.sql.Statement;

public class createTables {
    public static void createAllTables(Connection conn){
        createFoodsTable(conn);
    }

    public static void createFoodsTable(Connection conn){
        String sql = "CREATE TABLE IF NOT EXISTS foods (\n"
                + "id INTEGER PRIMARY KEY,\n"
                + " name TEXT,\n"
                + " mealType TEXT,\n"
                + " foodType TEXT,\n"
                + " used INTEGER NOT NULL DEFAULT 0\n"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
