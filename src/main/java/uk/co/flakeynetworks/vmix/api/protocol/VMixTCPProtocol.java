package uk.co.flakeynetworks.vmix.api.protocol;

import uk.co.flakeynetworks.vmix.VMixVersion;

public interface VMixTCPProtocol {

    String PROTOCOL_DELIMITER = " ";

    void processMessage(String message);
    VMixVersion decodeVersion(String response);
    void subscribeTally();
    long getLastMessageTimestamp();
} // end of VMixTCPProtocol
