package fr.cseries.cseries.home.series;

public class Series {

	private String id;
	private String name;
	private String description;
	private String image;

	public Series(String id) {
		this.id = id;
	}

	// SETTERS

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setImage(String image) {
		this.image = image;
	}

	// GETTERS

	public String getId() { return id; }

	public String getName() {
		return name;
	}

	public String getDescription() { return description; }

	public String getImage() {
		return image;
	}
}
