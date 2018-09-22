package uk.co.flakeynetworks.vmix.api.command.streaming;

import uk.co.flakeynetworks.vmix.api.command.VMixCommand;

public class StreamingStart extends VMixCommand {

    public StreamingStart() {

        parameters.put("Function", "StartStreaming");
    } // end of constructor
} // end of StreamingStart
