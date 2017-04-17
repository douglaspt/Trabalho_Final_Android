package br.com.pch.livraria28scj;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.com.pch.livraria28scj.dao.UsuarioDAO;
import br.com.pch.livraria28scj.model.Usuario;

public class SplashScreen extends AppCompatActivity {

    //Tempo que nosso splashscreen ficará visivel
    private final int SPLASH_DISPLAY_LENGTH = 3500;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new AsyncFetch().execute("http://www.mocky.io/v2/58b9b1740f0000b614f09d2f");
        carregar();
    }

    private void carregar(){

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.animacao_splash);
        anim.reset();

        ImageView iv = (ImageView) findViewById(R.id.splash);

        if (iv != null){
            iv.clearAnimation();
            iv.startAnimation(anim);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                startActivity(intent);
                SplashScreen.this.finish();

            }
        }, SPLASH_DISPLAY_LENGTH);


    }

    private class AsyncFetch extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(SplashScreen.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            //pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return e.toString();
            }
            try {

                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                conn.setDoOutput(true);

            } catch (IOException e1) {
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Realiza a leitura dos dados do servidor
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Envia os dados para o onPostExecute
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            pdLoading.dismiss();
            try {
                UsuarioDAO usuarioDAO = new UsuarioDAO(SplashScreen.this);

                JSONObject json = new JSONObject(result);
                Usuario usuario = usuarioDAO.getUsuario(json.getString("usuario"));
                if(usuario == null){
                    usuario = new Usuario();
                    usuario.setUsuario(json.getString("usuario"));
                    usuario.setSenha(json.getString("senha"));

                    usuarioDAO.add(usuario);
                }


            } catch (JSONException e) {
                Toast.makeText(SplashScreen.this, e.toString(), Toast.LENGTH_LONG).show();
            }

        }

    }



}
