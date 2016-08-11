package br.com.joaoretamero.popularmovies.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "genre", id = "id")
public class Genre extends Model {

    @Column(name = "id")
    public int id;

    @Column(name = "name")
    public String name;

    public Genre() {
        super();
    }
}
