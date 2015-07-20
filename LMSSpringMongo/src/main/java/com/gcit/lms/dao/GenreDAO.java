package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Genre;
import com.gcit.lms.domain.Publisher;

@SuppressWarnings("unchecked")
public class GenreDAO extends BaseDAO<Genre> implements ResultSetExtractor<List<Genre>>{

	
	public void create(Genre genre) throws Exception {
		template.update("insert into tbl_publisher (genreName) values(?, ?)",
				new Object[] { genre.getGenre_name()});
	}

	public void update(Publisher publisher) throws Exception {
		template.update("update tbl_publisher set publisherName = ?, publisherAddress=?, publisherPhone=? where publisherId = ?",
				new Object[] { publisher.getPublisherName(),  publisher.getPublisherAddress(), publisher.getPublisherPhone(), publisher.getPublisherId() });

	}

	public void delete(Author author) throws Exception {
		template.update("delete from tbl_author where authorId = ?",
				new Object[] { author.getAuthorName() });
	}

	
	public List<Genre> readAll() throws Exception{
		return (List<Genre>) template.query("select * from tbl_genre", this);
		
	}

	public Genre readOne(int genre_id) throws Exception {
		List<Genre> genres = (List<Genre>) template.query("select * from tbl_genre where genre_id =? ", new Object[] {genre_id}, this);
		if(genres!=null && genres.size()>0){
			return genres.get(0);
		}
		return null;
	}

	@Override
	public List extractData(ResultSet rs) throws SQLException {
		List<Genre> genres =  new ArrayList<Genre>();
		
		while(rs.next()){
			Genre g = new Genre();
			g.setGenre_id(rs.getInt("genre_id"));
			g.setGenre_name(rs.getString("genre_name"));
			genres.add(g);
		}
		return genres;
	}

}
