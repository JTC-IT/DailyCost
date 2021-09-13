package bo;

import java.util.Calendar;

public class test {

	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		
		System.out.println(calendar.getTime());
		System.out.println(Calendar.MONTH);
		System.out.println(calendar.getMaximum(Calendar.DAY_OF_MONTH));
		System.out.println(calendar.get(Calendar.MONTH));
		System.out.println(calendar.get(Calendar.YEAR));
	}
}
