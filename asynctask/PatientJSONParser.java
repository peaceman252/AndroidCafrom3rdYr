package com.example.n00143569.asynctask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by N00143569 on 22/02/2017.
 */

public class PatientJSONParser {
    public static List<Patient> parseFeed(String content) {

            Patient patient = null;
            List<Patient> patientList = new ArrayList<>();

            if(content != null) {
                try {
                    JSONObject jsonObject = new JSONObject(content);
                    JSONArray patients = jsonObject.getJSONArray("patients");

                    for (int i = 0; i < patients.length(); i++) {
                        patient = new Patient();
                        patientList.add(patient);
                        JSONObject object = patients.getJSONObject(i);

                        int id = object.getInt("patientId");
                        String name = object.getString("patientName");
                        int phone = object.getInt("phone");
                        String meds = object.getString("medications");
                        String photo = object.getString("photo");

                        patient.setPatientId(id);
                        patient.setPatientName(name);
                        patient.setPhone(phone);
                        patient.setMedications(meds);
                        patient.setPhoto(photo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            return patientList;



    }
}
