package ch.qoa.an.perfapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private Button goRecap;
    private ImageView sportImage;
    private Button goLogin;
    private TextView textSport;
    //GraphView graph;
    GraphView bargraph;
    Integer setSport;
    String TAG = "TestApp";

    public static ArrayList<SportItem> SessionList;

    private float mScaleFactor = 1.f;


    TextView textMsg;

    private ScaleGestureDetector scaleGestureDetector;

    //Date[] date = new Date[13];
    //DataPoint[] values = new DataPoint[13];

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
        long date_min=0;
        long date_max=0;
        Integer Ymax =0;

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


            if(i==0)
            {
                Ymax=session.getRep();
                date_min=date[i].getTime();
                date_max=date[i].getTime();
            }

            if(session.getRep()>Ymax)
            {
                Ymax=session.getRep();
            }

            if(date[i].getTime()< date_min)
            {
                date_min = date[i].getTime();
            }

            if(date[i].getTime()> date_max)
            {
                date_max = date[i].getTime();
            }

            i++;
        }

        BarGraphSeries<DataPoint> series=new BarGraphSeries<>(values);

        graph.addSeries(series);

        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        //graph.getGridLabelRenderer().setNumHorizontalLabels(10); // only 4 because of the space
        //graph.getGridLabelRenderer().setNumVerticalLabels(10); // only 4 because of the space
        //graph.getGridLabelRenderer().setLabelHorizontalHeight(20);
        graph.getGridLabelRenderer().setTextSize(25); //25
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(90);
        graph.getGridLabelRenderer().setLabelsSpace(10);
        //graph.getGridLabelRenderer().setPadding(5); //10

        if(MainActivity.Time_nRep)
        {
            graph.getGridLabelRenderer().setVerticalAxisTitle(getString(R.string.ModeTime)); //ESSAI
        }
        else
        {
            graph.getGridLabelRenderer().setVerticalAxisTitle(getString(R.string.ModeRep)); //ESSAI
        }

        graph.getViewport().setXAxisBoundsManual(true); // pour ne pas sortir du graph mettre à false

        //graph.getViewport().setMinX(date_min-200000000);
        //graph.getViewport().setMaxX(date_max+200000000);
        graph.getViewport().setMinX(series.getLowestValueX()-200000000);
        graph.getViewport().setMaxX(series.getHighestValueX()+200000000);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(series.getHighestValueY()+10);

        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);
        //graph.getViewport().setScalableY(false);

        graph.getViewport().setBorderColor(Color.BLACK);
        graph.getViewport().setDrawBorder(false);

        //graph.getViewport().setMaxXAxisSize(100);

        // as we use dates as labels, the human rounding to nice readable numbers
        // is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);

        //textSport = myView.findViewById(R.id.textSport);
        sportImage=myView.findViewById(R.id.imageSport);
        switch(setSport) {
            case 0:
                //textSport.setText("ABDOS");
                series.setColor(COLOR_ABDO);
                sportImage.setImageResource(R.drawable.abdos);
                break;
            case 1:
                //textSport.setText("DORSEAUX");
                series.setColor(COLOR_DORSEAU);
                sportImage.setImageResource(R.drawable.dorsaux);
                break;
            case 2:
                //textSport.setText("CORDE");
                series.setColor(COLOR_CORDE);
                sportImage.setImageResource(R.drawable.corde_sauter);
                break;
            case 3:
                //textSport.setText("SQUATS");
                series.setColor(COLOR_SQUAT);
                sportImage.setImageResource(R.drawable.squats);
                break;
            default:
                //Do Something
        }

        series.setSpacing(10); //10
        // draw values on top
        series.setDrawValuesOnTop(true);
        //series.setAnimated(true);
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
        //getActivity().setTitle("Sport History: "+"Abdo");
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
