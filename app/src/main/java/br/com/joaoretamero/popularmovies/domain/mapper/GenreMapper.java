package br.com.joaoretamero.popularmovies.domain.mapper;

import java.util.ArrayList;
import java.util.List;

import br.com.joaoretamero.popularmovies.domain.json.GenreJson;
import br.com.joaoretamero.popularmovies.domain.local.Genre;

public class GenreMapper {

    public List<Genre> mapJsonListToLocalList(List<GenreJson> genreJsonList) {
        List<Genre> genreList = new ArrayList<Genre>(genreJsonList.size());

        for (GenreJson genreJson : genreJsonList) {
            genreList.add(mapJsonToLocal(genreJson));
        }

        return genreList;
    }

    public Genre mapJsonToLocal(GenreJson genreJson) {
        Genre genre = new Genre();
        genre.name = genreJson.name;

        return genre;
    }
}
