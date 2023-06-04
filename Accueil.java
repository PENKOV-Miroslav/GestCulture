package com.example.gestculture.Interfaces;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gestculture.DAO.DataToJSON;
import com.example.gestculture.DAO.ParcelleDAO;
import com.example.gestculture.DAO.VisiterDAO;
import com.example.gestculture.Metier.Parcelle;
import com.example.gestculture.Metier.Visiter;
import com.example.gestculture.R;
import org.json.JSONException;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import java.util.List;


public class Accueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        Intent thisIntent = getIntent();
        String matricule = thisIntent.getExtras().getString("matricule");
        TextView loginText = (TextView) findViewById(R.id.immatricule);
        String message = "Bonjour " + matricule;
        loginText.setText(message);

        Button buttonSaisieExploitation = (Button) findViewById(R.id.BtnInsertFiche);
        buttonSaisieExploitation.setOnClickListener(v -> {
            Intent SaisieExploitation = new Intent(Accueil.this, com.example.gestculture.Interfaces.SaisieExploitation.class);
            startActivity(SaisieExploitation);
        });

        Button buttonConsulterSaisie = (Button) findViewById(R.id.BtnSelectFiche);
        buttonConsulterSaisie.setOnClickListener(v -> {
            Intent ConsulterSaisie = new Intent(Accueil.this, com.example.gestculture.Interfaces.ConsulterSaisie.class);

            startActivity(ConsulterSaisie);
        });

        Button buttonModificationSaisie = (Button) findViewById(R.id.BtnUpdateFiche);
        buttonModificationSaisie.setOnClickListener(v -> {
            Intent ConsulterSaisie = new Intent(Accueil.this, ModificationSaisie.class);

            startActivity(ConsulterSaisie);
        });

        Button button3 = (Button) findViewById(R.id.BtnSupprFiche);
        button3.setOnClickListener(v -> {
            Intent ConsulterSaisie = new Intent(Accueil.this, SuppressionSaisie.class);

            startActivity(ConsulterSaisie);
        });

        Button button4 = (Button) findViewById(R.id.BtnUpload);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Créer un AlertDialog.Builder
                AlertDialog.Builder builder = new AlertDialog.Builder(Accueil.this);
                builder.setTitle("Confirmer l'envoi");
                builder.setMessage("Voulez-vous vraiment envoyer les données ?");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String json = null;
                        try {
                            json = DataToJSON.convertDataToJson(Accueil.this);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        // Si le JSON obtenu n'est pas vide, on exécute la méthode execute() de la classe DataToJSON en lui passant le JSON en tant que paramètre.
                        if (!json.equals("{}")){
                            new DataToJSON().execute(json);
                        }
                    }
                });
                builder.setNegativeButton("Non", null);

                // Afficher l'AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });


        Button button5 = (Button) findViewById(R.id.BtnDeconnexion);
        button5.setOnClickListener(v -> {
            // Efface l'historique des activités
            Intent deconnection = new Intent(Accueil.this, MainActivity.class);
            deconnection.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(deconnection);
        });

        Button button1 = (Button) findViewById(R.id.buttonInscrie);
        button1.setOnClickListener(v -> {
            Intent Inscription = new Intent(Accueil.this, InsertionTech.class);

            startActivity(Inscription);
        });


    }
}