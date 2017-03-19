package fr.cseries.cseries.home.films;

public class Film {

	private String id;
	private String title;
	private String cover;
	private String file;

	public Film(String id) {
		this.id = id;
	}

	// SETTERS

	public void setId(String id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public void setFile(String file) {
		this.file = file;
	}

	// GETTERS


	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getCover() {
		return cover;
	}

	public String getFile() {
		return file;
	}
}
