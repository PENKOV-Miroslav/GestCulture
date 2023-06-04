package com.example.gestculture.DAO;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.gestculture.Metier.Parcelle;
import com.example.gestculture.Metier.Visiter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DataToJSON extends AsyncTask<String, Void, String> {
    public static Context context;

    /**
     * Pour chaque parcelle, la méthode crée un objet JSONObject contenant les données de la parcelle et l'ajoute à un tableau de parcelles.
     * Si le tableau contient des parcelles, il est ajouté à l'objet JSON principal sous le nom "parcelles".
     * De même, pour chaque intervention, la méthode crée un objet JSONObject contenant les données de l'intervention et l'ajoute à un tableau d'interventions.
     * Si le tableau contient des interventions, il est ajouté à l'objet JSON principal sous le nom "interventions".
     * @param leContext
     * @return retourne la chaîne de caractères représentant l'objet JSON créé
     * @throws JSONException
     */
    public static String convertDataToJson(Context leContext) throws JSONException {
        context = leContext;
        JSONObject jsonObject = new JSONObject();
        ParcelleDAO parcelleDAO = new ParcelleDAO(context);
        parcelleDAO.open();
        List<Parcelle> parcellesList = parcelleDAO.getAllParcelles();
        parcelleDAO.close();

        // Ajouter les données des parcelles
        JSONArray parcellesArray = new JSONArray();
        for (Parcelle parcelle : parcellesList) {
            JSONObject parcelleObject = new JSONObject();
            parcelleObject.put("Annee", parcelle.getAnnee());
            parcelleObject.put("IdExploitation", parcelle.getIdExploitation());
            parcelleObject.put("IdParcelle", parcelle.getIdParcelle());
            parcelleObject.put("surface", parcelle.getSurface());
            parcelleObject.put("decoupage", parcelle.getDecoupage());
            parcelleObject.put("RendementPrevu", parcelle.getRendementPrevu());
            parcelleObject.put("RendementReel", parcelle.getRendementReel());
            parcelleObject.put("code", parcelle.getCode());
            parcellesArray.put(parcelleObject);
        }
        if (!parcellesArray.isNull(0)){
            jsonObject.put("parcelles", parcellesArray);
        }

        VisiterDAO visiterDAO = new VisiterDAO(context);
        visiterDAO.open();
        List<Visiter> visitersList = visiterDAO.FindAll();
        visiterDAO.close();

        // Ajouter les données des visites
        JSONArray visitersArray = new JSONArray();
        for (Visiter visiter : visitersList) {
            JSONObject visiteObject = new JSONObject();
            visiteObject.put("Matricule", visiter.getMatricule());
            visiteObject.put("IdExploitation", visiter.getIdExploitation());
            visiteObject.put("Annee", visiter.getAnnee());
            visitersArray.put(visiteObject);
        }

        if (!visitersArray.isNull(0)){
            jsonObject.put("visiter", visitersArray);
        }
        Log.d(TAG, jsonObject.toString());
        return jsonObject.toString();
    }

    /**
     * Permet d'envoyer une requête POST vers une URL et de récupérer la réponse du serveur.
     * Si la réponse indique que l'insertion des données dans la base de données a été effectuée avec succès,
     * alors la méthode supprime toutes les données des tables Parcelle et Intervenir dans la base de données locale.
     * Elle prend en paramètre une chaîne de caractères json qui représente les données à envoyer au serveur en format JSON.
     * @param json
     * @return retourne la réponse du serveur sous forme de chaîne de caractères
     * @throws IOException
     */
    public static String sendPostRequest(String json) throws IOException {
        HttpURLConnection conn = null;
        OutputStream os = null;
        BufferedReader br = null;
        String responseString = null;

        try {
            // Créer la connexion HTTP
            String url = "http://10.0.2.2:8888/GestCulture/script_local_to_BDD.php";
            URL obj = new URL(url);
            conn = (HttpURLConnection) obj.openConnection();
            conn.setDoOutput(true); // Pour pouvoir envoyer des données en POST
            conn.setRequestMethod("POST");

            // Envoyer les données
            OutputStream writer = conn.getOutputStream();
            writer.write(json.getBytes());
            writer.flush();

            // Récupérer la réponse du serveur
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output;
            StringBuilder sb = new StringBuilder();
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            responseString = sb.toString();
        } finally {
            // Fermer la connexion et les flux de lecture/écriture
            if (conn != null) {
                conn.disconnect();
            }
            if (os != null) {
                os.close();
            }
            if (br != null) {
                br.close();
            }
        }
        //Si insertion réussi, on vide les tables parcelle et visiter
        if (responseString.equals("{\"status\":\"ok\"}")) {
            Log.d(TAG, "BON");
            ParcelleDAO parcelleDAO = new ParcelleDAO(context);
            parcelleDAO.open();
            for (Parcelle parcelle:parcelleDAO.getAllParcelles()){
                parcelleDAO.deleteParcelle(parcelle);
            }
            parcelleDAO.close();

            VisiterDAO visiterDAO = new VisiterDAO(context);
            visiterDAO.open();
            for (Visiter visiter:visiterDAO.FindAll()){
                visiterDAO.deleteVisiter(visiter);
            }
            visiterDAO.close();
        } else {
            Log.d(TAG, "ERREUR");
        }

//        ParcelleDAO parcelleDAO = new ParcelleDAO(context);
//        parcelleDAO.open();
//        for (Parcelle parcelle:parcelleDAO.getAllParcelles()){
//            parcelleDAO.supprimerParcelle(parcelle);
//        }
//        parcelleDAO.close();
//
//        IntervenirDAO intervenirDAO = new IntervenirDAO(context);
//        intervenirDAO.open();
//        for (Intervenir intervenir:intervenirDAO.getAllInterventions()){
//            intervenirDAO.supprimerIntervention(intervenir);
//        }
//        intervenirDAO.close();

        return responseString;
    }

    /**
     * La méthode récupère la chaîne JSON passée en paramètre et essaie d'envoyer une requête HTTP POST contenant ces données en utilisant la méthode sendPostRequest.
     * Si l'envoi réussit, la méthode retourne la réponse du serveur sous forme d'une chaîne de caractères.
     * @param params
     * @return
     */
    @Override
    protected String doInBackground(String...params) {
        String json = params[0];
        try {
            return sendPostRequest(json);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

