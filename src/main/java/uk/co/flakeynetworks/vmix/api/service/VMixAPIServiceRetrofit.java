package uk.co.flakeynetworks.vmix.api.service;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import uk.co.flakeynetworks.vmix.api.web.VMixWebAPI;
import uk.co.flakeynetworks.vmix.api.web.retrofit.VMixAPIRetrofit;
import uk.co.flakeynetworks.vmix.api.web.retrofit.VMixRetrofitStatusAPI;

import java.net.URL;

@Deprecated
public class VMixAPIServiceRetrofit implements VMixAPIService {

    @Override
    public VMixWebAPI connect(URL url) {

        // Create the retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url.toString())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();


        VMixRetrofitStatusAPI statusAPI = retrofit.create(VMixRetrofitStatusAPI.class);

        // Encapsulate the retrofit implement into the service interface.
        return new VMixAPIRetrofit(statusAPI);
    } // end of connect
} // end of VMixAPIServiceRetrofit
