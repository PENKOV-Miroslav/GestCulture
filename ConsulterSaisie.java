package com.example.gestculture.Interfaces;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gestculture.DAO.ParcelleDAO;
import com.example.gestculture.Metier.Parcelle;
import com.example.gestculture.R;

import java.util.Arrays;
import java.util.List;

public class ConsulterSaisie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_saisie);

        Button buttonBack = (Button) findViewById(R.id.btnRetourConsulter);
        Button buttonSelect = (Button) findViewById(R.id.buttonSelect);

        buttonBack.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });

        TableLayout tableLayout = findViewById(R.id.tableLayout);
        ScrollView scrollView = findViewById(R.id.scrollView);

        buttonSelect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ParcelleDAO parcelleDAO = new ParcelleDAO(ConsulterSaisie.this);
                parcelleDAO.open();
                List<Parcelle> parcelles = parcelleDAO.getAllParcelles();
                parcelleDAO.close();

                int numColumns = 8; // Define numColumns here
                String[][] data = new String[parcelles.size() + 1][numColumns]; // Define data here
                data[0][0] = "Année";
                data[0][1] = "IdExploitation";
                data[0][2] = "IdParcelle";
                data[0][3] = "Surface";
                data[0][4] = "Découpage";
                data[0][5] = "Rendement Prévu";
                data[0][6] = "Rendement Réel";
                data[0][7] = "Code";

                for (int i = 0; i < parcelles.size(); i++) {
                    Parcelle parcelle = parcelles.get(i);
                    data[i + 1][0] = parcelle.getAnnee();
                    data[i + 1][1] = String.valueOf(parcelle.getIdExploitation());
                    data[i + 1][2] = String.valueOf(parcelle.getIdParcelle());
                    data[i + 1][3] = String.valueOf(parcelle.getSurface());
                    data[i + 1][4] = parcelle.getDecoupage();
                    data[i + 1][5] = String.valueOf(parcelle.getRendementPrevu());
                    data[i + 1][6] = String.valueOf(parcelle.getRendementReel());
                    data[i + 1][7] = String.valueOf(parcelle.getCode());
                }

                // Define the separator and the column width
                String separator = " | ";
                int[] columnWidths = {350, 350, 350, 350, 350, 440, 400, 350};

                // Build the header row
                TableRow headerRow = new TableRow(ConsulterSaisie.this);
                for (int i = 0; i < numColumns; i++) {
                    TextView textView = new TextView(ConsulterSaisie.this);
                    textView.setText(data[0][i]);
                    textView.setWidth(columnWidths[i]);
                    textView.setTextColor(Color.BLACK);
                    textView.setBackgroundColor(Color.BLUE);
                    textView.setGravity(Gravity.CENTER);
                    textView.setPadding(10, 10, 10, 10);
                    textView.setBackgroundResource(R.drawable.cell_shape);
                    headerRow.addView(textView);
                }
                tableLayout.addView(headerRow);

                // Build the data rows
                for (int i = 1; i < data.length; i++) {
                    TableRow dataRow = new TableRow(ConsulterSaisie.this);
                    for (int j = 0; j < numColumns; j++) {
                        TextView textView = new TextView(ConsulterSaisie.this);
                        textView.setText(data[i][j]);
                        textView.setWidth(columnWidths[j]);
                        textView.setGravity(Gravity.CENTER);
                        textView.setPadding(10, 10, 10, 10);
                        textView.setBackgroundResource(R.drawable.cell_shape);
                        dataRow.addView(textView);
                    }
                    tableLayout.addView(dataRow);
                }

                // Add the TableLayout to the ScrollView
                scrollView.removeAllViews();
                scrollView.addView(tableLayout);
            }
        });

    }
}