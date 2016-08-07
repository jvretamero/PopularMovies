package br.com.joaoretamero.popularmovies.repository;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class BaseRealmRepository<T extends RealmObject> {

    protected Realm realm;
    private Class<T> tClass;

    public BaseRealmRepository(Class<T> tClass) {
        this.tClass = tClass;
        this.realm = Realm.getDefaultInstance();
    }

    public RealmResults<T> findAll() {
        return realm.where(tClass).findAll();
    }

    public void closeRealm() {
        if (this.realm != null)
            this.realm.close();
    }
}
