package myapplication.uk.ac.shef.oak.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import myapplication.uk.ac.shef.oak.myapplication.activities.MapsActivity;
import myapplication.uk.ac.shef.oak.myapplication.model.VisitData;

/**
 * Activity to create a new visit with a title text input and button.
 */
public class NewVisit extends AppCompatActivity {

    private static AppCompatActivity activity;

    private NewVisitViewModel visitViewModel;

    public static AppCompatActivity getActivity() {
        return activity;
    }

    /**
     * Set current activity
     * @param activity
     */
    public static void setActivity(AppCompatActivity activity) {
        NewVisit.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_visit);
        setActivity(this);

        visitViewModel = new ViewModelProvider(this).get(NewVisitViewModel.class);

        final EditText titleEditText = (EditText) findViewById(R.id.title_input);
        final Button startButton = (Button) findViewById(R.id.startButton);
        titleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    startButton.setEnabled(true);
                } else {
                    startButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleText = titleEditText.getText().toString();

                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyy-mm-dd hh:mm:ss");
                String dateString = dateFormat.format(date);

                VisitData visitData = new VisitData(titleText, dateString);

                visitViewModel.insert(visitData);

                if (titleText.length() > 0) {
                    // Switching over to the Maps activity and finishing this one
                    Intent intent = new Intent(view.getContext(), MapsActivity.class);
                    view.getContext().startActivity(intent);
                    finish();
                }
            }
        });
    }

}
