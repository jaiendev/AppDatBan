package com.trungdang.appdatban.HomeAdmin.Doanhthu;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.trungdang.appdatban.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Doanhthu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Doanhthu extends Fragment implements OnChartGestureListener, OnChartValueSelectedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    LineChart lineChart;
    TextView txtTongdonhang,txtTongTien;
    int donhang=0;
    int tongtien=0;

    Button btn7ngay,btn30ngay;
    FirebaseFirestore fStore=FirebaseFirestore.getInstance();
    public Doanhthu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Doanhthu.
     */
    // TODO: Rename and change types and number of parameters
    public static Doanhthu newInstance(String param1, String param2) {
        Doanhthu fragment = new Doanhthu();
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
        View view= inflater.inflate(R.layout.fragment_doanhthu, container, false);
        AnhXa(view);

        return view;
    }
    private void AnhXa(View view){
        txtTongTien=view.findViewById(R.id.txtTongtienDoanhthu);
        txtTongdonhang=view.findViewById(R.id.txtTongDonhang);
        btn30ngay=view.findViewById(R.id.btn30ngay);
        lineChart=view.findViewById(R.id.linechart);
        TongtienTongdonhang(view);

    }
    private void TongtienTongdonhang(View view){

        fStore.collection("Bills")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int t1 = 0;
                        int t2= 0;
                        int t3= 0;
                        int t4= 0;
                        int t5= 0;
                          int t6= 0;
                          int t7= 0;
                          int t8= 0;
                          int t9=0;
                          int t10=0;
                          int t11=0;
                          int t12=0;
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String tongtienbill = document.getString("tongTien");
                                String[] tachchuoi = tongtienbill.split(" ");
                                String date = document.getString("date");
                                String[] thang = date.split("/");
                                tongtien += Integer.parseInt(tachchuoi[0]);
                                donhang++;
                                txtTongdonhang.setText(donhang + "");
                                txtTongTien.setText(tongtien + "");
                                if (thang[1].equals("1")) {
                                    t1 += Integer.parseInt(tachchuoi[0]);
                                }
                                if (thang[1].equals("2")) {
                                    t2 += Integer.parseInt(tachchuoi[0]);
                                }
                                if (thang[1].equals("3")) {
                                    t3 += Integer.parseInt(tachchuoi[0]);
                                }
                                if (thang[1].equals("4")) {
                                    t4 += Integer.parseInt(tachchuoi[0]);
                                }
                                if (thang[1].equals("5")) {
                                    t5 += Integer.parseInt(tachchuoi[0]);
                                }
                                if (thang[1].equals("6")) {
                                    t6 += Integer.parseInt(tachchuoi[0]);
                                }
                                if (thang[1].equals("7")) {
                                    t7 += Integer.parseInt(tachchuoi[0]);
                                }
                                if (thang[1].equals("8")) {
                                    t8 += Integer.parseInt(tachchuoi[0]);
                                }
                                if (thang[1].equals("9")) {
                                    t9 += Integer.parseInt(tachchuoi[0]);
                                }
                                if (thang[1].equals("10")) {
                                    t10 += Integer.parseInt(tachchuoi[0]);
                                }
                                if (thang[1].equals("11")) {
                                    t11 += Integer.parseInt(tachchuoi[0]);
                                }
                                if (thang[1].equals("12")) {
                                    t12 += Integer.parseInt(tachchuoi[0]);
                                }

                                lineChart.setDragEnabled(true);
                                lineChart.setScaleEnabled(false);
                                LimitLine upper_limit=new LimitLine(65f,"Danger");//tạo 1 line trên biểu đồ
                                upper_limit.setLineWidth(4f);
                                upper_limit.enableDashedLine(10f,10f,0f);
                                upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
                                upper_limit.setTextSize(15f);

                                LimitLine lower_limit=new LimitLine(35f,"Too Low");
                                lower_limit.setLineWidth(2f);
                                lower_limit.enableDashedLine(5f,5f,0f);
                                lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
                                lower_limit.setTextSize(15f);

                                YAxis leftAxis=lineChart.getAxisLeft(); //tạo các line ẩn
                                leftAxis.removeAllLimitLines();
                                leftAxis.addLimitLine(upper_limit);
                                leftAxis.addLimitLine(lower_limit);
                                leftAxis.setAxisMaximum(100f);
                                leftAxis.setAxisMinimum(25f);
                                leftAxis.enableGridDashedLine(10f,10f,0);
                                leftAxis.setDrawLimitLinesBehindData(true);
                                lineChart.getAxisRight().setEnabled(false); // xóa 1 cạnh phải đồ thị
                                float th1=(t1/tongtien)*100;
                                float th2=(t2/tongtien)*100;
                                float th3=(t3/tongtien)*100;
                                float th4=(t4/tongtien)*100;
                                float th5=(t5/tongtien)*100;
                                float th6=(t6/tongtien)*100;
                                float th7=(t7/tongtien)*100;
                                float th8=(t8/tongtien)*100;
                                float th9=(t9/tongtien)*100;
                                float th10=(t10/tongtien)*100;
                                float th11=(t11/tongtien)*100;
                                float th12=(t12/tongtien)*100;

                                ArrayList<Entry> yValue= new ArrayList<>();

                                yValue.add(new Entry(0,th1));
                                yValue.add(new Entry(1,th2));
                                yValue.add(new Entry(2,th3));
                                yValue.add(new Entry(3,th4));
                                yValue.add(new Entry(4,th5));
                                yValue.add(new Entry(5,th6));
                                yValue.add(new Entry(6,th7));
                                yValue.add(new Entry(7,th8));
                                yValue.add(new Entry(8,th9));
                                yValue.add(new Entry(9,th10));
                                yValue.add(new Entry(10,th11));
                                yValue.add(new Entry(11,th12));


                                LineDataSet set1 = new LineDataSet(yValue,"Biểu đồ doanh thu 2021");

                                set1.setFillAlpha(110);

                                set1.setColor(Color.RED);
                                set1.setLineWidth(3f);
                                set1.setValueTextColor(Color.BLUE);

                                ArrayList<ILineDataSet> dataSets= new ArrayList<>();
                                dataSets.add(set1);

                                LineData data= new LineData(dataSets);

                                lineChart.setData(data);


                                String[] Values={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};//tạo tên cho côt

                                XAxis xAxis=lineChart.getXAxis();
                                xAxis.setValueFormatter(new MyAxisValueFormatter(Values));
                                xAxis.setGranularity(0.5f);
                                xAxis.setTextSize(0.2f);
                                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                            }
                        } else {

                        }
                    }
                });

//        CollectionReference productRefs = fStore.collection("Bills");
//        productRefs.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public  void onSuccess(QuerySnapshot documentSnapshots) {
//                if (documentSnapshots.isEmpty()) {
//                    Log.d("TAG", "onSuccess: LIST EMPTY");
//                    return;
//                } else {
//                    for (DocumentSnapshot document : documentSnapshots) {
//
//                    }
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d("TAG","Error");
//            }
//        });
    }
    private void Linechart30ngay(View view)
    {
//        lineChart.setOnChartGestureListener((OnChartGestureListener) getActivity());
//        lineChart.setOnChartValueSelectedListener((OnChartValueSelectedListener) getActivity());

    }
    public class MyAxisValueFormatter implements IAxisValueFormatter {
        private String[] mValues;
        public MyAxisValueFormatter(String[] mValues){
            this.mValues=mValues;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues[(int) value];
        }
    }
    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

}