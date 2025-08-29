package SQL;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private static final Connection conn = connect();
    public static Connection getConnection(){
        return conn;
    }

    public static Connection connect() {
        String appData = System.getenv("APPDATA");
        String dbFolder = appData + File.separator + "MealPicker";
        File dir = new File(dbFolder);
        if (!dir.exists()) {
            System.out.println(dir.mkdirs());
        }
        String url = "jdbc:sqlite:" + dbFolder + File.separator + "mealpicker.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            createTables.createAllTables(conn);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
