package ac.hongik.tripdiary.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ac.hongik.tripdiary.data.RanksAllAndAge;

public class RanksAllAndAgeRowMapper implements RowMapper<RanksAllAndAge> {

	@Override
	public RanksAllAndAge mapRow(ResultSet rs, int rowNum) throws SQLException {
		RanksAllAndAge item = new RanksAllAndAge();
		try {
			item.city = rs.getString("city");
			item.city_id = rs.getInt("city_id");
			item.count = rs.getInt("count");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return item;
	}

}