package br.com.joaoretamero.popularmovies.modelo;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Filme extends RealmObject {

    @PrimaryKey
    public int id;
    public String poster;
    public String backdrop;
    public String titulo;
    public String resumo;
    public String paginaOficial;
    public String idImdb;
}
