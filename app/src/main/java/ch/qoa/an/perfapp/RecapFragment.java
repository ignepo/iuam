package ch.qoa.an.perfapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.jjoe64.graphview.GraphView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class RecapFragment extends Fragment implements OnChartValueSelectedListener{
    View myView;
    GraphView bargraph;
    private Button goAbdoTime;
    String TAG = "TestApp";

    public static ArrayList<SessionItem> AllSessionListRecap;


    private BarChart mChart;
    private SeekBar mSeekBarX, mSeekBarY;

    public static final int[] COLORS_PERFAPP = {
            Color.rgb(241, 3, 138), //#f1038a abdo
            Color.rgb(148, 16, 231), //#9410e7 dorseaux
            Color.rgb(61, 33, 233),//#3d21e9 cordes
            Color.rgb(7, 200, 227)//#07C8E3 squats
    };


    private OnFragmentInteractionListener mListener;

    public RecapFragment() {
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
            myView = inflater.inflate(R.layout.fragment_recap, container, false);
        }

        mChart = (BarChart) myView.findViewById(R.id.BarChart);
        mChart.setOnChartValueSelectedListener(this);

        mChart.getDescription().setEnabled(false);

        // if more than 15 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(15);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);

        mChart.setDrawValueAboveBar(false);
        mChart.setHighlightFullBarEnabled(false);

        // change the position of the y-labels
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setValueFormatter(new MyAxisValueFormatter());
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        mChart.getAxisRight().setEnabled(false);

        XAxis xLabels = mChart.getXAxis();
        xLabels.setPosition(XAxis.XAxisPosition.TOP);
        xLabels.setValueFormatter(new MyXAxisValueFormatter(mChart));


        //*************************************************************************************
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        //for (int i = 0; i < mSeekBarX.getProgress() + 1; i++) {

        float val1;
        float val2;
        float val3;
        float val4;


        for (SessionItem session: AllSessionListRecap) {
            //date[i] = new Date(session.getYear(), session.getMonth(), session.getDay());
            //calendar.getTimeinMilis();
            //int date = 731+i;
            Date date = new Date(session.getYear(), session.getMonth()-1, session.getDay()); //faire month-1
            //getDay(date)
            //Date date = new Date(2018, 6, i+1);
            Log.i(TAG, "onCreateView: "+getDay(date));
            //float mult = 50;
            if(MainActivity.Time_nRep)
            {
                val1 = session.getTimeAbdos();
                val2 = session.getTimeDorsaux();
                val3 = session.getTimeCorde();
                val4 = session.getTimeSquats();
            }
            else
            {
                val1 = session.getRepAbdos();
                val2 = session.getRepDorsaux();
                val3 = session.getRepCorde();
                val4 = session.getRepSquats();
            }


            yVals1.add(new BarEntry(
                    getDay(date)+731, //731=2018 // 367 = 2017 // 0=2016
                    new float[]{val1, val2, val3, val4})); //ESSAI
        }

        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "");
            set1.setDrawIcons(false);
            set1.setColors(getColors());
            set1.setStackLabels(new String[]{getString(R.string.Abdos), getString(R.string.Dorsaux), getString(R.string.Corde), getString(R.string.Squats)});

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueFormatter(new MyValueFormatter());
            data.setValueTextColor(Color.WHITE);

            mChart.setData(data);
        }

        mChart.setFitBars(true);
        mChart.invalidate();
        //*************************************************************************************

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);

// mChart.setDrawLegend(false);

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
        getActivity().setTitle(getString(R.string.TitleHist));
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
        void onRecapFragmentInteraction(Integer uri);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        BarEntry entry = (BarEntry) e;

        if (entry.getYVals() != null)
            Log.i("VAL SELECTED", "Value: " + entry.getYVals()[h.getStackIndex()]);
        else
            Log.i("VAL SELECTED", "Value: " + entry.getY());
    }

    @Override
    public void onNothingSelected() {
        // TODO Auto-generated method stub

    }

    private int[] getColors() {

        int stacksize = 4;

        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];

        colors = COLORS_PERFAPP;

        return colors;
    }

    public void setSessionHistList(ArrayList<SessionItem> allSessionList) {
        this.AllSessionListRecap = allSessionList;
    }

    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_YEAR);
    }
}
