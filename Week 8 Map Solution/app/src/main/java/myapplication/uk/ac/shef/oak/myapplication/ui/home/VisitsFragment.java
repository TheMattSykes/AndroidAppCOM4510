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

import myapplication.uk.ac.shef.oak.myapplication.AlbumViewModel;
//import myapplication.uk.ac.shef.oak.myapplication.MyAdapter;
import myapplication.uk.ac.shef.oak.myapplication.NewVisit;
import myapplication.uk.ac.shef.oak.myapplication.R;
import myapplication.uk.ac.shef.oak.myapplication.VisitAdapter;
import myapplication.uk.ac.shef.oak.myapplication.model.ImageData;
import myapplication.uk.ac.shef.oak.myapplication.model.VisitData;

/**
 * VisitsFragment
 * Used to display a list of visits the user has created as well as a button to create a new visit.
 */
public class VisitsFragment extends Fragment {

    private Activity activity;

    private VisitsViewModel visitsViewModel;


    private VisitAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<VisitData> visitsList = new ArrayList<>();

//    public void ImportFragment(Activity activity) {
//        this.activity = activity;
//    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        visitsViewModel =
                ViewModelProviders.of(this).get(VisitsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_visits, container, false);

        visitsViewModel = new ViewModelProvider(this).get(VisitsViewModel.class);

        visitsViewModel.getVisits().observe(getViewLifecycleOwner(), new Observer<List<VisitData>>(){

            @Override
            public void onChanged(List<VisitData> visitsData) {
                mAdapter.setVisits(visitsData);
            }

        });

        mRecyclerView = (RecyclerView) root.findViewById(R.id.visits_recycler_view);
        initData();
        // set up the RecyclerView
        int numberOfColumns = 1;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        mAdapter = new VisitAdapter(visitsList);
        mRecyclerView.setAdapter(mAdapter);



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

    /**
     * initData
     * Add list of seed temporary data to the list.
     */
    private void initData() {
//        visitsList.add(new VisitData("TEST VISIT A", null));
//        visitsList.add(new VisitData("TEST VISIT B", null));
    }
}