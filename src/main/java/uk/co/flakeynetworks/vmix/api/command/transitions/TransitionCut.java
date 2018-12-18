package uk.co.flakeynetworks.vmix.api.command.transitions;

import uk.co.flakeynetworks.vmix.api.command.VMixCommand;

public class TransitionCut extends VMixCommand {

    public TransitionCut() {

        parameters.put("Function", "Cut");
    } // end of constructor
} // end of TransitionCut
