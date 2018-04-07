package ch.qoa.an.perfapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Date;

import static com.android.volley.VolleyLog.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AbdoTimeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AbdoTimeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AbdoTimeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
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

    //Date[] date = new Date[13];
    //DataPoint[] values = new DataPoint[13];

    public static final int COLOR_ABDO = Color.rgb(241, 3, 138);
    public static final int COLOR_DORSEAU = Color.rgb(148, 16, 231);
    public static final int COLOR_CORDE = Color.rgb(61, 33, 233);
    public static final int COLOR_SQUAT = Color.rgb(7, 200, 227);

    private OnFragmentInteractionListener mListener;

    public AbdoTimeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AbdoTimeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AbdoTimeFragment newInstance(String param1, String param2) {
        AbdoTimeFragment fragment = new AbdoTimeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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


        for (SportItem session: SessionList) {
            date[i] = new Date(session.getYear(), session.getMonth(), session.getDay());
            Log.i(TAG, "onCreateView********************************: "+date[i]);
            values[i] = new DataPoint(date[i], session.getRep());
            i++;
        }

        /*switch(setSport) {
            case 0:
                Integer i=0;


                for (SportItem session: SessionList) {
                    date[i] = new Date(session.getYear(), session.getMonth(), session.getDay());
                    values[i] = new DataPoint(date[i], session.getRep());
                    i++;
                }

                break;
            default:

                date[0] = new Date(2018, 6, 2);
                date[1] = new Date(2018, 6, 10);
                date[2] = new Date(2018, 6, 11);
                date[3] = new Date(2018, 6, 12);
                date[4] = new Date(2018, 6, 15);
                date[5] = new Date(2018, 6, 16);
                date[6] = new Date(2018, 6, 20);
                date[7] = new Date(2018, 6, 30);
                date[8] = new Date(2018, 7, 2);
                date[9] = new Date(2018, 7, 3);
                date[10] = new Date(2018, 7, 12);
                date[11] = new Date(2018, 7, 13);
                date[12] = new Date(2018, 7, 14);

                values[0] = new DataPoint(date[0], 3);
                values[1] = new DataPoint(date[1], 10);
                values[2] = new DataPoint(date[2], 23);
                values[3] = new DataPoint(date[3], 43);
                values[4] = new DataPoint(date[4], 10);
                values[5] = new DataPoint(date[5], 23);
                values[6] = new DataPoint(date[6], 43);
                values[7] = new DataPoint(date[7], 10);
                values[8] = new DataPoint(date[8], 23);
                values[9] = new DataPoint(date[9], 4);
                values[10] = new DataPoint(date[10], 25);
                values[11] = new DataPoint(date[11], 54);
                values[12] = new DataPoint(date[12], 34);

                BarGraphSeries<DataPoint> series=new BarGraphSeries<>(values);

                break;
                //Do Something
        }*/

        BarGraphSeries<DataPoint> series=new BarGraphSeries<>(values);
        //series = new LineGraphSeries<DataPoint>(values);


        graph.addSeries(series);

        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(4); // only 4 because of the space
        //graph.getGridLabelRenderer().setLabelHorizontalHeight(20);
        graph.getGridLabelRenderer().setTextSize(25);
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(90);
        graph.getGridLabelRenderer().setLabelsSpace(10);
        graph.getGridLabelRenderer().setPadding(10);

        // set manual x bounds to have nice steps
        graph.getViewport().setMinX(date[0].getTime());
        graph.getViewport().setMaxX(date[SessionList.size()-1].getTime());

        graph.getViewport().setMinY(0);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(false);

        graph.getViewport().setBorderColor(Color.BLACK);
        graph.getViewport().setDrawBorder(true);

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

        series.setSpacing(10);

        // draw values on top
        series.setDrawValuesOnTop(false);
        //series.setAnimated(true);
        //series.setValuesOnTopColor(Color.RED);
        //series.setValuesOnTopSize(50);

        return myView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Integer uri) {
        if (mListener != null) {
            mListener.onAbdoTimeFragmentInteraction(uri);
        }
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
        getActivity().setTitle("Sport History");
    }

    public void setSport(Integer sport) {
        setSport = sport;
    }

    public void setStationList(ArrayList<SportItem> sessionList) {
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
