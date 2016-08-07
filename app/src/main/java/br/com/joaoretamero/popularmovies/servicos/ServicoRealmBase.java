package br.com.joaoretamero.popularmovies.servicos;

import io.realm.Realm;

public class ServicoRealmBase {

    protected Realm realm;

    public ServicoRealmBase() {
        this.realm = Realm.getDefaultInstance();
    }

    public void closeRealm() {
        if (this.realm != null)
            this.realm.close();
    }
}
