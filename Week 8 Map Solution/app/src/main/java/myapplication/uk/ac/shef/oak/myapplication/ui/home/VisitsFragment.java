package myapplication.uk.ac.shef.oak.myapplication.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import myapplication.uk.ac.shef.oak.myapplication.NewVisit;
import myapplication.uk.ac.shef.oak.myapplication.R;

public class VisitsFragment extends Fragment {

    private Activity activity;

    private VisitsViewModel visitsViewModel;

//    public void ImportFragment(Activity activity) {
//        this.activity = activity;
//    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        visitsViewModel =
                ViewModelProviders.of(this).get(VisitsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_visits, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        visitsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    /**
     * Override onCreateOptionsMenu to add an add icon to the top menu bar
     * */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.topmenuvisits, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}