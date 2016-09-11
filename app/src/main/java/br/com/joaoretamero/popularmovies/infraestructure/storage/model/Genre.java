package br.com.joaoretamero.popularmovies.infraestructure.storage.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "genre", id = "_id")
public class Genre extends Model {

    @Column(name = "genre_id")
    public int genreId;

    @Column(name = "name")
    public String name;

    public Genre() {
        super();
    }

    public static Genre findOneByGenreId(int genreId) {
        return new Select()
                .from(Genre.class)
                .where("genre_id = ?", genreId)
                .executeSingle();
    }

    public static List<Genre> findAllFromMovie(Long movieId) {
        return joinMovie(new Select().from(Genre.class), movieId)
                .orderBy("target_model.name")
                .execute();
    }

    private static From joinMovie(From from, Long movieId) {
        return from
                .as("target_model")
                .innerJoin(MovieGenre.class)
                .as("join_model")
                .on("join_model.genre = target_model._id")
                .where("join_model.movie = ?", movieId);
    }

}
