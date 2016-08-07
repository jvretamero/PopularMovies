package br.com.joaoretamero.popularmovies.services;

import io.realm.Realm;

public abstract class BaseRealmService {

    protected Realm realm;

    public BaseRealmService() {
        this.realm = Realm.getDefaultInstance();
    }

    public void closeRealm() {
        if (this.realm != null)
            this.realm.close();
    }
}
