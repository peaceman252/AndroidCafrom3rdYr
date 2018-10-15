package com.example.n00143569.asynctask;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by N00143569 on 22/02/2017.
 */

public class PatientAdapter extends ArrayAdapter<Patient>{
    private Context context;
    private List<Patient> patientList;

    public PatientAdapter(Context context, int resource, List<Patient> objects) {
        super(context, resource, objects);
        this.context = context;
        this.patientList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_patient, parent, false);

        Patient patient = patientList.get(position);
        TextView tv = (TextView) view.findViewById(R.id.textView1);
        tv.setText(patient.getPatientName());

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView1);
        imageView.setImageBitmap(patient.getBitmap());

        return view;
    }
}
