package com.example.gestculture.Interfaces;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gestculture.DAO.ParcelleDAO;
import com.example.gestculture.Metier.Parcelle;
import com.example.gestculture.R;

public class SuppressionSaisie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppression_saisie);

        Button buttonBack = (Button) findViewById(R.id.btnRetourSuppr);

        buttonBack.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });

        Button buttonValider = (Button) findViewById(R.id.btnValideSuppr);
        EditText idParcelle = (EditText) findViewById(R.id.editTextIdParcelleSuppr);
        buttonValider.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                int code_parcelleStr = Integer.parseInt(idParcelle.getText().toString());


                    try {
                        Parcelle parcelle = new Parcelle(null, 0, code_parcelleStr, null, null, null, null, 0);
                        ParcelleDAO parcelleDAO = new ParcelleDAO(SuppressionSaisie.this);
                        parcelleDAO.open();
                        parcelleDAO.deleteParcelle(parcelle);
                        parcelleDAO.close();
                        idParcelle.setText("");
                        Toast.makeText(getApplicationContext(), "La fiche parcelle est supprimer", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        // Gérer l'exception si elle se produit
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Une erreur s'est produite lors de la suppression dans parcelle", Toast.LENGTH_SHORT).show();
                    }


            }

        });


    }

}