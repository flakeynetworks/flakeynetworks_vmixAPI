package uk.co.flakeynetworks.vmix.api.command.recording;

import uk.co.flakeynetworks.vmix.api.command.VMixCommand;

public class RecordingStop extends VMixCommand {

    public RecordingStop() {
        parameters.put("Function", "StopRecording");
    } // end of constructor
} // end of RecordingStop
