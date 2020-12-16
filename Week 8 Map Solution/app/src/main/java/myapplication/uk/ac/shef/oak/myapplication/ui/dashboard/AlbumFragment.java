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

import myapplication.uk.ac.shef.oak.myapplication.Image;
import myapplication.uk.ac.shef.oak.myapplication.MyAdapter;
import myapplication.uk.ac.shef.oak.myapplication.MyViewModel;

import myapplication.uk.ac.shef.oak.myapplication.R;


public class AlbumFragment extends Fragment {

    private AlbumViewModel albumViewModel;

    private List<Image> myPictureList = new ArrayList<>();
    private RecyclerView.Adapter  mAdapter;
    private MyAdapter photoAdapter;
    private RecyclerView mRecyclerView;

    private Activity activity;

    LiveData<Image> stringToDisplay;
    private MyViewModel myViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        albumViewModel =
                ViewModelProviders.of(this).get(AlbumViewModel.class);
        View root = inflater.inflate(R.layout.fragment_album, container, false);
//        final TextView textView = root.findViewById(R.id.text_album);
//        albumViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        // Get a new or existing ViewModel from the ViewModelProvider.
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        myViewModel.getImages().observe(getViewLifecycleOwner(), new Observer<List<Image>>(){

            @Override
            public void onChanged(@Nullable final List<Image> images) {
                photoAdapter.setPhotos(images);
            }
        });

        mRecyclerView = (RecyclerView) root.findViewById(R.id.grid_recycler_view);

        // set up the RecyclerView
        int numberOfColumns = 4;
        mRecyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), numberOfColumns));
//        mAdapter = new MyAdapter(myPictureList);
        photoAdapter = new MyAdapter(myPictureList);
        mRecyclerView.setAdapter(photoAdapter);



        return root;
    }
}