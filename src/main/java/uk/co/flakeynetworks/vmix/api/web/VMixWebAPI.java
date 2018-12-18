package uk.co.flakeynetworks.vmix.api.web;

import uk.co.flakeynetworks.vmix.status.VMixStatus;

import java.io.IOException;

public interface VMixWebAPI {

    VMixStatus getStatus() throws IOException;
}
