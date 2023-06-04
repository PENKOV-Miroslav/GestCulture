package com.example.gestculture.Interfaces;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gestculture.DAO.TechnicienDAO;
import com.example.gestculture.R;
import com.example.gestculture.SQLHelper.SQLiteHelper;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteHelper databaseHelper = new SQLiteHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        databaseHelper.onCreate(db);

        EditText matricule = (EditText) findViewById(R.id.editTextTextPersonName);
        EditText mdp = (EditText) findViewById(R.id.editTextTextMDP);
        //pattern RegEX permettant de verifier que le matricule respect le format XX-000-XX
        //et que le mot de passe et compris entre 8 et 12 caractères
        Pattern matriculeRegex = Pattern.compile("[A-Z]{2}-\\d{3}-[A-Z]{2}");
        Pattern passwordRegex = Pattern.compile("^.{8,12}$");


        Button button = (Button) findViewById(R.id.bt_connexion);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String matriculeStr = matricule.getText().toString();
                String mdpStr = mdp.getText().toString();


                if (TextUtils.isEmpty(matriculeStr) || TextUtils.isEmpty(mdpStr)) {
                    Toast.makeText(MainActivity.this, "Veuillez renseigner tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (!matriculeRegex.matcher(matriculeStr).matches()) {
                    Toast.makeText(MainActivity.this, "Le format du matricule est invalide", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!passwordRegex.matcher(mdpStr).matches()) {
                    Toast.makeText(MainActivity.this, "Le mot de passe doit avoir entre 8 et 12 caractères", Toast.LENGTH_SHORT).show();
                    return;
                }


                TechnicienDAO technicienDAO = new TechnicienDAO(MainActivity.this);
                technicienDAO.openAuthentification();
                boolean isLoginValid = technicienDAO.checkLogin(matriculeStr, mdpStr);
                if (isLoginValid == true) {
                    // Connexion réussie, faire quelque chose ici...
                    Intent intent  = new Intent(getApplicationContext(), Accueil.class);
                    intent.putExtra("matricule", matriculeStr);
                    startActivity(intent);
                } else {
                    // Connexion échouée, informer l'utilisateur...
                    Toast.makeText(MainActivity.this, "Login ou Mdp incorrect", Toast.LENGTH_SHORT).show();
                }

                    technicienDAO.closeAuthentification();



            }
        });




    }

}