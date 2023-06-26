package it.polito.tdp.imdb.model;

import java.util.Objects;

public class Coppia {
	
	private Movie m1;
	private Movie m2;
	private int n;
	
	public Coppia(Movie m1, Movie m2, int n) {
		super();
		this.m1 = m1;
		this.m2 = m2;
		this.n = n;
	}

	public Movie getM1() {
		return m1;
	}

	public void setM1(Movie m1) {
		this.m1 = m1;
	}

	public Movie getM2() {
		return m2;
	}

	public void setM2(Movie m2) {
		this.m2 = m2;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	@Override
	public int hashCode() {
		return Objects.hash(m1, m2, n);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coppia other = (Coppia) obj;
		return Objects.equals(m1, other.m1) && Objects.equals(m2, other.m2) && n == other.n;
	}

	@Override
	public String toString() {
		return "m1=" + m1 + ", m2=" + m2 + ", n=" + n;
	}
	
}
