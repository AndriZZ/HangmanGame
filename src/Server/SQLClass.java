package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;

import com.mysql.cj.ServerVersion;

public class SQLClass {
	public JdbcRowSet getCategoriesFromDB(JdbcRowSet rowSet) throws SQLException {
		rowSet.setUrl("jdbc:mysql://localhost/java" + "?user=root&password=root");
		rowSet.setCommand("select * from categories");
		rowSet.execute();
		return rowSet;
	}

	public String pickWordFromDB(Connection conn, String clientInput) throws SQLException {
		String chosenWord = "";
		PreparedStatement pstmt = conn.prepareStatement(
				"SELECT word FROM (SELECT word FROM words AS t WHERE cat_id=?) AS k ORDER BY RAND() LIMIT 1");
		pstmt.setString(1, clientInput);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			chosenWord = (rs.getString(1).substring(1));
		}
		System.out.println("Word to guess: " + chosenWord);
		return chosenWord;
	}
}
