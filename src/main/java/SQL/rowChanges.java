package SQL;
import java.sql.*;
import java.util.Arrays;
import java.util.Map;

import static SQL.rowReads.findRow;
import static SQL.rowReads.randomFood;


public class rowChanges {
    public static boolean insert(Connection conn, String table,
                                 String[] columns, Object ... info){
        if (columns.length != info.length) {
            System.out.println("Bad length");
            return false;
        }
        StringBuilder colNames = new StringBuilder();
        StringBuilder colValues = new StringBuilder();
        for (String col : columns){
            colNames.append(col).append(", ");
            colValues.append("?, ");
        }
        colNames.setLength(colNames.length() - 2);
        colValues.setLength(colValues.length() - 2);
        String sql = "INSERT INTO " + table + "(" + colNames + ") VALUES (" + colValues + ");";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            int i = 1;
            for (Object key : info) {
                stmt.setObject(i++, key);
            }
            stmt.executeUpdate();
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public static boolean update(Connection conn, String table,
                                 String [] pkeyCols, Object [] pkeyVals,
                                 String [] changedCols, Object [] newVals){
        if (pkeyCols.length != pkeyVals.length || changedCols.length != newVals.length) {
            System.out.println("Bad length");
            return false;
        }
//        StringBuilder setClause = new StringBuilder();
//        for (String col : changedCols){
//            setClause.append(col).append(" = ?, ");
//        }
//        setClause.setLength(setClause.length() - 2);
//        setClause.append(" ");
//
//        StringBuilder whereClause = new StringBuilder();
//        for (String col : pkeyCols){
//            whereClause.append(col).append(" = ? AND ");
//        }
//        whereClause.setLength(whereClause.length() - 5);
//        whereClause.append(" ");
        String setClause = String.join(", ",
                Arrays.stream(changedCols).map(c -> c + " = ?").toArray(String[]::new));

        // Build WHERE clause
        String whereClause = String.join(" AND ",
                Arrays.stream(pkeyCols).map(c -> c + " = ?").toArray(String[]::new));

        String sql = "UPDATE " + table + " SET " + setClause + " WHERE " + whereClause + ";";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            int i = 1;
            for (Object key : newVals) {
                stmt.setObject(i++, key);
            }
            for (Object key : pkeyVals) {
                stmt.setObject(i++, key);
            }
            stmt.executeUpdate();
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public static boolean setMealUsed(Connection conn, Object mealId, boolean used){
        if (!update(conn, "foods", new String[]{"id"},
                new Object[]{mealId},
                new String[]{"used"}, new Object[]{used ? 1 : 0}))
            return false;
        Map<String, Object> meal = findRow(conn, "foods", new String[]{"id"}, new Object[]{mealId});
        if (meal == null) return false;
        String mealType = (String) meal.get("mealType");
        String foodType = (String) meal.get("foodType");
        if (findRow(conn, "foods", new String[]{"mealType", "foodType", "used"},
                new Object[]{mealType, foodType, 0}) == null) {
            return update(conn, "foods", new String[]{"mealType", "foodType"},
                    new Object[]{mealType, foodType},
                    new String[]{"used"}, new Object[]{0});
        }

        return true;
    }
}
