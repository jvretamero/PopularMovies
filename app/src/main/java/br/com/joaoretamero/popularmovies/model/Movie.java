package br.com.joaoretamero.popularmovies.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Movie extends RealmObject {

    @PrimaryKey
    public int id;
    public String poster;
    public String backdrop;
    public String title;
    public String overview;
    public int runtime;
    public float voteAverage;
    public RealmList<ProductionCompany> productionCompanies;
    public RealmList<Genre> genres;
}
