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

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;



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
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){

            String personId = acct.getId();
            saveID(personId);

        }

        googleButton = (SignInButton) findViewById(R.id.sign_in_button);
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                switch (view.getId()){
                    case R.id.sign_in_button:
                        singIn();
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

    private void singIn(){
        Intent signInIntent = GoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try{
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Intent intent = new Intent (LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }catch (ApiException e){
            Log.w("Error", "signInResult:failed code="+e.getStatusCode());
        }
    }

    private void saveID(String ID){

        SharedPreferences preferencias = getSharedPreferences
                ("ID usuario", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("ID",ID);
        editor.commit();

    }

}
