package myapplication.uk.ac.shef.oak.myapplication.ui.dashboard;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import myapplication.uk.ac.shef.oak.myapplication.AlbumAdapter;
import myapplication.uk.ac.shef.oak.myapplication.AlbumViewModel;
import myapplication.uk.ac.shef.oak.myapplication.Image;
import myapplication.uk.ac.shef.oak.myapplication.MyAdapter;
import myapplication.uk.ac.shef.oak.myapplication.MyViewModel;

import myapplication.uk.ac.shef.oak.myapplication.R;
import myapplication.uk.ac.shef.oak.myapplication.model.ImageData;

/**
 * AlbumFragment
 * Lists the pictures taken across all visits in a gallery view.
 */
public class AlbumFragment extends Fragment {

    private List<ImageData> myImageList = new ArrayList<>();
    private RecyclerView.Adapter  mAdapter;
    private AlbumAdapter imageAdapter;
    private RecyclerView mRecyclerView;
    private Activity activity;
    LiveData<ImageData> stringToDisplay;
    private AlbumViewModel albumViewModel;

    /**
     * Initialiser: Set up recycler view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_album, container, false);

        // Get a new or existing ViewModel from the ViewModelProvider.
        albumViewModel = new ViewModelProvider(this).get(AlbumViewModel.class);

        albumViewModel.getImages().observe(getViewLifecycleOwner(), new Observer<List<ImageData>>(){

            @Override
            public void onChanged(List<ImageData> imageData) {
                imageAdapter.setImages(imageData);
            }

        });

        mRecyclerView = (RecyclerView) root.findViewById(R.id.grid_recycler_view);

        // set up the RecyclerView
        int numberOfColumns = 4;
        mRecyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), numberOfColumns));
//        mAdapter = new MyAdapter(myPictureList);
        imageAdapter = new AlbumAdapter(myImageList);
        mRecyclerView.setAdapter(imageAdapter);

        return root;
    }
}