package com.example.donar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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

import DonArDato.actualizaIG;
import Negocio.Login;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {

    //TextView txtID;

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

                String personId = acct.getId();
                Log.i("Mensaje",personId);
                saveID(personId);

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
                        Log.i("Tag_sesion", "IdUpdate Correcto correcto " + getEmail() + " | " + getId());
                    } else {
                        Log.i("Tag_sesion", "Ya se encuentra actualizado");
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
        Call<Login> http_call = lg.checkCorreo(getEmail());
        http_call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Intent intent;
                Login login = response.body();
                if(response.body() != null) {
                    if (login.getInicio() == 1){
                            actualizaID();
                            Log.i("Tag_sesion", "Inicio correcto " + getEmail() + " | " + getId());
                             intent = new Intent(LoginActivity.this, MainActivity.class);
                    }else {
                        signOut();
                        intent = new Intent(LoginActivity.this, LoginActivity.class);
                        Log.i("Tag_sesion", "No se pudo iniciar sesion");
                    }
                    startActivity(intent);
                }
                else
                {
                    Log.e("errorResposne", "NO ME MANDO LO QUE QUERIA LA API");
                }
            }
            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                signOut();
                Intent intent = new Intent (LoginActivity.this, LoginActivity.class);
                startActivity(intent);
                Log.i("Tag_sesion", "No se pudo iniciar sesion |"+t.getMessage()+" | "+call);
            }
        });
    }

}