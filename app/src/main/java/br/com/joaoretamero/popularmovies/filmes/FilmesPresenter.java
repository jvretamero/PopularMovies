package br.com.joaoretamero.popularmovies.filmes;

public class FilmesPresenter {

    private FilmesView view;

    public FilmesPresenter(FilmesView view) {
        this.view = view;
    }

    public void inicia() {
        view.setShowRefresh(true);
    }

    public void onItemClick() {
        view.exibeFilmeDetalhe();
    }

    public void onRefresh() {

    }

    public void onMenuOrdemClick() {
        view.exibeOpcoesOrdem();
    }

    public void onMenuConfiguracoesClick() {
        view.exibeConfiguracoes();
    }
}
