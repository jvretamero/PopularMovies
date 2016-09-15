package br.com.joaoretamero.popularmovies.domain.model;

public class ProductionCompany {

    private String name;

    public ProductionCompany(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ProductionCompany{" +
                "name='" + name + '\'' +
                '}';
    }
}
