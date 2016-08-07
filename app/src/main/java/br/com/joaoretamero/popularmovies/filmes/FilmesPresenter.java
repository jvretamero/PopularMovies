package br.com.joaoretamero.popularmovies.filmes;

import br.com.joaoretamero.popularmovies.servicos.ServicoFilme;

public class FilmesPresenter {

    private FilmesView view;
    private ServicoFilme servicoFilme;

    public FilmesPresenter(FilmesView view) {
        this.view = view;
        this.servicoFilme = new ServicoFilme();
    }

    public void inicia() {
        view.exibeIndicadorAtualizando(true);
        listaFilmes();
    }

    public void finaliza() {
        this.servicoFilme.closeRealm();
    }

    public void onItemClick() {
        view.exibeFilmeDetalhe();
    }

    public void onRefresh() {
        listaFilmes();
    }

    public void onMenuOrdemClick() {
        view.exibeOpcoesOrdem();
    }

    public void onMenuConfiguracoesClick() {
        view.exibeConfiguracoes();
    }

    private void listaFilmes() {
        view.exibeFilmes(servicoFilme.getAllFilmes());
        view.exibeIndicadorAtualizando(false);
    }
}
