package br.com.joaoretamero.popularmovies.modelo;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Video extends RealmObject {

    @PrimaryKey
    public String id;
    public String name;
    public String youtubeId;
}
