package com.example.n00143569.asynctask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by N00143569 on 22/02/2017.
 */

public class PatientXMLParser {
    public static List<Patient> parseFeed(String content){
        try {
            boolean inDataItemTag = false;
            String currentTagName = "";
            Patient patient = null;
            List<Patient> patientList = new ArrayList<>();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(content));

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        currentTagName=parser.getName();

                        if (currentTagName.equals("patient")){
                            inDataItemTag = true;
                            patient = new Patient();
                            patientList.add(patient);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("patient")){
                            inDataItemTag = false;
                        }
                        currentTagName = "";
                        break;

                    case XmlPullParser.TEXT:
                        if (inDataItemTag && patient != null){
                            switch (currentTagName){
                                case "patientId":
                                    patient.setPatientId(Integer.parseInt(parser.getText()));
                                    break;
                                case "patientName":
                                    patient.setPatientName(parser.getText());
                                    break;
                                case "phone":
                                    patient.setPhone(Integer.parseInt(parser.getText()));
                                    break;
                                case "medications":
                                    patient.setMedications(parser.getText());
                                    break;
                                case "photo":
                                    patient.setPhoto(parser.getText());
                                    break;
                            }
                        }
                        break;
                }
                eventType = parser.next();
            }
            return patientList;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
