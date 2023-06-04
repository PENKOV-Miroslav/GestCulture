package com.example.gestculture.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gestculture.Metier.Technicien;
import com.example.gestculture.SQLHelper.SQLiteHelper;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;



public class TechnicienDAO {
    private SQLiteDatabase db;
    private SQLiteHelper dbHelper;

    public TechnicienDAO(Context context) {
        dbHelper = SQLiteHelper.getInstance(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void openAuthentification() {
        db = dbHelper.getReadableDatabase();
    }

    public void closeAuthentification() {
        dbHelper.close();
    }

    public void ajouterTechnicien(Technicien technicien) {
        ContentValues values = new ContentValues();
        values.put("Matricule", technicien.getMatricule());
        values.put("Mdp", technicien.getMdp());
        values.put("Nom", technicien.getNom());
        values.put("Prenom", technicien.getPrenom());
        db.insert("Technicien", null, values);
    }

    public void modifierTechnicien(Technicien technicien) {
        ContentValues values = new ContentValues();
        values.put("Nom", technicien.getNom());
        values.put("Pernom", technicien.getPrenom());
        db.update("Technicien", values, "Matricule" + " = ?",
                new String[] { String.valueOf(technicien.getMatricule()) });
    }

    public void supprimerTechnicien(Technicien technicien) {
        db.delete("Technicien", "Matricule" + " = ?",
                new String[] { String.valueOf(technicien.getMatricule()) });
    }

    public Technicien getTechnicienParMatricule(String matricule) {
        Cursor cursor = db.query("Technicien", new String[] { "Matricule",
                        "Nom", "Prenom" }, "Matricule" + "=?",
                new String[] { String.valueOf(matricule) }, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Technicien technicien = new Technicien((cursor.getString(0)), cursor.getString(1),
                    cursor.getString(2),cursor.getString(3),cursor.getInt(4));
            return technicien;
        } else {
            // Gérer ici la situation où le technicien n'existe pas
            return null;
        }
    }

    public List<Technicien> getAllTechniciens() {
        List<Technicien> techniciens = new ArrayList<Technicien>();
        String selectQuery = "SELECT  * FROM " + "Technicien";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Technicien technicien = new Technicien(cursor.getString(0), cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getInt(4));
                techniciens.add(technicien);
            } while (cursor.moveToNext());
        }
        return techniciens;
    }



    private static final int BCRYPT_ROUNDS = 10;
    public void insertUtilisateur(Technicien technicien) {

        ContentValues values = new ContentValues();
        values.put("Matricule", technicien.getMatricule());

        // Hacher le mot de passe avec Bcrypt
        String hashedPassword = BCrypt.hashpw(technicien.getMdp(), BCrypt.gensalt(BCRYPT_ROUNDS));
        values.put("mdp", hashedPassword);

        values.put("Nom", technicien.getNom());
        values.put("Prenom", technicien.getPrenom());
        values.put("idrole", technicien.getIdrole());

        db.insert("Technicien", null, values);
        db.close();
    }



    public boolean checkLogin(String matriculeStr, String mdpStr) {

        // Récupérer l'utilisateur correspondant au matricule
        Cursor cursor = db.rawQuery("SELECT * FROM Technicien WHERE matricule = ?", new String[] { matriculeStr });
        if (cursor != null && cursor.moveToFirst()) {
            String hashedPassword = cursor.getString(1);
            cursor.close();

            // Vérifier que le mot de passe haché correspond au mot de passe fourni
            return BCrypt.checkpw(mdpStr, hashedPassword);
        }

        return false;
    }





}




