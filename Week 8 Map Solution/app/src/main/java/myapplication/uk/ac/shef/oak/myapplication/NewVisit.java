package myapplication.uk.ac.shef.oak.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class NewVisit extends AppCompatActivity {

    private static AppCompatActivity activity;

    public static AppCompatActivity getActivity() {
        return activity;
    }

    public static void setActivity(AppCompatActivity activity) {
        NewVisit.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_visit);
        setActivity(this);
    }

}
