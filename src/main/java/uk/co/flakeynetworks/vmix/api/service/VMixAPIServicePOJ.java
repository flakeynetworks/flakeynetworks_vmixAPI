package uk.co.flakeynetworks.vmix.api.service;

import uk.co.flakeynetworks.vmix.api.web.VMixWebAPI;
import uk.co.flakeynetworks.vmix.api.web.poj.VMixAPIPOJ;

import java.net.URL;

public class VMixAPIServicePOJ implements VMixAPIService {

    @Override
    public VMixWebAPI connect(URL url) {

        VMixWebAPI retrofitService = new VMixAPIPOJ(url);

        return retrofitService;
    } // end of connect
} // end of VMixAPIServicePOJ
