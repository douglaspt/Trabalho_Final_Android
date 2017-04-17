package br.com.pch.livraria28scj;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import br.com.pch.livraria28scj.adapter.AndroidAdapter;
import br.com.pch.livraria28scj.dao.LivroDAO;
import br.com.pch.livraria28scj.listener.OnItemClickListener;
import br.com.pch.livraria28scj.model.Livro;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AndroidAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MenuActivity.this, NovoActivity.class),
                        NovoActivity.CODE_NOVO_LIVRO);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Carregar lista de Livros
        carregaLivros();
    }

    //Carregar Livros em uma lista
    private void carregaLivros(){

        LivroDAO livroDAO = new LivroDAO(this);
        StringBuilder sb = new StringBuilder();
        List<Livro> livros = livroDAO.getAll();

        setUpRecyclerView(livros);

    }

    private void setUpRecyclerView(List<Livro> data) {

        RecyclerView rcLista = (RecyclerView) findViewById(R.id.rcLista);
        mAdapter = new AndroidAdapter(MenuActivity.this, data);
        rcLista.setAdapter(mAdapter);
        rcLista.setLayoutManager(new LinearLayoutManager(MenuActivity.this));

        ImageButton ibDelete = (ImageButton) findViewById(R.id.ibDelete);

        mAdapter.setClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                final Livro livro = mAdapter.getItem(position);
                Intent i = new Intent(MenuActivity.this, AlteraActivity.class);
                String idLivro = Integer.toString(livro.getId());
                i.putExtra(Livro.TAG_ID, idLivro);
                i.putExtra(Livro.TAG_TITULO, livro.getTitulo());
                i.putExtra(Livro.TAG_AUTOR, livro.getAutor());
                String idCategoria = Integer.toString(livro.getCategoria().getId());
                i.putExtra(Livro.TAG_CATEGORIA_ID, idCategoria);

                startActivityForResult(i, AlteraActivity.CODE_ALTERA_LIVRO);
                //startActivity(i);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED) {
            Toast.makeText(MenuActivity.this, R.string.txt_cancelado,
                    Toast.LENGTH_LONG).show();
        } else {
            if(requestCode == NovoActivity.CODE_NOVO_LIVRO) {
                carregaLivros();
            } else {
                if (requestCode == AlteraActivity.CODE_ALTERA_LIVRO){
                    carregaLivros();
                }
            }
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SobreActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add) {
            startActivityForResult(new Intent(MenuActivity.this, NovoActivity.class),
                    NovoActivity.CODE_NOVO_LIVRO);

        } else if (id == R.id.nav_sobre){
            startActivity(new Intent(this, SobreActivity.class));

        } else if (id == R.id.nav_sair) {
            this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
