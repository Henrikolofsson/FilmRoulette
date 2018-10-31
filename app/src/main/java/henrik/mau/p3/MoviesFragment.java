package henrik.mau.p3;


import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MoviesFragment extends Fragment {
    private static GridView gridview;
    public static int width;
    private Controller controller;

    public MoviesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        setWindow();

        initComponents(view);


        return view;
    }

    private void initComponents(View view) {
        if (getActivity() != null) {
            ArrayList<String> array = new ArrayList<String>();
            ImageAdapter adapter = new ImageAdapter(getActivity(), array, width);
            gridview = (GridView) view.findViewById(R.id.gridView);

            gridview.setColumnWidth(width);
            gridview.setAdapter(adapter);
        }
        //listen for presses on gridview items
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);
                controller.showMovieDetails(position);
            }
        });
    }

    private void setWindow() {
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x / 3;
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle("Most Popular Movies");

        if (controller.isNetworkAvailable()) {
            controller.startImageLoadTask();
            gridview.setVisibility(GridView.VISIBLE);

        } else {
            TextView textview1 = new TextView(getActivity());
            RelativeLayout layout1 = (RelativeLayout) getActivity().findViewById(R.id.relativelayout);
            textview1.setText("You are not connected to the Internet");
            if (layout1.getChildCount() == 1) {
                layout1.addView(textview1);
            }
            gridview.setVisibility(GridView.GONE);
        }
    }


    public void setContent(ArrayList<String> result) {
        ImageAdapter adapter = new ImageAdapter(getActivity(), result, width);
        gridview.setAdapter(adapter);

    }

    public void setController(Controller controller) {
        this.controller = controller;

    }
}
