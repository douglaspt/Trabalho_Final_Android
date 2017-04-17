package br.com.pch.livraria28scj.model;

/**
 * Created by douglas.teixeira on 03/04/2017.
 */

public class Usuario {

    public final static String TAG_USUARIO = "usuario";
    public final static String TAG_SENHA = "senha";

    private int id;
    private String usuario;
    private String senha;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
