package fr.cseries.cseries.episodes;

public class Episode {

	private String name;
	private String serie;
	private String episode;

	public Episode(String name, String serie, String episode) {
		this.name = name;
		this.serie = serie;
		this.episode = episode;
	}

	// SETTERS

	public void setName(String name) {
		this.name = name;
	}

	public void setSerie(String serie) { this.serie = serie; }

	public void setEpisode(String episode) { this.episode = episode; }

	// GETTERS

	public String getName() {
		return name;
	}

	public String getSerie() { return serie; }

	public String getEpisode() { return episode; }
}