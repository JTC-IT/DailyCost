package bo;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

import bean.member;
import dao.dailyCostDao;

public class dailyCostBo {
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat sdfe = new SimpleDateFormat("E, dd MMM yyyy");
	private dailyCostDao dcdao = new dailyCostDao();
	private ArrayList<member> listMembers = dcdao.getListMember();
	private Calendar calendar = Calendar.getInstance();
	
	public String formatmoney(double money) {
		return new DecimalFormat("###,###,###").format(money)+" VNĐ";
	}
	
	public boolean isLeapYear(int year) {
		if (year % 400 == 0) 
	        return true;
	    if (year % 4 == 0 && year % 100 != 0) 
	        return true;
	    return false; 
	}
	
	public int coutDateByMonth(int month, int year) {
		if(month == 2) {
			if(isLeapYear(year))
				return 29;
			return 28;
		}
		if(month == 4 || month == 6 || month == 9 || month == 11)
			return 30;
		return 31;
	}
	
	public int checkDate(String day, String month, String year) {
		if(year.length() != 4)
			return -3;
		int yy;
		try {
			yy = Integer.parseInt(year);
		} catch (Exception e) {
			return -3;
		}
		
		int mm;
		try {
			mm = Integer.parseInt(month);
			if(mm < 1 || mm > 12)
				return -2;
		} catch (Exception e) {
			return -2;
		}
		
		int dd;
		try {
			dd = Integer.parseInt(day);
			if(dd < 1 || dd > coutDateByMonth(mm, yy))
				return -1;
		} catch (Exception e) {
			return -1;
		}
		
		return 1;
	}
	
	public int getSizeMember() {
		return listMembers.size();
	}
	
	public int getIdMember(int index) {
		return listMembers.get(index).getId();
	}
	
	public String getNameMember(int id) {
		for(member mb: listMembers)
			if(mb.getId() == id)
				return mb.getName();
		return null;
	}
	
	public String getCurDateE() {
		return sdfe.format(new Date());
	}
	
	public int getCurDay() {
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	public int getCurMonth() {
		return calendar.get(Calendar.MONTH)+1;
	}
	
	public int getCurYear() {
		return calendar.get(Calendar.YEAR);
	}
	
	public boolean checkPayCurDate() {
		return dcdao.checkPayCurDate();
	}
	
	public DefaultComboBoxModel<String> get_cmbMonthModel(){
		return new DefaultComboBoxModel<String>(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"});
	}
	
	public DefaultComboBoxModel<String> get_cmbYearModel(){
		int year = getCurYear();
		String[] dsYear = new String[3];
		for(int i = year; i > year-3; i--)
			dsYear[year-i] = ""+i;
		return new DefaultComboBoxModel<String>(dsYear);
	}
	
	public DefaultComboBoxModel<String> get_modelListNameMember() {
		DefaultComboBoxModel<String> modelListNameMember = new DefaultComboBoxModel<String>();
		int len = listMembers.size();
		for(int i = 0; i < len; i++)
			modelListNameMember.addElement(listMembers.get(i).getName());
		return modelListNameMember;
	}
	
	public DefaultTableModel get_TableModelHistory(int month, int year) {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Ngày");
		model.addColumn("Số tiền");
		model.addColumn("Người thanh toán");
		String fromDate = String.format("%d/%d/01", year, month);
		String toDate = String.format("%d/%d/%d", year, month, coutDateByMonth(month, year));
		for(Object[] ds: dcdao.getListDailyCost(fromDate, toDate)) {
			model.addRow(new Object[] {sdf.format(ds[0]), formatmoney((int)ds[1]), getNameMember((int)ds[2])});
		}
		return model;
	}
	
	public boolean InsertDailyCost(String date, int cost, int indexMember) {
		try {
			dcdao.InsertDailyCost(sdf.parse(date), cost, getIdMember(indexMember));
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public int getSumCostByDate(String fromDate, String toDate) {
		try {
			return dcdao.getSumByMonth(sdf.parse(fromDate), sdf.parse(toDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public DefaultTableModel geTableModelPayer(String fromDate,String toDate) {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Thành viên");
		model.addColumn("Số lần chi trả");
		model.addColumn("Đã chi trả");
		model.addColumn("Trung bình chi tiêu");
		model.addColumn("Còn Thiếu");
		
		try {
			Date date1 = sdf.parse(fromDate);
			Date date2 = sdf.parse(toDate);
			float Tong = (float)dcdao.getSumByMonth(date1, date2)/getSizeMember();
			for(member mb: listMembers) {
				int numberPay = dcdao.getNumberPay(date1, date2, mb.getId());
				int payerSum = 	dcdao.getSumByMember(date1, date2, mb.getId());
				model.addRow(
					new Object[] {mb.getName()
								, numberPay
								, formatmoney(payerSum)
								, formatmoney(payerSum/Math.max(1, numberPay))+"/Ngày"
								, formatmoney(Math.max(0.0,(float)Tong-payerSum))
					}
				);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return model;
	}
}