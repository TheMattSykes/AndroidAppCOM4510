package myapplication.uk.ac.shef.oak.myapplication.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HelpViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HelpViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Press the + button in the title bar on the visits page to start a new visit. " +
                "Click on either of the yellow floating buttons to add a new photo to that visit and view them on the album page.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}