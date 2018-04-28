package ch.qoa.an.perfapp;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jjoe64.graphview.GraphView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GlobalFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GlobalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
//implements OnStreetViewPanoramaReadyCallback
public class GlobalFragment extends Fragment implements OnChartValueSelectedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    String TAG = "TestApp";

    //70r0-0kLNDWmRIOukJSNBrTPRlP
    /*public static final int[] COLORS_PERFAPP = {
            Color.rgb(159, 89, 198),
            Color.rgb(230, 96, 152),
            Color.rgb(255, 255, 107),
            Color.rgb(179, 240, 100)
    };*/
    /*public static final int[] COLORS_PERFAPP = {
            Color.rgb(242, 254, 113),
            Color.rgb(0, 151, 167),
            Color.rgb(0, 188, 212),
            Color.rgb(0, 0, 0)
    };*/

    public static final int[] COLORS_PERFAPP = {
            Color.rgb(241, 3, 138), //#f1038a abdo
            Color.rgb(148, 16, 231), //#9410e7 dorseaux
            Color.rgb(61, 33, 233),//#3d21e9 cordes
            Color.rgb(7, 200, 227)//#07C8E3 squats
    };

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View myView;
    private Button buttonRep;
    private Button buttonTime;
    private TextView text;
    PieChart pieChart;


    private OnFragmentInteractionListener mListener;

    public GlobalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GlobalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GlobalFragment newInstance(String param1, String param2) {
        GlobalFragment fragment = new GlobalFragment();
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
        myView=inflater.inflate(R.layout.fragment_global, container, false);
        Log.i(TAG, "onCreanteView: ********FRAGMENT*********");

        //processGETRequest();

        //-----------------------------------------------------------------------------------
        // Ajout listener
        //-----------------------------------------------------------------------------------
        /*buttonRep = myView.findViewById(R.id.buttonRep);
        buttonRep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null){
                    MainActivity.Time_nRep=false;
                    //buttonRep.setBackgroundTintList(getResources().getColorStateList((R.color.colorAccent)));

                    //buttonTime.setBackgroundTintList(getResources().getColorStateList((R.color.grey_300)));

                    mListener.onGlobalFragmentInteraction(101);
                }
            }
        });*/

        /*buttonTime = myView.findViewById(R.id.buttonTime);
        buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null){
                    MainActivity.Time_nRep=true;
                    //buttonTime.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));

                    //buttonRep.setBackgroundTintList(getResources().getColorStateList(R.color.grey_300));
                    mListener.onGlobalFragmentInteraction(100);
                }
            }
        });*/

        if(MainActivity.Time_nRep)
        {
            //buttonTime.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));

            //buttonRep.setBackgroundTintList(getResources().getColorStateList(R.color.grey_300));
        }
        else
        {
            //buttonRep.setBackgroundTintList(getResources().getColorStateList((R.color.colorAccent)));

            //buttonTime.setBackgroundTintList(getResources().getColorStateList((R.color.grey_300)));

        }

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
        pieChart.setUsePercentValues(false); //Converti en pourcentage
        pieChart.getDescription().setEnabled(false); //Text dans le chart
        //pieChart.setDrawEntryLabels(false);
        pieChart.setDrawEntryLabels(true);//Text dans le chart
        pieChart.setEntryLabelColor(Color.rgb(33, 33, 33));
        pieChart.setEntryLabelTextSize(15);
        //pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setExtraOffsets(10, 0, 10, 0); //Centrage du cercle dans la zone
        pieChart.setHoleRadius(20);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(true);
        //pieChart.setHoleColor(Color.BLACK);
        pieChart.setHoleColor(Color.DKGRAY);
        pieChart.setTransparentCircleRadius(35f); //transparence du cercle
        //pieChart.setTransparentCircleRadius(0f);

        ArrayList<PieEntry> yValues = new ArrayList<>();
        /*yValues.add(new PieEntry(43f,"Abdo")); //x=0 dans higlight
        yValues.add(new PieEntry(23f, "Dorseaux"));//x=1 dans higlight
        yValues.add(new PieEntry(67f, "Corde"));//x=2 dans higlight
        yValues.add(new PieEntry(35f, "Squat"));//x=3 dans higlight*/

        //while(AbdosNum == "null" );
        yValues.add(new PieEntry(Float.parseFloat(MainActivity.AbdosNum), "Abdo")); //x=0 dans higlight
        yValues.add(new PieEntry(Float.parseFloat(MainActivity.DorseauxNum), "Dorseaux"));//x=1 dans higlight
        yValues.add(new PieEntry(Float.parseFloat(MainActivity.CordesNum), "Corde"));//x=2 dans higlight
        yValues.add(new PieEntry(Float.parseFloat(MainActivity.SquatsNum), "Squat"));//x=3 dans higlight

        //yValues.add(new PieEntry(40f, "partye"));
        //yValues.add(new PieEntry(23f, "partyf"));

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(5f);
        dataSet.setSelectionShift(5f);
        //dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        dataSet.setColors(COLORS_PERFAPP);


        PieData data = new PieData((dataSet));
        data.setValueTextSize(21f);
        //data.setValueTextColor(Color.TRANSPARENT);
        //data.setValueTextColor(Color.rgb(33, 33, 33));
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);
        pieChart.getLegend().setEnabled(false); //Enlever la légende
        pieChart.setOnChartValueSelectedListener(this);

        //text = myView.findViewById(R.id.sport);

        return myView;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i(TAG, "onValueSelected e: *****************"+e);
        Log.i(TAG, "onValueSelected h: *****************"+h.getX());
        mListener.onGlobalFragmentInteraction((int)(h.getX()));
        //text = myView.findViewById(R.id.sport);

        /*switch((int)(h.getX())) {
            case 0:
                Log.i(TAG, "onValueSelected : Abdos");
                text.setText("ABDOS");
                break;
            case 1:
                Log.i(TAG, "onValueSelected : Dorseaux");
                text.setText("DORSEAUX");
                break;
            case 2:
                Log.i(TAG, "onValueSelected : Corde");
                text.setText("CORDE");
                break;
            case 3:
                Log.i(TAG, "onValueSelected : Squats");
                text.setText("SQUATS");
                break;
            default:
                Log.i(TAG, "onValueSelected : Other");
        }*/

    }

    @Override
    public void onNothingSelected() {

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Integer uri) {
        if (mListener != null) {
            mListener.onGlobalFragmentInteraction(uri);
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
        getActivity().setTitle("PerfApp");
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
