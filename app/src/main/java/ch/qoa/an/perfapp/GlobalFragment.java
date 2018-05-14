package ch.qoa.an.perfapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GlobalFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
//implements OnStreetViewPanoramaReadyCallback
public class GlobalFragment extends Fragment implements OnChartValueSelectedListener{

    String TAG = "TestApp";

    public static final int[] COLORS_PERFAPP = {
            Color.rgb(241, 3, 138), //#f1038a abdo
            Color.rgb(148, 16, 231), //#9410e7 dorseaux
            Color.rgb(61, 33, 233),//#3d21e9 cordes
            Color.rgb(7, 200, 227)//#07C8E3 squats
    };

    View myView;
    private RadioButton buttonRep;
    private RadioButton buttonTime;
    private boolean first = true;
    PieChart pieChart;

    private OnFragmentInteractionListener mListener;

    public GlobalFragment() {
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
        myView=inflater.inflate(R.layout.fragment_global, container, false);

        buttonRep = myView.findViewById(R.id.radioButton2);
        buttonTime = myView.findViewById(R.id.radioButton);

        if(first)
        {
            buttonRep.setChecked(true);
            first=false;
        }

        buttonRep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null){
                    MainActivity.Time_nRep=false;
                    mListener.onGlobalFragmentInteraction(101);
                }
            }
        });

        buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null){
                    MainActivity.Time_nRep=true;
                    mListener.onGlobalFragmentInteraction(100);
                }
            }
        });

        FloatingActionButton seeStationOnMap = myView.findViewById(R.id.mapButton);
        seeStationOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null){
                    mListener.onGlobalFragmentInteraction(800);
                }
            }
        });

        //-----------------------------------------------------------------------------------
        // Graphique Piechart
        //-----------------------------------------------------------------------------------
        pieChart = myView.findViewById(R.id.piechart);
        pieChart.setTouchEnabled(true);
        pieChart.setUsePercentValues(true); //Convert in percent
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawEntryLabels(true);//Text dans le chart
        pieChart.setEntryLabelColor(Color.rgb(33, 33, 33));
        pieChart.setEntryLabelTextSize(15);
        pieChart.setExtraOffsets(10, 0, 10, 0); //Centering the circle.
        pieChart.setHoleRadius(20);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.DKGRAY);
        pieChart.setTransparentCircleRadius(35f); //Circle transparency

        ArrayList<PieEntry> yValues = new ArrayList<>();

        yValues.add(new PieEntry(Float.parseFloat(MainActivity.AbdosNum), getString(R.string.Abdos))); //x=0 dans higlight
        yValues.add(new PieEntry(Float.parseFloat(MainActivity.DorseauxNum), getString(R.string.Dorsaux)));//x=1 dans higlight
        yValues.add(new PieEntry(Float.parseFloat(MainActivity.CordesNum), getString(R.string.Corde)));//x=2 dans higlight
        yValues.add(new PieEntry(Float.parseFloat(MainActivity.SquatsNum), getString(R.string.Squats)));//x=3 dans higlight

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(5f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(COLORS_PERFAPP);


        PieData data = new PieData((dataSet));
        data.setValueTextSize(21f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);
        pieChart.getLegend().setEnabled(false); //Remove Legend
        pieChart.setOnChartValueSelectedListener(this);
        
        return myView;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        mListener.onGlobalFragmentInteraction((int)(h.getX()));
    }

    @Override
    public void onNothingSelected() {

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
        getActivity().setTitle(getString(R.string.PerfApp));
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
        void onGlobalFragmentInteraction(Integer uri);
    }
}
