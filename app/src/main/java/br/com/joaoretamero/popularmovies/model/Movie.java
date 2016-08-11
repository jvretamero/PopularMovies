package br.com.joaoretamero.popularmovies.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "movie", id = "id")
public class Movie extends Model {

    @Column(name = "id")
    public int id;

    @Column(name = "poster")
    public String poster;

    @Column(name = "backdrop")
    public String backdrop;

    @Column(name = "title")
    public String title;

    @Column(name = "overview")
    public String overview;

    @Column(name = "runtime")
    public int runtime;

    @Column(name = "vote_average")
    public float voteAverage;
}
