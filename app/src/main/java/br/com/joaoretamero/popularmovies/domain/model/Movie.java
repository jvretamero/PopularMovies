package br.com.joaoretamero.popularmovies.domain.model;

import java.util.List;

public class Movie {

    private int id;

    private String poster;

    private String backdrop;

    private String title;

    private String overview;

    private int durationInMinutes;

    private float voteAverage;

    private float popularity;

    private List<Genre> genres;

    private List<Video> videos;

    private List<ProductionCompany> productionCompanies;

    public Movie(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", poster='" + poster + '\'' +
                ", backdrop='" + backdrop + '\'' +
                ", title='" + title + '\'' +
                ", overview='" + overview + '\'' +
                ", durationInMinutes=" + durationInMinutes +
                ", voteAverage=" + voteAverage +
                ", popularity=" + popularity +
                '}';
    }
}
