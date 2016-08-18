package br.com.joaoretamero.popularmovies.domain.json;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class VideoJson {

    @JsonField(name = "id")
    public String id;

    @JsonField(name = "key")
    public String key;

    @JsonField(name = "name")
    public String name;
}
