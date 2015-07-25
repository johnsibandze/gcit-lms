package com.gcit.lms.domain;

import java.util.UUID;

import org.springframework.data.annotation.Id;

public class Genre {

	private UUID genreId = UUID.randomUUID();
	private String genreName;

	@Id
	public UUID getGenreId() {
		return genreId;
	}

	public void setGenreId(UUID genreId) {
		this.genreId = genreId;
	}

	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

}
