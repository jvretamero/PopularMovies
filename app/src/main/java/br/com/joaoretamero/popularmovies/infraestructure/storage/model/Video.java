package br.com.joaoretamero.popularmovies.infraestructure.storage.model;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "video", id = "_id")
public class Video extends Model {

    @Column(name = "movie")
    public Movie movie;

    @Column(name = "name")
    public String name;

    @Column(name = "youtube_id")
    public String youtubeId;

    public Video() {
        super();
    }

    public static List<Video> findAllFromMovie(Long movieId) {
        return new Select()
                .from(Video.class)
                .where("movie = ?", movieId)
                .orderBy("name")
                .execute();
    }

    public static void clearAll() {
        new Delete()
                .from(Video.class)
                .execute();
    }

    public static void clearAllFromMovie(Long movieId) {
        new Delete()
                .from(Video.class)
                .where("movie = ?", movieId)
                .execute();
    }

    public static void bulkInsert(Movie movie, List<Video> videoList) {
        ActiveAndroid.beginTransaction();
        try {
            for (Video video : videoList) {
                video.movie = movie;
                video.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }
}
