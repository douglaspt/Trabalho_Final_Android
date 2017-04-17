package br.com.pch.livraria28scj;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import br.com.pch.livraria28scj.dao.UsuarioDAO;
import br.com.pch.livraria28scj.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private final String LOGIN_DEFAULT = "fiap";
    private final String SENHA_DEFAULT = "123";

    private final String KEY_APP_PREFERENCES = "login";
    private final String KEY_LOGIN = "login";

    private TextInputLayout tilLogin;
    private TextInputLayout tilSenha;
    private CheckBox cbManterConectado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tilLogin = (TextInputLayout) findViewById(R.id.tilLogin);
        tilSenha = (TextInputLayout) findViewById(R.id.tilSenha);
        cbManterConectado = (CheckBox) findViewById(R.id.cbManterConectado);

        if(isConectado())
            iniciarAPP();
    }

    public void logar(View view){
        if(isLoginValido()){
            if(cbManterConectado.isChecked()){
                manterConectado();
            }
            iniciarAPP();
        } else {
            Toast.makeText(LoginActivity.this, "Login Inválido!", Toast.LENGTH_LONG).show();
        }

    }

    private boolean isLoginValido(){
        String login = tilLogin.getEditText().getText().toString();
        String senha = tilSenha.getEditText().getText().toString();

        UsuarioDAO usuarioDAO = new UsuarioDAO(this);
        Usuario usuario = usuarioDAO.getUsuario(login);
        //Usuario usuario = usuarioDAO.getBy(1);
        if(usuario != null){
            if(login.equals(usuario.getUsuario()) && senha.equals(usuario.getSenha())){
                return true;
            } else {
                return false;
            }
        } else return false;


    }

    private void manterConectado(){
        String login = tilLogin.getEditText().getText().toString();
        SharedPreferences pref = getSharedPreferences(KEY_APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_LOGIN, login);
        editor.apply();
    }

    private boolean isConectado(){
        SharedPreferences shared = getSharedPreferences(KEY_APP_PREFERENCES, MODE_PRIVATE);
        String login = shared.getString(KEY_LOGIN,"");
        if(login.equals("")){
            return false;
        } else {
            return true;
        }
    }

    private void iniciarAPP(){
        startActivity(new Intent(this, MenuActivity.class));
        this.finish();
    }
}
