package ac.hongik.tripdiary.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ac.hongik.tripdiary.data.Posting;

public class PostingRowMapper implements RowMapper<Posting> {

	@Override
	public Posting mapRow(ResultSet rs, int rowNum) throws SQLException {
		Posting item = new Posting();
		try {
			item.diary_id = rs.getInt("diary_id");
			item.photo = rs.getString("photo");
			item.diaryment = rs.getString("diaryment");
			item.posting_date = rs.getDate("posting_date");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return item;
	}

}