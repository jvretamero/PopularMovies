package br.com.joaoretamero.popularmovies.infrastructure.network.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject
public class MovieJsonResponse {

    @JsonField(name = "results")
    public List<MovieJson> results;
}
