package com.gcit.lms.domain;

import java.lang.reflect.Method;

public class Author {
	private int authorId;
	private String authorName;

	/**
	 * @return the authorId
	 */
	public int getAuthorId() {
		return authorId;
	}

	/**
	 * @param authorId
	 *            the authorId to set
	 */
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	/**
	 * @return the authorName
	 */
	public String getAuthorName() {
		return authorName;
	}

	/**
	 * @param authorName
	 *            the authorName to set
	 */
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public static void main(String[] args) {
		try {
			String className = "com.gcit.lms.domain.Author";
			String attrib = "authorName";

			Author a = new Author();
			Class c = Class.forName(className);
			Object obj = c.newInstance();

			a.setAuthorName("test");
			Method setter = c.getMethod(
					"set" + attrib.substring(0, 1).toUpperCase()
							+ attrib.substring(1, attrib.length()),
					String.class);
			setter.invoke(obj, "test");

			System.out.println(a.getAuthorName());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
