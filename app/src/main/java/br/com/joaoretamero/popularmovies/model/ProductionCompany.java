package br.com.joaoretamero.popularmovies.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "production_company", id = "id")
public class ProductionCompany extends Model {

    @Column(name = "id")
    public int id;

    @Column(name = "name")
    public String name;

    public ProductionCompany() {
        super();
    }
}
