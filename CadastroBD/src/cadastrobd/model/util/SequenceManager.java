package cadastrobd.model.util;

import java.sql.*;

public class SequenceManager {
    public static long getValue(String sequenceName) {
        String sql = "SELECT NEXT VALUE FOR " + sequenceName + " AS seq";
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getLong("seq");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Não foi possível obter valor da sequência: " + sequenceName);
    }
}
