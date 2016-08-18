package br.com.joaoretamero.popularmovies.domain.json;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class ProductionCompanyJson {

    @JsonField(name = "id")
    public int id;

    @JsonField(name = "name")
    public String name;
}
