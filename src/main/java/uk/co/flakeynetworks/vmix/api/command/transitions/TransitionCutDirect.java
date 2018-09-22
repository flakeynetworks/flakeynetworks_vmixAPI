package uk.co.flakeynetworks.vmix.api.command.transitions;

import uk.co.flakeynetworks.vmix.api.command.VMixCommand;

/**
 * Cuts the input directly to Output without changing Preview
 */
public class TransitionCutDirect extends VMixCommand {

    private String input;

    public TransitionCutDirect(String input) {

        parameters.put("Function", "CutDirect");
        parameters.put("Input", input);
    } // end of constructor
} // end of TransitionCutDirect
