package uk.co.flakeynetworks.vmix.api.service;

import uk.co.flakeynetworks.vmix.api.web.VMixWebAPI;
import uk.co.flakeynetworks.vmix.api.web.poj.VMixAPIPOJ;

import java.net.URL;

public class VMixAPIServicePOJ implements VMixAPIService {

    @Override
    public VMixWebAPI connect(URL url) {

        return new VMixAPIPOJ(url);
    } // end of connect
} // end of VMixAPIServicePOJ
