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
import android.support.v4.content.ContextCompat;
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
import com.example.stanislav.myapplication.view.HorizontalRulerView;

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
                item.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.card));


                TextView proposal = new TextView(view.getContext());
                proposal.setGravity(Gravity.CENTER);
                proposal.setTypeface(null, Typeface.BOLD);
                proposal.setText(getString(R.string.title_card) + " " + order.getLocalProposal().getProposal().getName());
                item.addView(proposal, layoutParams);

                HorizontalRulerView rulerView = new HorizontalRulerView(view.getContext());
                item.addView(rulerView);


                TextView point = new TextView(view.getContext());
                point.setGravity(Gravity.CENTER);
                point.setText(getString(R.string.location) + " " +order.getLocalProposal().getPopulatedPoint().getName());
                item.addView(point);

                TextView price = new TextView(view.getContext());
                price.setGravity(Gravity.CENTER);
                price.setText(getString(R.string.price_card) + " " + order.getPrice().toString());
                item.addView(price, layoutParams);

                TextView status = new TextView(view.getContext());
                status.setGravity(Gravity.CENTER);
                status.setTypeface(null, Typeface.BOLD);
                status.setText(getString(R.string.status) + " " +order.getStatus().name());
                item.addView(status, layoutParams);

                Button button = new Button(view.getContext());
                button.setGravity(Gravity.CENTER);
                button.setText(getString(R.string.show_report));
                button.setId((int) (long) order.getDroneId());
                button.setOnClickListener(this);
                button.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.button_accept));
                button.setTextColor(Color.WHITE);
                item.addView(button, layoutParams);


                itemContainer.addView(item, layoutParams);
            }
        } else {
            TextView info = new TextView(view.getContext());
            info.setText(getString(R.string.no_active_order_or_upload));

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
            reportString = application.getReportString(report);
        } else {
            reportString = getString(R.string.not_allowed_yet);
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
}
