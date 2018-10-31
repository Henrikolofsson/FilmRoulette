package henrik.mau.p3;

import android.support.v4.app.Fragment;

import Fragments.DataFragment;
import Fragments.StartFragment;

public class Controller {
    private MainActivity mainActivity;
    private DataFragment dataFragment;
    private StartFragment startFragment;

    public Controller(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        initializeDataFragment();
        initializeFragments();
    }

    private void initializeDataFragment() {
        dataFragment = (DataFragment) mainActivity.getFragment("DataFragment");
        if(dataFragment == null){
            dataFragment = new DataFragment();
            mainActivity.addFragment(dataFragment, "DataFragment");
            dataFragment.setActiveFragment("StartFragment");
        }
        dataFragment.setController(this);
    }

    private void initializeFragments(){
        initializeStartFragment();
    }

    private void initializeStartFragment(){
        startFragment = (StartFragment) mainActivity.getFragment("StartFragment");
        if(startFragment == null){
            startFragment = new StartFragment();
        }
        startFragment.setController(this);
        addStartFragment();
    }

    private void setFragment(String tag){
        switch(tag){
            case "StartFragment":
                setFragment(startFragment, "StartFragment");
                break;
        }
    }

    private void setFragment(Fragment fragment, String tag){
        mainActivity.setFragment(fragment, tag);
        dataFragment.setActiveFragment(tag);
    }

    public void addStartFragment(){
        setFragment("StartFragment");
    }


}
