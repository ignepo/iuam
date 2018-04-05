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
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Date;


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

    Date d1;
    Date d2;
    Date d3;
    Date d4;
    Date d5;
    Date d6;
    Date d7;
    Date d8;
    Date d9;
    Date d10;
    Date d11;
    Date d12;
    Date d13;

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


        //-----------------------------------------------------------------------------------
        // generate Dates
        d1 = new Date(2018, 6, 2);
        d2 = new Date(2018, 6, 10);
        d3 = new Date(2018, 6, 11);
        d4 = new Date(2018, 6, 12);
        d5 = new Date(2018, 6, 15);
        d6 = new Date(2018, 6, 16);
        d7 = new Date(2018, 6, 20);
        d8 = new Date(2018, 6, 30);
        d9 = new Date(2018, 7, 2);
        d10 = new Date(2018, 7, 3);
        d11 = new Date(2018, 7, 12);
        d12 = new Date(2018, 7, 13);
        d13 = new Date(2018, 7, 14);


        GraphView graph = myView.findViewById(R.id.bargraph);

        graph.removeAllSeries();

        // you can directly pass Date objects to DataPoint-Constructor
        // this will convert the Date to double via Date#getTime()
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(d1, 43),
                new DataPoint(d2, 10),
                new DataPoint(d3, 23),
                new DataPoint(d4, 43),
                new DataPoint(d5, 10),
                new DataPoint(d6, 23),
                new DataPoint(d7, 43),
                new DataPoint(d8, 10),
                new DataPoint(d9, 23),
                new DataPoint(d10, 43),
                new DataPoint(d11, 10),
                new DataPoint(d12, 23),
                new DataPoint(d13, 68)
        });
        graph.addSeries(series);

        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

        // set manual x bounds to have nice steps
        graph.getViewport().setMinX(d1.getTime());
        graph.getViewport().setMaxX(d13.getTime());
        graph.getViewport().setXAxisBoundsManual(true);

        // as we use dates as labels, the human rounding to nice readable numbers
        // is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);
        //-----------------------------------------------------------------------------------


        //GraphView graph = (GraphView) myView.findViewById(R.id.bargraph);
        /*BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, -1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);*/

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


        // styling
        /*series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });*/

        series.setSpacing(5);

        // draw values on top
        series.setDrawValuesOnTop(false);
        //series.setValuesOnTopColor(Color.RED);
        //series.setValuesOnTopSize(50);

        return myView;
        /*// Inflate the layout for this fragment
        if(myView == null){
            myView = inflater.inflate(R.layout.fragment_abdo_time, container, false);
        }

        //-----------------------------------------------------------------------------------
        // Ajout listener
        //-----------------------------------------------------------------------------------
        goRecap = myView.findViewById(R.id.button2);
        goRecap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null){
                    mListener.onAbdoTimeFragmentInteraction(1);
                }
            }
        });

        textSport = myView.findViewById(R.id.textSport);
        switch(setSport) {
            case 0:
                textSport.setText("ABDOS");
                break;
            case 1:
                textSport.setText("DORSEAUX");
                break;
            case 2:
                textSport.setText("CORDE");
                break;
            case 3:
                textSport.setText("SQUATS");
                break;
            default:
                //Do Something
        }

        // TODO Enlever le bouton de test
        //-----------------------------------------------------------------------------------
        // Ajout listener
        //-----------------------------------------------------------------------------------
        goLogin = myView.findViewById(R.id.buttonLogout);
        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Firebase sign out
                mListener.onAbdoTimeFragmentInteraction(999);
                /*FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                MainActivity.finish();
                startActivity(EmailPasswordActivity.intentLogin); //attention à commenter
            }
        });


        //-----------------------------------------------------------------------------------
        // Graphique ligne
        //-----------------------------------------------------------------------------------
        graph = myView.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3)
        });
        graph.addSeries(series);

        return myView;*/
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
