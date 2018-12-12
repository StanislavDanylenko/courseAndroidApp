package com.example.stanislav.myapplication.retrofit.interfaze;

import com.example.stanislav.myapplication.entity.location.Country;
import com.example.stanislav.myapplication.entity.model.LocalProposalUserModel;
import com.example.stanislav.myapplication.entity.model.UserCredentialsModel;
import com.example.stanislav.myapplication.entity.proposal.LocalProposal;
import com.example.stanislav.myapplication.entity.proposal.UserOrder;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProposalService {

    @POST("mobile/proposal/point/{id}")
    Call<List<LocalProposal>> getProposals(@Body UserCredentialsModel model, @Path("id") Long id);

    @POST("mobile/user/{id}/status/{status}")
    Call<List<UserOrder>> getStatusProposals(@Body UserCredentialsModel model,
                                             @Path("id") Long id,
                                             @Path("status") String status);

    @POST("mobile/user/{id}")
    Call<List<UserOrder>> getAllUsersProposals(@Body UserCredentialsModel model,
                                               @Path("id") Long id);

    @POST("mobile/proposal")
    Call<UserOrder> addProposal(@Body LocalProposalUserModel model);

}
