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
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;


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
    private Button goLogin;
    private TextView textSport;
    GraphView graph;
    Integer setSport;

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
                startActivity(EmailPasswordActivity.intentLogin);*/
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
