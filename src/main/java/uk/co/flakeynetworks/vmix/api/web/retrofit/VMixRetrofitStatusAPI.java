package uk.co.flakeynetworks.vmix.api.web.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import uk.co.flakeynetworks.vmix.status.VMixStatus;

public interface VMixRetrofitStatusAPI {

    @GET("?")
    Call<VMixStatus> getStatus();
} // end of vMixStatus
