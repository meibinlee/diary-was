package ac.hongik.tripdiary.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ac.hongik.tripdiary.data.Diary;

public class DiaryRowMapper implements RowMapper<Diary> {

	@Override
	public Diary mapRow(ResultSet rs, int rowNum) throws SQLException {
		Diary item = new Diary();
		try {
			item.diary_id = rs.getInt("diary_id");
			item.user_id = rs.getString("user_id");
			item.title = rs.getString("title");
			item.city_id = rs.getInt("city_id");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return item;
	}

}