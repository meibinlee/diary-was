package ac.hongik.tripdiary.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CountryRowMapper implements RowMapper<String> {

	@Override
	public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		String item = "";
		try {
			item = rs.getString("country");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return item;
	}

}