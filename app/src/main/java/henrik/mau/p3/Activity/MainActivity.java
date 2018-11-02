package henrik.mau.p3.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import henrik.mau.p3.Controller.Controller;
import henrik.mau.p3.R;


public class MainActivity extends AppCompatActivity {
    private Controller controller;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeSystem();
    }

    private void initializeSystem() {
        fragmentManager = getSupportFragmentManager();
        controller = new Controller(this);
    }

    public void setFragment(Fragment fragment, String tag){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainactivity_container, fragment, tag);
        fragmentTransaction.commit();
    }

    public void addFragment(Fragment fragment, String tag){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragment, tag);
        fragmentTransaction.commit();
    }


    public Fragment getFragment(String tag){

        return fragmentManager.findFragmentByTag(tag);
    }

    @Override
    public void onBackPressed() {
        if (controller.backPressed()) {
            super.onBackPressed();
        }
    }

}
