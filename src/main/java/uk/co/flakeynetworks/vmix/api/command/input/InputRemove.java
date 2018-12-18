package uk.co.flakeynetworks.vmix.api.command.input;

import uk.co.flakeynetworks.vmix.api.command.VMixCommand;
import uk.co.flakeynetworks.vmix.status.Input;

public class InputRemove extends VMixCommand {


    public InputRemove(String input) {

        parameters.put("Function", "RemoveInput");
        parameters.put("Input", input);
    } // end of constructor


    public InputRemove(Input input) {

        parameters.put("Function", "RemoveInput");
        parameters.put("Input", input.getKey());
    } // end of constructor
} // end of InputRemove
