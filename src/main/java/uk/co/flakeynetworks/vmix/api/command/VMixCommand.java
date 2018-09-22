package uk.co.flakeynetworks.vmix.api.command;

import uk.co.flakeynetworks.vmix.VMixVersion;
import uk.co.flakeynetworks.vmix.api.exceptions.FeatureNotAvailableException;

import java.util.HashMap;
import java.util.Map;

public class VMixCommand {

    protected Map<String, String> parameters = new HashMap<>();
    protected VMixVersion minVersion;

    public Map<String, String> getParameters() throws FeatureNotAvailableException { return parameters; } // end of getParameters
} // end of VMixCommand
