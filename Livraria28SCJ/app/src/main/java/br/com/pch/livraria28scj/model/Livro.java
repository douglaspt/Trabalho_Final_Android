package br.com.pch.livraria28scj.model;

/**
 * Created by douglas.teixeira on 03/04/2017.
 */

public class Livro {

    public final static String TAG_LIVRO = "livro";
    public final static String TAG_ID = "id";
    public final static String TAG_TITULO = "titulo";
    public final static String TAG_AUTOR = "autor";
    public final static String TAG_CATEGORIA_ID = "categoria_id";

    private int id;
    private String titulo;
    private String autor;
    private Categoria categoria;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
