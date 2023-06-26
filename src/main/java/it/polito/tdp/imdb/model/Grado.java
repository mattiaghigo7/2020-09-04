package it.polito.tdp.imdb.model;

public class Grado {
	
	private Movie m;
	private int grado;
	
	public Grado(Movie m, int grado) {
		super();
		this.m = m;
		this.grado = grado;
	}

	public Movie getM() {
		return m;
	}

	public int getGrado() {
		return grado;
	}
	
	
}
