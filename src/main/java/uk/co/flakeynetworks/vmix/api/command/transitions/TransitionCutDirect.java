package uk.co.flakeynetworks.vmix.api.command.transitions;

import uk.co.flakeynetworks.vmix.api.command.VMixCommand;
import uk.co.flakeynetworks.vmix.status.Input;

/**
 * Cuts the input directly to Output without changing Preview
 */
public class TransitionCutDirect extends VMixCommand {


    private String input;


    public TransitionCutDirect(String input) {

        parameters.put("Function", "CutDirect");
        parameters.put("Input", input);
    } // end of constructor


    public TransitionCutDirect(Input input) {

        parameters.put("Function", "CutDirect");
        parameters.put("Input", input.getKey());
    } // end of TransitionCutDirect
} // end of TransitionCutDirect
