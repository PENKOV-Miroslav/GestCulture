package com.example.gestculture.Interfaces;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gestculture.DAO.ParcelleDAO;
import com.example.gestculture.Metier.Parcelle;
import com.example.gestculture.R;

public class ModificationSaisie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification_saisie);

        Button buttonBack = (Button) findViewById(R.id.btnRetoutModif);
        Button buttonValideModif = (Button) findViewById(R.id.btnValideModif);

        buttonBack.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });


        EditText idExploitation = (EditText) findViewById(R.id.editTextTextExploitationID);
        EditText idParcelle = (EditText) findViewById(R.id.editTextTextParcelleID);
        EditText surface = (EditText) findViewById(R.id.editTextTextSurface);
        EditText decoupage = (EditText) findViewById(R.id.editTextTextDecoupage);
        EditText rend_prev = (EditText) findViewById(R.id.editTextTextRendPrevu);
        EditText rend_real = (EditText) findViewById(R.id.editTextTextRendReel);
        EditText code = (EditText) findViewById(R.id.editTextTextCodeEsp);

        buttonValideModif.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                int code_exploiStr = Integer.parseInt(idExploitation.getText().toString());
                int code_parcelleStr = Integer.parseInt(idParcelle.getText().toString());
                String surfaceStr = surface.getText().toString();
                String decoupageStr = decoupage.getText().toString();
                String rend_prevStr = rend_prev.getText().toString();
                String rend_realStr = rend_real.getText().toString();
                int codeStr = Integer.parseInt(code.getText().toString());

                try {
                    Parcelle parcelle = new Parcelle(null ,code_exploiStr, code_parcelleStr, surfaceStr, decoupageStr, rend_prevStr, rend_realStr, codeStr);
                    ParcelleDAO parcelleDAO = new ParcelleDAO(ModificationSaisie.this);
                    parcelleDAO.open();
                    parcelleDAO.updateParcelle(parcelle);
                    parcelleDAO.close();
                } catch (Exception e) {
                    // Gérer l'exception si elle se produit
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Une erreur s'est produite lors de l'insertion dans parcelle", Toast.LENGTH_SHORT).show();
                }
                idExploitation.setText("");
                idParcelle.setText("");
                surface.setText("");
                decoupage.setText("");
                rend_prev.setText("");
                rend_real.setText("");
                code.setText("");
                Toast.makeText(getApplicationContext(), "Modification prise en compte !", Toast.LENGTH_SHORT).show();


            }
        });
    }
}