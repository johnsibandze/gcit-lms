package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Genre;
import com.gcit.lms.domain.Publisher;

@SuppressWarnings("unchecked")
public class GenreDAO extends BaseDAO<Genre> {

	public GenreDAO(Connection conn) throws Exception {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void create(Genre genre) throws Exception {
		save("insert into tbl_publisher (genreName) values(?, ?)",
				new Object[] { genre.getGenre_name()});
	}

	public void update(Publisher publisher) throws Exception {
		save("update tbl_publisher set publisherName = ?, publisherAddress=?, publisherPhone=? where publisherId = ?",
				new Object[] { publisher.getPublisherName(),  publisher.getPublisherAddress(), publisher.getPublisherPhone(), publisher.getPublisherId() });

	}

	public void delete(Author author) throws Exception {
		save("delete from tbl_author where authorId = ?",
				new Object[] { author.getAuthorName() });
	}

	
	public List<Genre> readAll() throws Exception{
		return (List<Genre>) read("select * from tbl_genre", null);
		
	}

	public Genre readOne(int genre_id) throws Exception {
		List<Genre> genres = (List<Genre>) read("select * from tbl_genre where genre_id =? ", new Object[] {genre_id});
		if(genres!=null && genres.size()>0){
			return genres.get(0);
		}
		return null;
	}

	@Override
	public List extractData(ResultSet rs) throws Exception {
		List<Genre> genres =  new ArrayList<Genre>();
		
		while(rs.next()){
			Genre g = new Genre();
			g.setGenre_id(rs.getInt("genre_id"));
			g.setGenre_name(rs.getString("genre_name"));
			genres.add(g);
		}
		return genres;
	}

	@Override
	public List extractDataFirstLevel(ResultSet rs) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
