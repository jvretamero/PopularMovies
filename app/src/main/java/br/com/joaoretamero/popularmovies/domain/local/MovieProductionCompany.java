package br.com.joaoretamero.popularmovies.domain.local;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;

import java.util.List;

@Table(name = "movie_production_company", id = "_id")
public class MovieProductionCompany extends Model {

    @Column(name = "movie")
    public Movie movie;

    @Column(name = "production_company")
    public ProductionCompany productionCompany;

    public MovieProductionCompany() {
        super();
    }

    public static void clearAll() {
        new Delete()
                .from(MovieProductionCompany.class)
                .execute();
    }

    public static void clearAllFromMovie(Long movieId) {
        new Delete()
                .from(MovieProductionCompany.class)
                .where("movie = ?", movieId)
                .execute();
    }

    public static void bulkInsert(Movie movie, List<ProductionCompany> productionCompanyList) {
        ActiveAndroid.beginTransaction();
        try {
            MovieProductionCompany movieProductionCompany;
            for (ProductionCompany productionCompany : productionCompanyList) {
                movieProductionCompany = new MovieProductionCompany();
                movieProductionCompany.movie = movie;
                movieProductionCompany.productionCompany = productionCompany;
                movieProductionCompany.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }
}
