package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;

import bean.member;

public class dailyCostDao {
	private Connection connec;
	private String sql;
	
	public dailyCostDao() {
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

	public boolean checkPayCurDate() {
		sql = "{? = Call func_checkPayCurDate()}";
		boolean ok = false;
		try {
			CallableStatement cas = connec.prepareCall(sql);
			cas.registerOutParameter(1, Types.BIT);
			cas.execute();
			ok = cas.getBoolean(1);
			cas.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ok;
	}
	
	public ArrayList<member> getListMember(){
		ArrayList<member> list = new ArrayList<member>();
		sql = "SELECT * FROM member WHERE isActive = 1";
		try {
			Statement sm = connec.createStatement();
			ResultSet rs = sm.executeQuery(sql);
			while(rs.next()) {
				list.add(new member(rs.getInt(1), rs.getString(2), rs.getNString(3), rs.getBoolean(4)));
			}
			rs.close();
			sm.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<Object[]> getListDailyCost(String fromDate, String toDate) {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		
		sql = "select date, cost, userID "
				+ "from dailycost "
				+ "where date between '"+fromDate+"' and '"+toDate+"' "
				+ "order by date desc;";
		try {
			Statement st = connec.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				list.add(new Object[] {rs.getDate(1),rs.getInt(2),rs.getInt(3)});
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int getSumByMonth(Date fromDate, Date toDate) {
		int sum = 0;
		sql = "{? = Call func_SumCost(?,?)}";
		try {
			CallableStatement cas;
			cas = connec.prepareCall(sql);
			cas.setDate(2, new java.sql.Date(fromDate.getTime()));
			cas.setDate(3, new java.sql.Date(toDate.getTime()));
			cas.registerOutParameter(1, Types.INTEGER);
			cas.execute();
			sum = cas.getInt(1);
			cas.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sum;
	}
	
	public int getNumberPay(Date fromDate, Date toDate, int userId) {
		int sum = 0;
		sql = "{? = Call func_numberPay(?, ?, ?)}";
		try {
			CallableStatement cas;
			cas = connec.prepareCall(sql);
			cas.registerOutParameter(1, Types.INTEGER);
			cas.setDate(2, new java.sql.Date(fromDate.getTime()));
			cas.setDate(3, new java.sql.Date(toDate.getTime()));
			cas.setInt(4, userId);
			cas.execute();
			sum = cas.getInt(1);
			cas.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sum;
	}
	
	public int getSumByMember(Date fromDate, Date toDate, int userId) {
		int sum = 0;
		sql = "{? = Call func_SumCostByMember(?, ?, ?)}";
		try {
			CallableStatement cas;
			cas = connec.prepareCall(sql);
			cas.registerOutParameter(1, Types.INTEGER);
			cas.setDate(2, new java.sql.Date(fromDate.getTime()));
			cas.setDate(3, new java.sql.Date(toDate.getTime()));
			cas.setInt(4, userId);
			cas.execute();
			sum = cas.getInt(1);
			cas.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sum;
	}
	
	public boolean InsertDailyCost(Date date, int cost, int userId) {
		sql = "insert into dailycost(date,cost,userID) values(?,?,?)";
		try {
			PreparedStatement ps = connec.prepareStatement(sql);
			ps.setDate(1, new java.sql.Date(date.getTime()));
			ps.setInt(2, cost);
			ps.setInt(3, userId);
			return ps.executeUpdate()>0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
