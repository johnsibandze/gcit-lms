package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Genre;

public class BookDAO extends BaseDAO<Book> implements ResultSetExtractor<List<Book>>{
	@Autowired
	PublisherDAO pdao;
	
	@Autowired
	AuthorDAO aDao;
	
	@Autowired
	GenreDAO gDao;

	public void create(final Book book) throws Exception {
		KeyHolder keyHolder = new GeneratedKeyHolder();
//		template.update("insert into tbl_book (title, pubId) values(?, ?)",
//				new Object[] { book.getTitle(), book.getPublisher().getPublisherId()}, keyHolder);
		
		template.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement pstmt = template.getDataSource().getConnection().prepareStatement("insert into tbl_book (title, pubId) values(?, ?)",  Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, book.getTitle());
				pstmt.setInt(2, 3);
				return pstmt;
			}
		}, keyHolder);

		int bookId = keyHolder.getKey().intValue();
		
		for(Author a: book.getAuthors()){
			template.update("insert into tbl_book_authors (bookId, authorId) values (?,?)", 
				new Object[]{bookId, a.getAuthorId()});
		}
		
//		for(Genre g: book.getGenres()){
//			template.update("insert into tbl_book_genres (bookId, genre_id) values (?,?)", 
//				new Object[]{bookId, g.getGenre_id()});
//		}
	}
	
	public List<Book> readAll() throws Exception{
		return (List<Book>) template.query("select * from tbl_book", this);
		
	}
	
	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<Book>();
		//GenreDAO gD
		while(rs.next()){
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			try {
				b.setPublisher(pdao.readOne(rs.getInt("pubId")));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			@SuppressWarnings("unchecked")
			List<Author> authors = (List<Author>) aDao.template.query("select * from tbl_author where authorId In"
					+ "(select authorId from tbl_book_authors where bookId=?)", new Object[] {rs.getInt("bookId")}, aDao);
			b.setAuthors(authors);
			@SuppressWarnings("unchecked")
			List<Genre> genres = (List<Genre>) gDao.template.query("select * from tbl_genre where genre_id In"
					+ "(select genre_id from tbl_book_genres where bookId=?)", new Object[] {rs.getInt("bookId")}, gDao);
			b.setGenres(genres);
			books.add(b);
		}
		
		return books;
	}
}
