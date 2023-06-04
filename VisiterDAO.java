package com.example.gestculture.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.gestculture.Metier.Parcelle;
import com.example.gestculture.SQLHelper.SQLiteHelper;
import com.example.gestculture.Metier.Visiter;

import java.util.ArrayList;
import java.util.List;

public class VisiterDAO {

    private SQLiteDatabase db;
    private final SQLiteHelper dbHelper;

    public VisiterDAO(Context context) {

        dbHelper = SQLiteHelper.getInstance(context);
    }

    public void open() {

        db = dbHelper.getWritableDatabase();
    }

    public void close() {

        dbHelper.close();
    }

    public void ajouterVisite(Visiter visiter) {
        ContentValues values = new ContentValues();
        values.put("Matricule", visiter.getMatricule());
        values.put("IdExploitation", visiter.getIdExploitation());
        values.put("annee", visiter.getAnnee());
        db.insert("Visiter", null, values);
    }

    public void updateVisite(Visiter visiter){
        String sql = "UPDATE Visiter SET IdExploitation = ?, Annee = ? WHERE Matricule = ?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.bindString(1, String.valueOf(visiter.getIdExploitation()));
        statement.bindString(2, String.valueOf(visiter.getAnnee()));
        statement.bindString(3, String.valueOf(visiter.getMatricule()));
        statement.execute();
    }

    public void deleteVisiter(Visiter visiter) {
        String sql = "DELETE FROM Visiter WHERE matricule = ?";
        SQLiteStatement statement = db.compileStatement(sql);

        try {
            //statement.bindLong(1, Long.parseLong(visiter.getMatricule()));
            statement.bindString(1, visiter.getMatricule());
            statement.executeUpdateDelete();
        } finally {
            statement.close();
            db.close();
        }
    }

    // méthode pour obtenir toutes les visites
    public List<Visiter> FindAll() {
        List<Visiter> visiters = new ArrayList<>();
        String selectQuery = "SELECT * FROM Visiter";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Visiter visiter = new Visiter(cursor.getString(0), cursor.getInt(1), cursor.getString(2));
                visiters.add(visiter);
            } while (cursor.moveToNext());
        } else {
            Log.d("ParcelleDAO", "No results found");
        }
        cursor.close();
        Log.d("ParcelleDAO", "getAllParcelles() result count: " + visiters.size());
        return visiters;
    }




}
