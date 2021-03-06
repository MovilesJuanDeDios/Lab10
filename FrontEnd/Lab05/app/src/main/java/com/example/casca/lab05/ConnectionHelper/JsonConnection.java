package com.example.casca.lab05.ConnectionHelper;

import android.os.AsyncTask;
import android.util.Log;

import com.example.casca.lab05.Model.Product;
import com.example.casca.lab05.Model.Usuario;
import com.example.casca.lab05.Utils.Data;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by casca on 12/05/2018.
 */

public class JsonConnection extends AsyncTask<String, String, List<Product>> {
    // doInBackground llama a los otros 2
    @Override
    protected List<Product> doInBackground(String... params) {
        URL url;
        HttpURLConnection urlConnection = null;
        List<Product> productos= new ArrayList<>();
        boolean get=false;
        try {
            if (params[1].equals("POST")) {
                Log.v("Haciendo POST","EN LA BASE");
                url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                Log.v("Haciendo POST",params[0]);
                int responseCode = urlConnection.getResponseCode();

                if(responseCode == HttpURLConnection.HTTP_OK){
                    Log.v("CatalogClient-Response", "GUARDADO EN LA BASE");
                }
            }
            else{
                if(params[1].equals("USER")){
                    url = new URL(params[0]);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");

                    int responseCode = urlConnection.getResponseCode();
                    String responseMessage = urlConnection.getResponseMessage();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        String responseString = readStream(urlConnection.getInputStream());
                        Log.v("CatalogClient-Response", responseString);
                        get = parseUsuarioData(responseString);
                    } else {
                        Log.v("CatalogClient", "Response code:" + responseCode);
                        Log.v("CatalogClient", "Response message:" + responseMessage);
                    }
                }else {
                    url = new URL(params[0]);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");

                    int responseCode = urlConnection.getResponseCode();
                    String responseMessage = urlConnection.getResponseMessage();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        String responseString = readStream(urlConnection.getInputStream());
                        Log.v("CatalogClient-Response", responseString);
                        get = parseProductoData(responseString);
                    } else {
                        Log.v("CatalogClient", "Response code:" + responseCode);
                        Log.v("CatalogClient", "Response message:" + responseMessage);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }

        return productos;
    }
    /* --------------------------------------------------------------- */
    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }
    /* --------------------------------------------------------------- */
    private boolean parseUsuarioData(String jString){

        try {
            JSONArray items = new JSONArray(jString);
            if(items != null) {
                for (int i = 0; i < items.length(); i++) {
                    String username=items.getJSONObject(i).getString("username");
                    String nombre=items.getJSONObject(i).getString("nombre");
                    String email=items.getJSONObject(i).getString("email");
                    String clave=items.getJSONObject(i).getString("clave");
                    int rol=items.getJSONObject(i).getInt("rol");

                    //the value of progress is a placeholder here....
                    Usuario usuario = new Usuario(username,nombre,email,clave,rol);
                    Data.listaUsuarios.add(usuario);
                }
                return true;
            }

            // }

        } catch (JSONException e) {
            Log.e("CatalogClient", "unexpected JSON exception", e);
        }

        return false;
    }
    /* --------------------------------------------------------------- */
    private boolean parseProductoData(String jString){
        try {
                JSONArray items = new JSONArray(jString);
                if(items != null) {
                    for (int i = 0; i < items.length(); i++) {
                        int codigo = items.getJSONObject(i).getInt("id");
                        String nombreProducto = items.getJSONObject(i).getString("title");
                        String descProducto = items.getJSONObject(i).getString("shortdesc");
                        int cantidad = items.getJSONObject(i).getInt("cantidad");
                        int precio = items.getJSONObject(i).getInt("price");
                        int image = items.getJSONObject(i).getInt("image");

                        //the value of progress is a placeholder here....
                        Product producto = new Product(codigo,nombreProducto,descProducto,cantidad,precio,image);
                        Data.listaProductos.add(producto);
                    }
                }

           // }

        } catch (JSONException e) {
            Log.e("CatalogClient", "unexpected JSON exception", e);
        }

        return false;
    }
    /* --------------------------------------------------------------- */
    protected void onPostExecute(List<Product> productos) {
        super.onPostExecute(productos);
        //make use of data..
    }

}
