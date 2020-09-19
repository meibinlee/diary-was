package ac.hongik.tripdiary.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ac.hongik.tripdiary.data.User;
import ac.hongik.tripdiary.data.UserCity;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User item = new User();
		try {
			item.user_id = rs.getString("user_id");
			item.user_pw = rs.getString("user_pw");
			item.user_name = rs.getString("user_name");
			item.birthdate = rs.getString("birthdate");
			item.sex = rs.getString("sex");
			item.signup_date = rs.getDate("signup_date");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return item;
	}

}