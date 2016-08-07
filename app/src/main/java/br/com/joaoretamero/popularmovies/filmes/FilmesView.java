package br.com.joaoretamero.popularmovies.filmes;

import br.com.joaoretamero.popularmovies.modelo.Filme;
import io.realm.RealmResults;

public interface FilmesView {
    void exibeFilmeDetalhe();

    void exibeOpcoesOrdem();

    void exibeConfiguracoes();

    void exibeIndicadorAtualizando(boolean exibeIndicador);

    void exibeFilmes(RealmResults<Filme> filmes);
}
