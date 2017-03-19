package fr.cseries.cseries.saisons;

/**
 * Class objet pour les jeux.
 */
public class Saisons {

	private String name;
	private String serie;

	public Saisons(String name, String serie) {
		this.name = name;
		this.serie = serie;
	}

	// SETTERS

	public void setName(String name) {
		this.name = name;
	}

	public void setSerie(String serie) { this.serie = serie; }

	// GETTERS

	public String getName() {
		return name;
	}

	public String getSerie() { return serie; }
}
