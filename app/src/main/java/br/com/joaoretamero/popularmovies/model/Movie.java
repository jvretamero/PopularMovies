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
    public String homepage;
    public String idImdb;
    public float voteAverage;
    public RealmList<ProductionCompany> productionCompanies;
    public RealmList<Genre> genres;
    public RealmList<Video> videos;
}
