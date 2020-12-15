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
import android.widget.LinearLayout;
import android.widget.TextView;


import android.Manifest;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import myapplication.uk.ac.shef.oak.myapplication.MyAdapter;
import myapplication.uk.ac.shef.oak.myapplication.NewVisit;
import myapplication.uk.ac.shef.oak.myapplication.R;
import myapplication.uk.ac.shef.oak.myapplication.Visit;
import myapplication.uk.ac.shef.oak.myapplication.VisitAdapter;
import myapplication.uk.ac.shef.oak.myapplication.VisitElement;

public class VisitsFragment extends Fragment {

    private Activity activity;

    private VisitsViewModel visitsViewModel;


    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<VisitElement> visitsList = new ArrayList<>();

//    public void ImportFragment(Activity activity) {
//        this.activity = activity;
//    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        visitsViewModel =
                ViewModelProviders.of(this).get(VisitsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_visits, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        visitsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        mRecyclerView = (RecyclerView) root.findViewById(R.id.visits_recycler_view);

        // set up the RecyclerView
        int numberOfColumns = 1;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        initData();
        mAdapter = new VisitAdapter(visitsList);

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

    private void initData() {
        visitsList.add(new VisitElement("TEST VISIT A"));
        visitsList.add(new VisitElement("TEST VISIT B"));
    }
}