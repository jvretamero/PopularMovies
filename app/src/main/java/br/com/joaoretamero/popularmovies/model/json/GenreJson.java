package br.com.joaoretamero.popularmovies.model.json;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class GenreJson {

    @JsonField(name = "id")
    public int id;

    @JsonField(name = "name")
    public String name;
}
