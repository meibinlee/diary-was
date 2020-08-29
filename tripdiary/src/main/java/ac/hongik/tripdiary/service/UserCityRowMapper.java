package ac.hongik.tripdiary.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ac.hongik.tripdiary.data.User;
import ac.hongik.tripdiary.data.UserCity;

public class UserCityRowMapper implements RowMapper<UserCity> {

	@Override
	public UserCity mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserCity item = new UserCity();
		try {
			item.user_id = rs.getString("user_id");
			item.city_id = rs.getInt("city_id");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return item;
	}

}