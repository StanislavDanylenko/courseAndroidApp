package com.example.stanislav.myapplication.activity.menu.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stanislav.myapplication.R;
import com.example.stanislav.myapplication.SpeeerApplication;
import com.example.stanislav.myapplication.entity.User;
import com.example.stanislav.myapplication.entity.UserAuth;
import com.example.stanislav.myapplication.entity.enumeration.Localization;
import com.example.stanislav.myapplication.entity.model.LocalProposalUserModel;
import com.example.stanislav.myapplication.entity.model.UserCredentialsModel;
import com.example.stanislav.myapplication.entity.proposal.LocalProposal;
import com.example.stanislav.myapplication.entity.proposal.Report;
import com.example.stanislav.myapplication.entity.proposal.UserOrder;
import com.example.stanislav.myapplication.retrofit.interfaze.ProposalService;
import com.example.stanislav.myapplication.retrofit.interfaze.UserService;
import com.example.stanislav.myapplication.view.HorizontalRulerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddFragment extends Fragment implements View.OnClickListener {

    private Retrofit retrofit;
    private User user;
    private UserAuth userAuth;

    private SpeeerApplication application;
    private View thisActivity;

    private List<LocalProposal> proposals;

    private LinearLayout.LayoutParams layoutParams;
    private LinearLayout.LayoutParams layoutParamsHeader;
    private LinearLayout.LayoutParams layoutParamsBody;
    private LinearLayout.LayoutParams layoutParamsInput;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        application = (SpeeerApplication) getActivity().getApplication();

        retrofit = application.getRetrofit();
        user = application.getUser();
        userAuth = application.getCredentials();

        proposals = application.getProposals();

        layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(30, 20, 30, 20);

        layoutParamsHeader = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsHeader.setMargins(30, 20, 30, 5);

        layoutParamsBody = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsBody.setMargins(30, 5, 30, 20);

        layoutParamsInput = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsInput.setMargins(50, 0, 50, 20);

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        thisActivity = view;

        LinearLayout itemContainer = (LinearLayout) view.findViewById(R.id.add_layout);

        if (proposals != null) {
            int i = 0;
            for (LocalProposal proposal : proposals) {

                LinearLayout item = new LinearLayout(view.getContext());
                item.setOrientation(LinearLayout.VERTICAL);
                item.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.card));


                TextView proposalTitleLabel = new TextView(view.getContext());
                proposalTitleLabel.setGravity(Gravity.CENTER);
                proposalTitleLabel.setTypeface(null, Typeface.BOLD);
                proposalTitleLabel.setText(getString(R.string.title));
                item.addView(proposalTitleLabel, layoutParamsHeader);

                TextView proposalTitle = new TextView(view.getContext());
                proposalTitle.setGravity(Gravity.CENTER);
                proposalTitle.setText(proposal.getProposal().getName());
                item.addView(proposalTitle, layoutParamsBody);



                TextView descriptionLabel = new TextView(view.getContext());
                descriptionLabel.setGravity(Gravity.CENTER);
                descriptionLabel.setTypeface(null, Typeface.BOLD);
                descriptionLabel.setText(getString(R.string.вуіскшзешщт));
                item.addView(descriptionLabel, layoutParamsHeader);

                TextView description = new TextView(view.getContext());
                description.setGravity(Gravity.CENTER);
                description.setText(proposal.getProposal().getDescription());
                item.addView(description, layoutParamsBody);



                TextView priceLabel = new TextView(view.getContext());
                priceLabel.setGravity(Gravity.CENTER);
                priceLabel.setTypeface(null, Typeface.BOLD);
                priceLabel.setText(getString(R.string.price));
                item.addView(priceLabel, layoutParamsHeader);

                TextView price = new TextView(view.getContext());
                price.setGravity(Gravity.CENTER);
                price.setText(proposal.getPrice().toString());
                item.addView(price, layoutParamsBody);


                HorizontalRulerView rulerView = new HorizontalRulerView(view.getContext());
                item.addView(rulerView);


                TextView xLabel = new TextView(view.getContext());
                xLabel.setGravity(Gravity.CENTER);
                xLabel.setTypeface(null, Typeface.BOLD);
                xLabel.setText(getString(R.string.latitude));
                item.addView(xLabel, layoutParamsHeader);

                EditText editX = new EditText(view.getContext());
                editX.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                editX.setGravity(Gravity.CENTER);
                editX.setId(100 + i);
                editX.setHint(getString(R.string.set_latitude));
                item.addView(editX, layoutParamsInput);


                TextView yLabel = new TextView(view.getContext());
                yLabel.setGravity(Gravity.CENTER);
                yLabel.setTypeface(null, Typeface.BOLD);
                yLabel.setText(getString(R.string.longitude));
                item.addView(yLabel, layoutParamsHeader);

                EditText editY = new EditText(view.getContext());
                editY.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                editY.setGravity(Gravity.CENTER);
                editY.setId(1000 + i);
                editY.setHint(getString(R.string.set_longitude));
                item.addView(editY, layoutParamsInput);


                Button button = new Button(view.getContext());
                button.setGravity(Gravity.CENTER);
                button.setText(getString(R.string.buy));
                button.setId(i);
                button.setOnClickListener(this);
                button.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.button_accept));
                button.setTextColor(Color.WHITE);
                item.addView(button, layoutParams);


                itemContainer.addView(item, layoutParams);
                i++;
            }
        } else {
            TextView info = new TextView(view.getContext());
            info.setText(getString(R.string.no_active_proposals));

            itemContainer.addView(info, layoutParams);
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        EditText editX = thisActivity.findViewById(100 + id);
        EditText editY = thisActivity.findViewById(1000 + id);


        Double xValue = null;
        try {
            xValue = Double.parseDouble(editX.getText().toString());
        } catch (NumberFormatException e) {
            editX.setError(getString(R.string.incorrect_input));
            return;
        }
        Double yValue = null;
        try {
            yValue = Double.parseDouble(editY.getText().toString());
        } catch (NumberFormatException e) {
            editY.setError(getString(R.string.incorrect_input));
            return;
        }

        addOrder(userAuth, proposals.get(id).getProposal().getId(), new Double[] {xValue, yValue});
    }

    private void addOrder(UserAuth credentials, long proposalId, Double[] coords) {

        LocalProposalUserModel model = new LocalProposalUserModel(user.getId(), user.getDefaultPopulatedPoint(), proposalId, coords);

        ProposalService proposalService = retrofit.create(ProposalService.class);
        proposalService.addProposal(application.getCookies(), model).enqueue(new Callback<UserOrder>() {
            @Override
            public void onResponse(Call<UserOrder> call, Response<UserOrder> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(thisActivity.getContext(), R.string.success_adding_order, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(thisActivity.getContext(), R.string.erroe_adding_order, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserOrder> call, Throwable t) {
                Toast.makeText(thisActivity.getContext(), R.string.erroe_adding_order, Toast.LENGTH_SHORT).show();
            }
        });
    }

}

