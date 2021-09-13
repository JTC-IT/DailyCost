package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;

public class LoginDao {
	private Connection connec;
	private String sql;
	public LoginDao() {
		super();
		String url = "jdbc:sqlserver://localhost:1433;databaseName=DailyCost";
		String user = "chinh";
		String password = "ttc0206";
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connec = DriverManager.getConnection(url,user,password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean signIn(String username, String password) {
		sql = "{? = Call func_checkAccount(?,?)}";
		try {
			CallableStatement cas = connec.prepareCall(sql);
			cas.registerOutParameter(1, Types.BIT);
			cas.setString(2, username);
			cas.setString(3, password);
			cas.execute();
			return cas.getBoolean(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
