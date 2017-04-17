package br.com.pch.livraria28scj;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

import br.com.pch.livraria28scj.dao.CategoriaDAO;
import br.com.pch.livraria28scj.dao.LivroDAO;
import br.com.pch.livraria28scj.model.Categoria;
import br.com.pch.livraria28scj.model.Livro;

public class NovoActivity extends AppCompatActivity {

    public final static int CODE_NOVO_LIVRO = 1002;
    private TextInputLayout tilTitulo;
    private TextInputLayout tilAutor;
    private Spinner spCategoria;
    private List<Categoria> categorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo);

        tilTitulo = (TextInputLayout) findViewById(R.id.tilTitulo);
        tilAutor = (TextInputLayout) findViewById(R.id.tilAutor);
        spCategoria = (Spinner) findViewById(R.id.spCategoria);

        CategoriaDAO categoriaDAO = new CategoriaDAO(this);
        categorias = categoriaDAO.getAll();

        ArrayAdapter<Categoria> adapter = new ArrayAdapter<Categoria>(getApplicationContext(),
                R.layout.categoria_spinner_item, categorias);
        adapter.setDropDownViewResource(R.layout.categoria_spinner_item);
        spCategoria.setAdapter(adapter);
    }

    public void cadastrar(View v) {
        LivroDAO livroDAO = new LivroDAO(this);
        Livro livro = new Livro();
        livro.setTitulo(tilTitulo.getEditText().getText().toString());
        livro.setAutor(tilAutor.getEditText().getText().toString());
        livro.setCategoria((Categoria) spCategoria.getSelectedItem());
        livroDAO.add(livro);

        retornaParaTelaAnterior();
    }

    //retorna para tela de lista
    public void retornaParaTelaAnterior() {
        Intent intentMessage=new Intent();
        setResult(CODE_NOVO_LIVRO, intentMessage);
        finish();
    }
}
