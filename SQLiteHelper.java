package com.example.gestculture.SQLHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static SQLiteHelper instance;

    private static final String DB_NAME = "GestCultures.db";
    private static final int DB_VERSION = 1;

    private Context mContext;

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;

        // Vérifiez si la base de données existe déjà dans l'emplacement de votre application
        if (!checkDatabase()) {
            // Si la base de données n'existe pas, copiez-la depuis le dossier assets
            try {
                copyDatabase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /** Utilisée pour obtenir une instance unique de la classe. Si l'instance n'a pas encore été créée, la méthode crée une nouvelle instance de la classe. */
    public static synchronized SQLiteHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SQLiteHelper(context.getApplicationContext());
        }
        return instance;
    }

    private boolean checkDatabase() {
        SQLiteDatabase db = null;
        try {
            String path = mContext.getDatabasePath(DB_NAME).getPath();
            db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            // la base de données n'existe pas
        }
        if (db != null) {
            db.close();
        }
        return db != null;
    }

    private void copyDatabase() throws IOException {
        InputStream inputStream = mContext.getAssets().open(DB_NAME);
        OutputStream outputStream = new FileOutputStream(mContext.getDatabasePath(DB_NAME));

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Créez les tables de la base de données ici
        // ...
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Mettez à jour la structure de la base de données ici si nécessaire
        // ...
    }
}
