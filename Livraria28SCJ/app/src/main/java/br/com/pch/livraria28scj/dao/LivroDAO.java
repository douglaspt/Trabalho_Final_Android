package br.com.pch.livraria28scj.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import br.com.pch.livraria28scj.model.Categoria;
import br.com.pch.livraria28scj.model.Livro;


/**
 * Created by douglas.teixeira on 13/03/2017.
 */

public class LivroDAO {

    private SQLiteDatabase db;
    private DBOpenHelper banco;


    public LivroDAO(Context context) {
        banco = new DBOpenHelper(context);
    }

    private static final String TABELA_LIVRO = "livro";
    private static final String COLUNA_ID = "id";
    private static final String COLUNA_TITULO = "titulo";
    private static final String COLUNA_AUTOR= "autor";
    private static final String COLUNA_CATEGORIA_ID = "categoria_id";

    public String add(Livro livro){
        long resultado;
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_TITULO, livro.getTitulo());
        values.put(COLUNA_AUTOR, livro.getAutor());
        values.put(COLUNA_CATEGORIA_ID, livro.getCategoria().getId());

        resultado = db.insert(TABELA_LIVRO, null, values);
        db.close();
        if(resultado == -1) {
            return "Erro ao inserir registro";
        } else {
            return "Registro inserido com sucesso";
        }
    }

    public String remove(Livro livro){
        long resultado;
        String where = "id = "+livro.getId();
        SQLiteDatabase db = banco.getWritableDatabase();
        resultado = db.delete(TABELA_LIVRO, where, null);
        if(resultado == -1) {
            return "Erro ao Exclui registro";
        } else {
            return "Registro Excluido com sucesso";
        }
    }


    public Livro getBy(int id) {
        SQLiteDatabase db = banco.getReadableDatabase();
        String colunas[] = { COLUNA_ID, COLUNA_TITULO, COLUNA_AUTOR, COLUNA_CATEGORIA_ID };
        String where = "id = " + id;
        Cursor cursor = db.query(true, TABELA_LIVRO, colunas, where, null, null,
                null, null, null);
        Livro livro = null;
        if(cursor != null)
        {
            cursor.moveToFirst();
            livro = new Livro();
            livro.setTitulo(cursor.getString(cursor.getColumnIndex(COLUNA_TITULO)));
            livro.setAutor(cursor.getString(cursor.getColumnIndex(COLUNA_AUTOR)));
            livro.setId(cursor.getInt(cursor.getColumnIndex(COLUNA_ID)));

            //Categoria categoria = categoriaDAO.getBy(cursor.getInt(cursor.getColumnIndex(COLUNA_CATEGORIA_ID)));
           // livro.setCategoria(categoria);
        }
        return livro;
    }

    public int update(Livro livro) {
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(COLUNA_ID, livro.getId());
        values.put(COLUNA_TITULO, livro.getTitulo());
        values.put(COLUNA_AUTOR, livro.getAutor());
        values.put(COLUNA_CATEGORIA_ID, livro.getCategoria().getId());
        return db.update(TABELA_LIVRO,values, COLUNA_ID +" = "+livro.getId(),null);
    }

    public List<Livro> getAll() {

        List<Livro> livros = new LinkedList<>();
        String rawQuery = "SELECT l.*, c.nome FROM " +
                LivroDAO.TABELA_LIVRO + " l INNER JOIN "+
                CategoriaDAO.TABELA_CATEGORIA + " c ON l."+LivroDAO.COLUNA_CATEGORIA_ID + " = c."+
                CategoriaDAO.COLUNA_ID +
                " ORDER BY " + LivroDAO.COLUNA_ID + " ASC";
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery(rawQuery, null);

        Livro livro = null;

        if (cursor.moveToFirst()) {
            do {
                livro = new Livro();
                livro.setId(cursor.getInt(0));
                livro.setTitulo(cursor.getString(2));
                livro.setAutor(cursor.getString(3));
                livro.setCategoria(new Categoria(cursor.getInt(1), cursor.getString(4)));
                livros.add(livro);

            }  while (cursor.moveToNext());

        }
        return livros;

    }


}
