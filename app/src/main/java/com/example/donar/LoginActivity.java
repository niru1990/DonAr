package com.example.donar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import DonArDato.ResponseData;
import DonArDato.actualizaIG;
import Service.LoginService;
import Negocio.Login;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {

    //TextView txtID;

    private String idGoogle;

    FirebaseAuth.AuthStateListener mAuthListener;
    @SuppressLint("WrongViewCast")
    SignInButton googleButton;
    int RC_SIGN_IN = 0;
    GoogleSignInClient GoogleSignInClient;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //txtID = findViewById(R.id.txtID);


        googleButton = (SignInButton) findViewById(R.id.sign_in_button);
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                switch (view.getId()){
                    case R.id.sign_in_button:
                        signIn();
                        break;
                }
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("749769909165-eu682b4ak518svj464drdevub29ak00j.apps.googleusercontent.com")
                .requestEmail()
                .build();
        GoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn(){
        Intent signInIntent = GoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

            if(acct!=null){

                idGoogle = acct.getId();
                Log.i("Mensaje",idGoogle);
                //saveID(personId);

            }
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try{
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            checkBD();
/*
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
*/
        }catch (ApiException e){
            Log.w("Error", "signInResult:failed code="+e.getStatusCode());
        }
    }

    private void signOut() {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("749769909165-eu682b4ak518svj464drdevub29ak00j.apps.googleusercontent.com")
                .build();

        GoogleSignInClient googleSignInClient=GoogleSignIn.getClient(this,gso);
        googleSignInClient.signOut();
        finish();
        startActivity(getIntent());
    }

    private void saveID(String ID){

        SharedPreferences preferencias = getSharedPreferences
                ("ID usuario", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("ID",ID);
        editor.commit();

    }

    private void saveTipo(String tipo){

        SharedPreferences preferencias = getSharedPreferences
                ("ID usuario", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("tipo",tipo);
        editor.commit();
    }

    private String getEmail(){
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        String personEmail="";
        if (acct != null) {
            personEmail = acct.getEmail();
        }
        return personEmail;
    }

    private String getId(){
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        String personId="";
        if (acct != null) {
            personId = acct.getId();
        }
        return personId;
    }
    public void actualizaID() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://donar.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final LoginService lg = retrofit.create(LoginService.class);
        actualizaIG aig = new actualizaIG(getEmail(), getId());
        Call<Login> http_call = lg.updateData(aig);
        http_call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Login login = response.body();
                if (response.body() != null) {
                    if (login.getInicio() == 1) {
                        Log.i("Tag_sesion", "IdUpdate Correcto " + getEmail() + " | " + getId());
                    } else {
                        Log.i("Tag_sesion", "No se pudo actualizar el idGoogle");
                    }
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {

            }
        });
    }

    public void checkBD() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://donar.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final LoginService lg = retrofit.create(LoginService.class);
        Call<ResponseData> http_call = lg.checkCorreo(getEmail());

        http_call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                Intent intent;
                //Integer idUsuario =response.body();
                ResponseData res = (ResponseData) response.body();
                if(response.body() != null) {
                    if (res.getIdUser() != 0 ){
                        actualizaID();
                        //ACA GUARDO el ID del USUARIO.
                        saveID(res.getIdUser()+"");
                        saveTipo(res.getTipoUser()+"");
                        Log.i("Tag_sesion", "Inicio correcto // Email: " + getEmail() + " | IdGoogle: " + getId()+" | IdUser: " +res.getIdUser());
                        intent = new Intent(LoginActivity.this, MainActivity.class);
                    }
                    else {
                        Toast.makeText(LoginActivity.this
                                , "El Correo no existe en nuestra base de datos, es necesario que se registre."
                                , Toast.LENGTH_LONG).show();
                        signOut();
                        intent = new Intent(LoginActivity.this, LoginActivity.class);
                        Log.i("Tag_sesion", "No se pudo iniciar sesion");
                    }
                    startActivity(intent);
                }
                else
                {
                    if(response.code() == 204) {
                        Toast.makeText(getApplicationContext(),
                                "No se encontro un usuario registrado con ese mail",
                                Toast.LENGTH_LONG).show();
                        signOut();
                    }
                    else
                    Log.e("errorResposne", "NO ME MANDO LO QUE QUERIA LA API");
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                signOut();
                Intent intent = new Intent (LoginActivity.this, LoginActivity.class);
                startActivity(intent);
                Log.i("Tag_sesion", "No se pudo iniciar sesion |"+t.getMessage()+" | "+call);
            }
        });
    }

}