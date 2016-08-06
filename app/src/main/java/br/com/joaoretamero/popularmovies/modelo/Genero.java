package br.com.joaoretamero.popularmovies.modelo;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Genero extends RealmObject {

    @PrimaryKey
    public int id;
    public String nome;
}
