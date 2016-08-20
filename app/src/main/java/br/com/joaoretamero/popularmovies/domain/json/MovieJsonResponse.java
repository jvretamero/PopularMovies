package br.com.joaoretamero.popularmovies.domain.json;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject
public class MovieJsonResponse {

    @JsonField(name = "results")
    public List<MovieJson> results;
}
