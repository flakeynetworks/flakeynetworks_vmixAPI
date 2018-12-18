package uk.co.flakeynetworks.vmix.api.service;

import uk.co.flakeynetworks.vmix.api.web.VMixWebAPI;

import java.net.URL;

public interface VMixAPIService {

    VMixWebAPI connect(URL url);
}
