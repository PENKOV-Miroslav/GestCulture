package com.example.gestculture.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.gestculture.Metier.Parcelle;
import com.example.gestculture.Metier.Visiter;
import com.example.gestculture.SQLHelper.SQLiteHelper;
import java.util.ArrayList;
import java.util.List;

public class ParcelleDAO {

    private SQLiteDatabase db;
    private final SQLiteHelper dbHelper;

    public ParcelleDAO(Context context) {

        dbHelper = SQLiteHelper.getInstance(context);
    }

    public void open() {

        db = dbHelper.getWritableDatabase();
    }

    public void close() {

        dbHelper.close();
    }
    // méthode pour ajouter une parcelle
    public void ajouterParcelle(Parcelle parcelle) {
        String sql = "INSERT INTO `PARCELLE`(annee, idExploitation, idParcelle, surface, decoupage, rendementReel, rendementPrevu, code) VALUES (?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);

        try {
            statement.bindString(1, parcelle.getAnnee());
            statement.bindLong(2, parcelle.getIdExploitation());
            statement.bindLong(3, parcelle.getIdParcelle());
            statement.bindString(4, parcelle.getSurface());
            statement.bindString(5, parcelle.getDecoupage());
            statement.bindString(6, parcelle.getRendementReel());
            statement.bindString(7, parcelle.getRendementPrevu());
            statement.bindLong(8, parcelle.getCode());
            statement.executeInsert();
        } finally {
            statement.close();
            db.close();
        }
    }


    //methode d'update des parcelles
    public void updateParcelle(Parcelle parcelle) {
        String sql = "UPDATE `PARCELLE` SET surface = ?, decoupage = ?, rendementReel = ?, rendementPrevu = ?, code = ? WHERE IdExploitation = ? AND idParcelle = ?";
        SQLiteStatement statement = db.compileStatement(sql);

        try {
            statement.bindString(1, parcelle.getSurface());
            statement.bindString(2, parcelle.getDecoupage());
            statement.bindString(3, parcelle.getRendementReel());
            statement.bindString(4, parcelle.getRendementPrevu());
            statement.bindLong(5, parcelle.getCode());
            statement.bindLong(6, parcelle.getIdExploitation());
            statement.bindLong(7, parcelle.getIdParcelle());
            statement.executeUpdateDelete();
        } finally {
            statement.close();
            db.close();
        }
    }



// méthode pour supprimer une parcelle
    public void deleteParcelle(Parcelle parcelle) {
        String sql = "DELETE FROM PARCELLE WHERE IdParcelle = ?";
        SQLiteStatement statement = db.compileStatement(sql);

        try {
            statement.bindLong(1, parcelle.getIdParcelle());
            statement.executeUpdateDelete();
        } finally {
            statement.close();
            db.close();
        }
    }


    // méthode pour obtenir toutes les parcelles
    public List<Parcelle> getAllParcelles() {
        List<Parcelle> parcelles = new ArrayList<>();
        String selectQuery = "SELECT * FROM Parcelle";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Parcelle parcelle = new Parcelle(cursor.getString(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),cursor.getInt(7));
                parcelles.add(parcelle);
            } while (cursor.moveToNext());
        } else {
            Log.d("ParcelleDAO", "No results found");
        }
        cursor.close();
        Log.d("ParcelleDAO", "getAllParcelles() result count: " + parcelles.size());
        return parcelles;
    }




        }
