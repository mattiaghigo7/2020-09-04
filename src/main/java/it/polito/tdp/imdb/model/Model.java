package it.polito.tdp.imdb.model;

import java.util.*;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	
	private ImdbDAO dao;
	
	private List<Movie> allMovies;
	private Map<Integer,Movie> idMovie;
	
	private Graph<Movie,DefaultWeightedEdge> grafo;
	private List<Movie> vertici;
	private List<Coppia> archi;
	
	private List<Movie> migliore;
	
	public Model() {
		this.dao=new ImdbDAO();
		this.allMovies=new ArrayList<>(dao.listAllMovies());
		this.idMovie=new HashMap<>();
		for(Movie m : this.allMovies) {
			this.idMovie.put(m.getId(), m);
		}
	}

	public List<Movie> calcolaCammino(Movie movie){
		this.migliore=new ArrayList<>();
		List<Movie> parziale=new ArrayList<>();
		parziale.add(movie);
		this.migliore=new ArrayList<>(parziale);
		List<Movie> raggiungibili = this.getAdiacenti(movie);
		ricorsione(parziale,raggiungibili,0);
		return migliore;
	}
	
	private void ricorsione(List<Movie> parziale, List<Movie> raggiungibili, int i) {
		if(parziale.size()>migliore.size()) {
			migliore = new ArrayList<>(parziale);
		}
		Movie momentaneo = parziale.get(parziale.size()-1);
		for(Movie m : raggiungibili) {
			for(Coppia c : this.archi) {
				if(c.getM1().equals(momentaneo) && c.getM2().equals(m)) {
					if(!parziale.contains(m)) {
						if(c.getN()>=i) {
							parziale.add(m);
							ricorsione(parziale,this.getAdiacenti(m),(int) c.getN());
							parziale.remove(m);
						}
					}
				}
			}
//			DefaultWeightedEdge e = this.grafo.getEdge(m, momentaneo);
//			if(!parziale.contains(m)) {
//				if(grafo.getEdgeWeight(e)>=i) {
//					parziale.add(m);
//					ricorsione(parziale,this.getAdiacenti(m),(int) grafo.getEdgeWeight(e));
//					parziale.remove(m);
//				}
//			}
		}
	}
	
	public void creaGrafo(double rank) {
		this.grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		this.vertici=new ArrayList<>(dao.getVertici());
		Graphs.addAllVertices(this.grafo, this.vertici);
//		System.out.println(grafo.vertexSet().size());
		
		this.archi=new ArrayList<>(dao.getArchi(rank, idMovie));
		for(Coppia c : this.archi) {
			Graphs.addEdgeWithVertices(this.grafo, c.getM1(), c.getM2(), c.getN());
		}
//		System.out.println(grafo.edgeSet().size());
	}
	
	public int getVerticiSize() {
		return this.grafo.vertexSet().size();
	}
	
	public List<Movie> getVertici(){
		this.vertici.sort(null);
		return this.vertici;
	}
	
	public int getArchiSize() {
		return this.grafo.edgeSet().size();
	}
	
	public Grado getPesoMassimo(){
		Movie movie = null;
		int bestGrado = 0;
		Grado g = null;
		for(Movie m : this.vertici) {
			int grado = 0;
			List<Movie> successori = Graphs.successorListOf(this.grafo, m);
			for(Movie m2 : successori) {
				DefaultWeightedEdge e = this.grafo.getEdge(m, m2);
					grado+=grafo.getEdgeWeight(e);
			}
			if(grado>bestGrado) {
				movie=m;
				bestGrado=grado;
			}
		}
		g = new Grado(movie,bestGrado);
		return g;
	}
	
	public List<Movie> getAdiacenti(Movie movie){
		List<Movie> successori = new ArrayList<>();
		successori = Graphs.successorListOf(this.grafo, movie);
		return successori;
	}
}
