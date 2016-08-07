package br.com.joaoretamero.popularmovies.servicos;

import br.com.joaoretamero.popularmovies.modelo.Filme;
import io.realm.RealmResults;
import io.realm.Sort;

public class ServicoFilme extends ServicoRealmBase {

    public RealmResults<Filme> getAllFilmes() {
        return this.realm.where(Filme.class).findAll().sort("nota", Sort.DESCENDING);
    }
}
