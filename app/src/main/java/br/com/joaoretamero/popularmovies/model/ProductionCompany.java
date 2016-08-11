package br.com.joaoretamero.popularmovies.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "production_company", id = "_id")
public class ProductionCompany extends Model {

    @Column(name = "name")
    public String name;

    public ProductionCompany() {
        super();
    }
}
