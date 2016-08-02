package br.com.joaoretamero.popularmovies.modelo;

public class ItemFilme {

    private int filmeId;
    private String posterPath;
    private String titulo;

    public ItemFilme(int filmeId, String titulo) {
        this.filmeId = filmeId;
        this.titulo = titulo;
        this.posterPath = null;
    }

    public int getFilmeId() {
        return filmeId;
    }

    public void setFilmeId(int filmeId) {
        this.filmeId = filmeId;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
