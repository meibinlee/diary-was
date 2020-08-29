package ac.hongik.tripdiary.data;

import java.util.ArrayList;
import java.util.Date;

public class User {
	public String user_id;
	public String user_pw;
	public String user_name;
	public String birthdate;
	public String sex;
	public Date signup_date;
	public ArrayList<Integer> city = new ArrayList<Integer>();
}
