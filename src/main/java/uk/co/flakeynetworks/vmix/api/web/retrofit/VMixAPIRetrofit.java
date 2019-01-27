package uk.co.flakeynetworks.vmix.api.web.retrofit;

import retrofit2.Response;
import uk.co.flakeynetworks.vmix.api.web.VMixWebAPI;
import uk.co.flakeynetworks.vmix.status.VMixStatus;

import java.io.IOException;

@Deprecated
public class VMixAPIRetrofit implements VMixWebAPI {


    VMixRetrofitStatusAPI retrofitService;


    public VMixAPIRetrofit(VMixRetrofitStatusAPI retrofitService) {

        this.retrofitService = retrofitService;
    } // end of constructor


    @Override
    public VMixStatus getStatus() throws IOException {

        Response<VMixStatus> response = retrofitService.getStatus().execute();

        return response.body();
    } // end of getStatus
} // end of VMixAPIRetrofit
