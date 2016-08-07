package br.com.joaoretamero.popularmovies.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProductionCompany extends RealmObject {

    @PrimaryKey
    public int id;
    public int name;
}
