package SQL;

import java.sql.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class rowReads {

    public static Map<String, Object> randomFood(Connection conn,
                                                 String [] filterCols, Object [] filterVals,
                                                 boolean used){
        filterCols = Arrays.copyOf(filterCols, filterCols.length + 1);
        filterVals = Arrays.copyOf(filterVals, filterVals.length + 1);
        filterCols[filterCols.length - 1] = "used";
        filterVals[filterVals.length - 1] = used ? 1 : 0;
        return findRow(conn, "foods", filterCols, filterVals);
    }

    public static Map<String, Object> randomFood(Connection conn, boolean used){
        return randomFood(conn, new String[]{}, new Object[]{}, used);
    }


    public static Map<String, Object> findRow(Connection conn, String table, String[] filterCols, Object[] filterVals) {
        Map<String, Object> rowMap = new LinkedHashMap<>();
        String whereClause = String.join(" AND ",
                Arrays.stream(filterCols).map(c -> c + " = ?").toArray(String[]::new));
        String sql = "SELECT * FROM " + table + " WHERE " + whereClause + "ORDER BY random() LIMIT 1;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            int i = 1;
            for (Object fval : filterVals) {
                stmt.setObject(i++, fval);
            }
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();
            for (i = 1; i <= columnCount; i++) {
                String columnName = meta.getColumnName(i);
                String value = rs.getString(i);
                rowMap.put(columnName, value);
            }
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return rowMap;
    }
}
