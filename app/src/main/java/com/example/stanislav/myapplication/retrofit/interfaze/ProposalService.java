package com.example.stanislav.myapplication.retrofit.interfaze;

import com.example.stanislav.myapplication.entity.location.Country;
import com.example.stanislav.myapplication.entity.model.LocalProposalUserModel;
import com.example.stanislav.myapplication.entity.model.UserCredentialsModel;
import com.example.stanislav.myapplication.entity.proposal.LocalProposal;
import com.example.stanislav.myapplication.entity.proposal.UserOrder;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProposalService {

    @GET("mobile/proposal/point/{id}")
    Call<List<LocalProposal>> getProposals(@Header("Cookie") String cookie, @Path("id") Long id);

    @GET("mobile/user/{id}/status/{status}")
    Call<List<UserOrder>> getStatusProposals(@Header("Cookie") String cookie,
                                             @Path("id") Long id,
                                             @Path("status") String status);

    @GET("mobile/user/{id}")
    Call<List<UserOrder>> getAllUsersProposals(@Header("Cookie") String cookie,
                                               @Path("id") Long id);

    @POST("mobile/proposal")
    Call<UserOrder> addProposal(@Header("Cookie") String cookie, @Body LocalProposalUserModel model);

}
