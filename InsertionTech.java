package com.example.gestculture.Interfaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gestculture.DAO.TechnicienDAO;
import com.example.gestculture.Metier.Technicien;
import com.example.gestculture.R;

import java.util.regex.Pattern;

public class InsertionTech extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertion_tech);


        Button buttonBack = (Button) findViewById(R.id.buttonRetourInscription);
        buttonBack.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });

        Button buttonSignIn = (Button) findViewById(R.id.buttonInscription);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            EditText matricule = (EditText) findViewById(R.id.editTextTextMatricule);
            EditText mdp = (EditText) findViewById(R.id.editTextTextPassword);
            EditText nom = (EditText) findViewById(R.id.editTextTextNom);
            EditText prenom = (EditText) findViewById(R.id.editTextTextPrenom);
            EditText idrole = (EditText) findViewById(R.id.editTextTextIdRole);

            //pattern RegEX permettant de verifier que le matricule respect le format XX-000-XX
            //et que le mot de passe et compris entre 8 et 12 caractères
            Pattern matriculeRegex = Pattern.compile("[A-Z]{2}-\\d{3}-[A-Z]{2}");
            Pattern passwordRegex = Pattern.compile("^.{8,12}$");

            public void onClick(View v) {

                String matriculeStr = matricule.getText().toString();
                String mdpStr = mdp.getText().toString();
                String nomStr = nom.getText().toString();
                String prenomStr = prenom.getText().toString();
                int idroleStr = Integer.parseInt(idrole.getText().toString());
                String resultIdRoleStr= String.valueOf(idroleStr);

                if (TextUtils.isEmpty(matriculeStr) || TextUtils.isEmpty(mdpStr) || TextUtils.isEmpty(nomStr) || TextUtils.isEmpty(prenomStr) || resultIdRoleStr.equals("")) {
                    Toast.makeText(InsertionTech.this, "Veuillez renseigner tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (!matriculeRegex.matcher(matriculeStr).matches()) {
                    Toast.makeText(InsertionTech.this, "Le format du matricule est invalide", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!passwordRegex.matcher(mdpStr).matches()) {
                    Toast.makeText(InsertionTech.this, "Le mot de passe doit avoir entre 8 et 12 caractères", Toast.LENGTH_SHORT).show();
                    return;
                }




                    Technicien technicien = new Technicien(matriculeStr, mdpStr, nomStr, prenomStr, idroleStr);
                    TechnicienDAO technicienDAO = new TechnicienDAO(InsertionTech.this);
                    technicienDAO.open();
                    technicienDAO.insertUtilisateur(technicien);
                    technicienDAO.close();
                    matricule.setText("");
                    mdp.setText("");
                    nom.setText("");
                    prenom.setText("");
                    idrole.setText("");
                    Toast.makeText(InsertionTech.this, "Vous etes inscrit", Toast.LENGTH_SHORT).show();

            }
        });



    }
}