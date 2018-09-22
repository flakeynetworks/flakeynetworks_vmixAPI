package uk.co.flakeynetworks.vmix.api.command.recording;

import uk.co.flakeynetworks.vmix.api.command.VMixCommand;

public class RecordingStart extends VMixCommand {

    public RecordingStart() {

        parameters.put("Function", "StartRecording");
    } // end of constructor
} // end of RecordingStart
