package ac.hongik.tripdiary.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ac.hongik.tripdiary.data.Code;

public class CodeRowMapper implements RowMapper<Code> {

	@Override
	public Code mapRow(ResultSet rs, int rowNum) throws SQLException {
		Code item = new Code();
		try {
			item.city_id = rs.getInt("city_id");
			item.country = rs.getString("country");
			item.city = rs.getString("city");
			item.x = rs.getFloat("x");
			item.y = rs.getFloat("y");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return item;
	}

}