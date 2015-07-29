package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Genre;

public class GenreDAO extends BaseDAO<Genre> implements
		ResultSetExtractor<List<Genre>> {

	private final String GENRE_COLLECTION = "Genres";

	public void create(Genre genre) throws Exception {
		mongoOps.insert(genre, GENRE_COLLECTION);
	}

	public void update(Genre genre) {
		// Query query = new
		// Query(Criteria.where("_id").is(genre.getGenreId()));
		// Genre oldGenre = mongoOps.findOne(query, Genre.class,
		// GENRE_COLLECTION);
		//
		// oldGenre.setGenreName(genre.getGenreName());
		mongoOps.save(genre, GENRE_COLLECTION);
	}

	public void delete(Genre genre) {
		Query query = new Query(Criteria.where("_id").is(genre.getGenreId()));
		mongoOps.remove(query, GENRE_COLLECTION);
	}

	public List<Genre> readAll() throws Exception {
		return mongoOps.findAll(Genre.class, GENRE_COLLECTION);
	}

	public Genre readOne(UUID genreId) throws Exception {
		Query query = new Query(Criteria.where("_id").is(genreId));
		return this.mongoOps.findOne(query, Genre.class, GENRE_COLLECTION);
	}

	@Override
	public List<Genre> extractData(ResultSet rs) throws SQLException {
		List<Genre> genres = new ArrayList<Genre>();

		while (rs.next()) {
			Genre g = new Genre();
			// g.setGenre_id(rs.getInt("genre_id"));
			// g.setGenre_name(rs.getString("genre_name"));
			genres.add(g);
		}
		return genres;
	}

}
