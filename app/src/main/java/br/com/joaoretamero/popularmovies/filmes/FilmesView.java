package br.com.joaoretamero.popularmovies.filmes;

public interface FilmesView {
    void exibeFilmeDetalhe();

    void exibeOpcoesOrdem();

    void exibeConfiguracoes();

    void setShowRefresh(boolean showRefresh);
}
