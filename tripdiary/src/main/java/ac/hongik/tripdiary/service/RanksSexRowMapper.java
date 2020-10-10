package ac.hongik.tripdiary.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ac.hongik.tripdiary.data.RanksSex;

public class RanksSexRowMapper implements RowMapper<RanksSex> {

	@Override
	public RanksSex mapRow(ResultSet rs, int rowNum) throws SQLException {
		RanksSex item = new RanksSex();
		try {
			item.sex = rs.getString("sex");
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