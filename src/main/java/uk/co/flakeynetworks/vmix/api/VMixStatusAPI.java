package uk.co.flakeynetworks.vmix.api;

import retrofit2.Call;
import retrofit2.http.GET;
import uk.co.flakeynetworks.vmix.status.VMixStatus;

@Deprecated
public interface VMixStatusAPI {

    @Deprecated
    @GET("?")
    Call<VMixStatus> getStatus();
} // end of vMixStatus
