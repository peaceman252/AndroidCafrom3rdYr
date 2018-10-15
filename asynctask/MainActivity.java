package com.example.n00143569.asynctask;

import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {
    private static final int PROGRESS = 0x1;
    private static final String PHOTOS_BASE_URL = "http://10.0.2.2/hanselandpetal/photos/";

    private ProgressBar mProgress;
    private int mProgressStatus = 0;
    List<Patient> patientList;
    List<MyTask> tasks;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        mProgress = (ProgressBar) findViewById(R.id.progress_bar);
        //mProgress.setVisibility(View.VISIBLE);
        tasks = new ArrayList<>();
        requestData("http://10.0.2.2/hanselandpetal/patients.json");

    }


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }
    public void getData(View v){
        requestData("http://10.0.2.2/hanselandpetal/patients.json");
    }

    private void requestData(String uri) {
        MyTask task = new MyTask();
        task.execute(uri);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            MyTask task = new MyTask();
            task.execute("http://10.0.2.2/hanselandpetal/patients.json");
            //task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"Param 1", "Param 2", "Param 3");
            return true;
        }



        return super.onOptionsItemSelected(item);
    }
    protected void updateDisplay(List<Patient> message){
        TextView output = (TextView) findViewById(R.id.output);
        //output.append(message+"\n");
        if (patientList != null){
            for (Patient patient: patientList){
                //output.append(patient.getPatientName()+"\n");
            }
        }
        PatientAdapter adapter = new PatientAdapter(this, R.layout.item_patient,patientList);
        setListAdapter(adapter);
    }

    private class MyTask extends AsyncTask<String, String, List<Patient>> {
        @Override
        protected List<Patient> doInBackground(String... params) {
           /* for(int i =0; i<params.length;i++){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String data = HttpManager.getData(params[0]);
                publishProgress(data);
                //publishProgress("Working with " + params[i]);
            }*/
            String content = HttpManager.getData(params[0]);
            patientList = PatientJSONParser.parseFeed(content);
            //patientList = PatientXMLParser.parseFeed(content);
            for (Patient patient : patientList){
                try{
                    String imageUrl = PHOTOS_BASE_URL + patient.getPhoto();
                    InputStream in = (InputStream) new URL(imageUrl).getContent();
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    patient.setBitmap(bitmap);
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();}

            }
            return patientList;
            //return content;
            //return "Task complete";
        }

        @Override
        protected void onPreExecute(){
            if (tasks.size()==0){
                mProgress.setVisibility(View.VISIBLE);
            }
            tasks.add(this);
            //updateDisplay("Starting task");
        }
        @Override
        protected void onPostExecute(List<Patient> result){
            //patientList = PatientXMLParser.parseFeed(result);

            updateDisplay(result);
            tasks.remove(this);
            if (tasks.size()==0){
                mProgress.setVisibility(View.INVISIBLE);
            }
        }
        @Override
        protected void onProgressUpdate(String... values){
            TextView output = (TextView) findViewById(R.id.output);
            for(int i =0; i<values.length;i++) {
                output.append(values[i] + "\n");
            }
        }

    }


}
