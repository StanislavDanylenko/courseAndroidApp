package com.example.stanislav.myapplication.background;

import android.content.Context;

import com.example.stanislav.myapplication.SpeeerApplication;
import com.example.stanislav.myapplication.entity.UserAuth;
import com.example.stanislav.myapplication.entity.model.UserCredentialsModel;
import com.example.stanislav.myapplication.entity.proposal.OperationStatus;
import com.example.stanislav.myapplication.entity.proposal.UserOrder;
import com.example.stanislav.myapplication.retrofit.interfaze.ProposalService;

import java.io.IOException;
import java.util.List;

import retrofit2.Retrofit;

public class ServerUpdate extends Thread {

    private SpeeerApplication application;
    private Context context;
    private MyService service;
    private List<UserOrder> orders;
    private UserAuth credentials;
    private Retrofit retrofit;

    public ServerUpdate(SpeeerApplication application, Context context, MyService service) {
        this.application = application;
        this.context = context;
        this.service = service;
        retrofit = application.getRetrofit();
        credentials = application.getCredentials();
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                sleep(1000 * 60);
                orders = application.getCurrentStatusOrderList();
                int count = processOrderList();
                if (count > 0) {
                    service.notificate(count);
                }
            } catch (InterruptedException e) {
                return;
            }
        }
    }


    private int processOrderList() {

        if (orders == null) {
            return 0;
        }

        UserCredentialsModel model = new UserCredentialsModel(credentials.getEmail(), credentials.getPassword());
        int res = 0;

        ProposalService proposalService = retrofit.create(ProposalService.class);
        try {
            List<UserOrder> ordersUpdated = proposalService.getStatusProposals(model, credentials.getId(), OperationStatus.NEW.name()).execute().body();

            if (ordersUpdated.size() != ordersUpdated.size()) {
                res += Math.abs(ordersUpdated.size() - ordersUpdated.size());
            }

            for (UserOrder newOrder : ordersUpdated) {
                for (UserOrder order : orders) {
                    if (newOrder.getUuid().equals(order.getUuid())) {
                        if (newOrder.getStatus() != order.getStatus()) {
                            res += 1;
                            break;
                        }
                    }
                }
            }
            application.setCurrentStatusOrderList(ordersUpdated);

        } catch (IOException e) {
            return 0;
        }
        return res;
    }
}
