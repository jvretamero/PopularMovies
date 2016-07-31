package br.com.joaoretamero.popularmovies.modelo;

public class ItemFilme {

    private int filmeId;
    private byte[] imagem;
    private String titulo;

    public ItemFilme(int filmeId, String titulo) {
        this.filmeId = filmeId;
        this.titulo = titulo;
        this.imagem = null;
    }

    public int getFilmeId() {
        return filmeId;
    }

    public void setFilmeId(int filmeId) {
        this.filmeId = filmeId;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
