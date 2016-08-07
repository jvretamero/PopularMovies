package br.com.joaoretamero.popularmovies.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Genre extends RealmObject {

    @PrimaryKey
    public int id;
    public String name;
}
