package com.example.stanislav.myapplication.activity.menu.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.stanislav.myapplication.R;
import com.example.stanislav.myapplication.SpeeerApplication;
import com.example.stanislav.myapplication.entity.proposal.Report;
import com.example.stanislav.myapplication.entity.proposal.UserOrder;

import java.util.List;

public class AllFragment extends Fragment implements View.OnClickListener {

    private SpeeerApplication application;

    private List<UserOrder> currentStatusOrderList;

    private LinearLayout.LayoutParams layoutParams;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        application = (SpeeerApplication) getActivity().getApplication();
        currentStatusOrderList = application.getCurrentStatusOrderList();

        layoutParams = new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(30, 20, 30, 20);

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);

        LinearLayout itemContainer = (LinearLayout) view.findViewById(R.id.all_layout);

        if (currentStatusOrderList != null) {
            for (UserOrder order : currentStatusOrderList) {

                LinearLayout item = new LinearLayout(view.getContext());
                item.setOrientation(LinearLayout.VERTICAL);
                item.setBackgroundColor(Color.parseColor("#ffffff"));


                TextView proposal = new TextView(view.getContext());
                proposal.setGravity(Gravity.CENTER);
                proposal.setTypeface(null, Typeface.BOLD);
                proposal.setText(order.getLocalProposal().getProposal().getName());
                item.addView(proposal, layoutParams);

                TextView point = new TextView(view.getContext());
                point.setGravity(Gravity.CENTER);
                point.setText(order.getLocalProposal().getPopulatedPoint().getName());
                item.addView(point);

                TextView price = new TextView(view.getContext());
                price.setGravity(Gravity.CENTER);
                price.setText(order.getPrice().toString());
                item.addView(price, layoutParams);

                TextView status = new TextView(view.getContext());
                status.setGravity(Gravity.CENTER);
                status.setText(order.getStatus().name());
                item.addView(status, layoutParams);

                Button button = new Button(view.getContext());
                button.setGravity(Gravity.CENTER);
                button.setText("Show report");
                button.setId((int) (long) order.getDroneId());
                button.setOnClickListener(this);
                button.setBackgroundColor(Color.parseColor("#2a68ee"));
                item.addView(button, layoutParams);


                itemContainer.addView(item, layoutParams);
            }
        } else {
            TextView info = new TextView(view.getContext());
            info.setText("No active orders or upload please");

            itemContainer.addView(info, layoutParams);
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Report report = findReport(id);

        String reportString;
        if (report != null) {
            reportString = getReportString(report);
        } else {
            reportString = "Not allowed yet";
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setMessage(reportString)
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private Report findReport(int id) {
        for (UserOrder order : currentStatusOrderList) {
            if (order.getDroneId() == id) {
                return order.getReport();
            }
        }
        return null;
    }

    private String getReportString(Report report) {
        StringBuilder sb = new StringBuilder();

        sb
          .append("HUMIDITY: ")
          .append(report.getHumidity())
          .append('\n')
          .append("RADIATION: ")
          .append(report.getRadiation())
          .append('\n')
          .append("PRESSURE: ")
          .append(report.getPressure())
          .append('\n')
          .append("AIR POLLUTION: ")
          .append(report.getAirPollution())
          .append('\n')
          .append("TEMPERATURE: ")
          .append(report.getTemperature());

        return sb.toString();
    }
}
