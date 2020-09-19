package ac.hongik.tripdiary.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ac.hongik.tripdiary.data.CityName;
import ac.hongik.tripdiary.data.User;
import ac.hongik.tripdiary.data.UserCity;

public class CityNameRowMapper implements RowMapper<CityName> {

	@Override
	public CityName mapRow(ResultSet rs, int rowNum) throws SQLException {
		CityName item = new CityName();
		try {
			item.city_id = rs.getInt("city_id");
			item.city = rs.getString("city");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return item;
	}

}