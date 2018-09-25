package uk.co.flakeynetworks.vmix.api.command.input;

import uk.co.flakeynetworks.vmix.api.command.VMixCommand;
import uk.co.flakeynetworks.vmix.status.Input;

public class InputSendToPreview extends VMixCommand {


    public InputSendToPreview(String input) {

        parameters.put("Function", "PreviewInput");
        parameters.put("Input", input);
    } // end of constructor


    public InputSendToPreview(Input input) {

        parameters.put("Function", "PreviewInput");
        parameters.put("Input", input.getKey());
    } // end of constructor
} // end of InputSendToPreview
