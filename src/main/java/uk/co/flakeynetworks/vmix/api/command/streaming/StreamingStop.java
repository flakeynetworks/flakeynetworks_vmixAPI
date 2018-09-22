package uk.co.flakeynetworks.vmix.api.command.streaming;

import uk.co.flakeynetworks.vmix.api.command.VMixCommand;

public class StreamingStop extends VMixCommand {

    public StreamingStop() {

        parameters.put("Function", "StopStreaming");
    } // end of constructor
} // end of StreamingStop
