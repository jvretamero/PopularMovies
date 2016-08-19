package br.com.joaoretamero.popularmovies.domain.local;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "genre", id = "_id")
public class Genre extends Model {

    @Column(name = "name")
    public String name;

    public Genre() {
        super();
    }

    public static List<Genre> findAllFromMovie(Long movieId) {
        return new Select()
                .from(Genre.class)
                .as("target_model")
                .innerJoin(MovieGenre.class)
                .as("join_model")
                .on("join_model.genre = target_model._id")
                .where("join_model.movie = ?", movieId)
                .orderBy("target_model.name")
                .execute();
    }
}
