package com.example.n00143569.asynctask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by n00143569 on 08/02/2017.
 */

public class HttpManager {
    public static String getData(String uri){
        BufferedReader reader = null;

        try{
            URL url = new URL(uri);

            //open a connection the that url
            HttpURLConnection con =(HttpURLConnection) url.openConnection();

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;
            //the reader will continue to read in from the input stream till everything is read in
            while ((line = reader.readLine()) != null){
                sb.append(line + "\n");
            }
            //convert sb to a string and return the lot the mainactivity for output to the ui
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        //finally is important to avoid leaks
        finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }
}
