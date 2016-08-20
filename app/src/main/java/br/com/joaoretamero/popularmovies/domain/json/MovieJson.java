package br.com.joaoretamero.popularmovies.domain.json;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject
public class MovieJson {

    @JsonField(name = "id")
    public int id;

    @JsonField(name = "poster_path")
    public String poster;

    @JsonField(name = "backdrop_path")
    public String backdrop;

    @JsonField(name = "original_title")
    public String title;

    @JsonField(name = "overview")
    public String overview;

    @JsonField(name = "runtime")
    public int runtime;

    @JsonField(name = "vote_average")
    public float voteAverage;

    @JsonField(name = "popularity")
    public float popularity;

    @JsonField(name = "production_companies")
    public List<ProductionCompanyJson> productionCompanies;

    @JsonField(name = "genres")
    public List<GenreJson> genres;

    @JsonField(name = "videos")
    public VideosJsonResponse videos;
}
