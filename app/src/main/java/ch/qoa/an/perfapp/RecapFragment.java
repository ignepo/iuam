package ch.qoa.an.perfapp;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecapFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View myView;
    GraphView bargraph;
    private Button goAbdoTime;

    public static final int COLOR_ABDO = Color.rgb(0, 0, 0);
    public static final int COLOR_DORSEAU = Color.rgb(148, 16, 231);
    public static final int COLOR_CORDE = Color.rgb(61, 33, 233);
    public static final int COLOR_SQUAT = Color.rgb(7, 200, 227);



    private OnFragmentInteractionListener mListener;

    public RecapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecapFragment newInstance(String param1, String param2) {
        RecapFragment fragment = new RecapFragment();
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
            myView = inflater.inflate(R.layout.fragment_recap, container, false);
        }

        //-----------------------------------------------------------------------------------
        // Ajout listener
        //-----------------------------------------------------------------------------------
        goAbdoTime = myView.findViewById(R.id.button1);
        goAbdoTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null){
                    mListener.onRecapFragmentInteraction(2);
                }
            }
        });




        //-----------------------------------------------------------------------------------
        // generate Dates
        /*Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();
        calendar.add(Calendar.DATE, 3);
        Date d4 = calendar.getTime();
        calendar.add(Calendar.DATE, 2);
        Date d5 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d6 = calendar.getTime();
        calendar.add(Calendar.DATE, 4);
        Date d7 = calendar.getTime();
        calendar.add(Calendar.DATE, 7);
        Date d8 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d9 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d10 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d11 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d12 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);*/
        Date d1 = new Date(2018, 6, 2);
        Date d2 = new Date(2018, 6, 10);
        Date d3 = new Date(2018, 6, 11);
        Date d4 = new Date(2018, 6, 12);
        Date d5 = new Date(2018, 6, 15);
        Date d6 = new Date(2018, 6, 16);
        Date d7 = new Date(2018, 6, 20);
        Date d8 = new Date(2018, 6, 30);
        Date d9 = new Date(2018, 7, 2);
        Date d10 = new Date(2018, 7, 3);
        Date d11 = new Date(2018, 7, 12);
        Date d12 = new Date(2018, 7, 13);
        Date d13 = new Date(2018, 7, 14);
        //Date d14 = new Date(2018, 6, 2);//set(year + 1900, month, date);//calendar.getTime();


        GraphView graph = (GraphView) myView.findViewById(R.id.bargraph);

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

        series.setColor(COLOR_ABDO);
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
        series.setValuesOnTopColor(Color.RED);
        series.setValuesOnTopSize(50);

        return myView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Integer uri) {
        if (mListener != null) {
            mListener.onRecapFragmentInteraction(uri);
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
        getActivity().setTitle("Session Details");
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
}
