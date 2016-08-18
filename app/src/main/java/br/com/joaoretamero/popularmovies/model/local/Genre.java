package br.com.joaoretamero.popularmovies.model.local;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "genre", id = "_id")
public class Genre extends Model {

    @Column(name = "name")
    public String name;

    public Genre() {
        super();
    }
}
