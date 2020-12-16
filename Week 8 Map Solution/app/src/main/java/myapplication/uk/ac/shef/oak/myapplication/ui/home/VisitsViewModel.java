package myapplication.uk.ac.shef.oak.myapplication.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import myapplication.uk.ac.shef.oak.myapplication.MainRepository;
import myapplication.uk.ac.shef.oak.myapplication.model.VisitData;

public class VisitsViewModel extends AndroidViewModel {
    private final MainRepository mainRepository;

    LiveData<List<VisitData>> visits;

    public VisitsViewModel(Application application) {
        super(application);

        mainRepository = new MainRepository(application);
        visits = mainRepository.getVisits();
    }

    /**
     * getter for the live data
     * @return an updated list of images
     */
    public LiveData<List<VisitData>> getVisits() {
        if (visits == null) {
            visits = new MutableLiveData<List<VisitData>>();
        }
        return visits;
    }
}