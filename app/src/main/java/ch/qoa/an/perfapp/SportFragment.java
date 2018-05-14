package ch.qoa.an.perfapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SportFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SportFragment extends Fragment {
    View myView;
    private ImageView sportImage;
    GraphView bargraph;
    Integer setSport;

    public static ArrayList<SportItem> SessionList;

    public static final int COLOR_ABDO = Color.rgb(241, 3, 138);
    public static final int COLOR_DORSEAU = Color.rgb(148, 16, 231);
    public static final int COLOR_CORDE = Color.rgb(61, 33, 233);
    public static final int COLOR_SQUAT = Color.rgb(7, 200, 227);

    private OnFragmentInteractionListener mListener;

    public SportFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        if(myView == null){
            myView = inflater.inflate(R.layout.fragment_abdo_time, container, false);
        }

        GraphView graph = myView.findViewById(R.id.bargraph);
        graph.removeAllSeries();

        Date[] date = new Date[SessionList.size()];
        DataPoint[] values = new DataPoint[SessionList.size()];

        Integer i=0;

        //Fill the data for the barChart with le SessionList
        for (SportItem session: SessionList) {
            date[i] = new Date(session.getYear(), session.getMonth(), session.getDay());
            if(MainActivity.Time_nRep)
            {
                values[i] = new DataPoint(date[i], session.getTime());
            }
            else
            {
                values[i] = new DataPoint(date[i], session.getRep());
            }

            i++;
        }

        BarGraphSeries<DataPoint> series=new BarGraphSeries<>(values);

        graph.addSeries(series);

        // Set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));

        //Set some parameters for the grid labels
        graph.getGridLabelRenderer().setTextSize(25);
        //graph.getGridLabelRenderer().setNumVerticalLabels(10); //ESSAI
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(90);
        graph.getGridLabelRenderer().setLabelsSpace(10);

        //Change the vertical Axis Title in accordance to the printed session
        //Minutes if the time are selected and Repetition if the rep option is selected
        if(MainActivity.Time_nRep)
        {
            graph.getGridLabelRenderer().setVerticalAxisTitle(getString(R.string.ModeTime));
        }
        else
        {
            graph.getGridLabelRenderer().setVerticalAxisTitle(getString(R.string.ModeRep));
        }

        //Set the maximum and minimum value for the X axis
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(series.getLowestValueX()-200000000);
        graph.getViewport().setMaxX(series.getHighestValueX()+200000000);

        //Set the maximum and minimum value for the Y axis and adjust automatically Y axis
        graph.getViewport().setYAxisBoundsManual(false);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(series.getHighestValueY()+10);

        // Set the option to scale the X axis and allow to scroll
        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);

        // Set the color of the boarder
        graph.getViewport().setBorderColor(Color.BLACK);
        graph.getViewport().setDrawBorder(false);


        // as we use dates as labels, the human rounding to nice readable numbers
        // is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);

        //Set the corresponding sport image
        sportImage=myView.findViewById(R.id.imageSport);
        switch(setSport) {
            case 0:
                series.setColor(COLOR_ABDO);
                sportImage.setImageResource(R.drawable.abdos);
                break;
            case 1:
                series.setColor(COLOR_DORSEAU);
                sportImage.setImageResource(R.drawable.dorsaux);
                break;
            case 2:
                series.setColor(COLOR_CORDE);
                sportImage.setImageResource(R.drawable.corde_sauter);
                break;
            case 3:
                series.setColor(COLOR_SQUAT);
                sportImage.setImageResource(R.drawable.squats);
                break;
            default:
                //Do Something
        }

        //Set the space between to date
        series.setSpacing(10); //10

        //Draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.DKGRAY);
        series.setValuesOnTopSize(20);

        return myView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title

        switch(setSport) {
            case 0:
                getActivity().setTitle(getString(R.string.TitleAbdo));
                break;
            case 1:
                getActivity().setTitle(getString(R.string.TitleDorsaux));
                break;
            case 2:
                getActivity().setTitle(getString(R.string.TitleCorde));
                break;
            case 3:
                getActivity().setTitle(getString(R.string.TitleSquat));
                break;
            default:
                //Do Something
        }
    }

    public void setSport(Integer sport) {
        setSport = sport;
    }

    public void setSessionList(ArrayList<SportItem> sessionList) {
        this.SessionList = sessionList;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onAbdoTimeFragmentInteraction(Integer uri);
    }
}
