package br.com.pch.livraria28scj.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.pch.livraria28scj.model.Usuario;

/**
 * Created by douglas.teixeira on 04/04/2017.
 */

public class UsuarioDAO {

    private DBOpenHelper banco;

    public UsuarioDAO(Context context){
        banco = new DBOpenHelper(context);
    }

    public static final String TABELA_USUARIO = "usuario";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_USU = "usuario";
    public static final String COLUNA_SENHA = "senha";


    public Usuario getUsuario(String login) {
        SQLiteDatabase db = banco.getReadableDatabase();
        String colunas[] = { COLUNA_ID, COLUNA_USU, COLUNA_SENHA};
        String where = " usuario = '" +login+"'" ;

        Cursor cursor = db.query(true, TABELA_USUARIO, colunas, where, null, null,
                null, null, null);
        Usuario usuario = null;
        if((cursor != null) && (cursor.getCount() > 0))
        {
            cursor.moveToFirst();
            usuario = new Usuario();
            usuario.setUsuario(cursor.getString(cursor.getColumnIndex(COLUNA_USU)));
            usuario.setSenha(cursor.getString(cursor.getColumnIndex(COLUNA_SENHA)));
            usuario.setId(cursor.getInt(cursor.getColumnIndex(COLUNA_ID)));
        }
        return usuario;
    }

    public Usuario getBy(int id) {
        SQLiteDatabase db = banco.getReadableDatabase();
        String colunas[] = { COLUNA_ID, COLUNA_USU, COLUNA_SENHA};
        String where = "id = " + id;
        Cursor cursor = db.query(true, TABELA_USUARIO, colunas, where, null, null,
                null, null, null);
        Usuario usuario = null;
        if(cursor != null)
        {
            cursor.moveToFirst();
            usuario = new Usuario();
            usuario.setUsuario(cursor.getString(cursor.getColumnIndex(COLUNA_USU)));
            usuario.setSenha(cursor.getString(cursor.getColumnIndex(COLUNA_SENHA)));
            usuario.setId(cursor.getInt(cursor.getColumnIndex(COLUNA_ID)));
        }
        return usuario;
    }

    public String add(Usuario usuario){
        long resultado;
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("usuario", usuario.getUsuario());
        values.put("senha", usuario.getSenha());

        resultado = db.insert("usuario", null, values);
        db.close();
        if(resultado == -1) {
            return "Erro ao inserir registro";
        } else {
            return "Registro inserido com sucesso";
        }
    }

}
