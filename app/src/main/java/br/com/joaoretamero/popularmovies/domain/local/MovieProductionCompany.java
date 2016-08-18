package br.com.joaoretamero.popularmovies.domain.local;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "movie_production_company", id = "_id")
public class MovieProductionCompany extends Model {

    @Column(name = "movie")
    public Movie movie;

    @Column(name = "production_company")
    public ProductionCompany productionCompany;

    public MovieProductionCompany() {
        super();
    }
}
