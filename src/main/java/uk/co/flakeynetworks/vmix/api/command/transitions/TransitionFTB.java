package uk.co.flakeynetworks.vmix.api.command.transitions;

import uk.co.flakeynetworks.vmix.api.command.VMixCommand;

/**
 * This command will toggle fade to black on program
 */
public class TransitionFTB extends VMixCommand {

    public TransitionFTB() {

        parameters.put("Function", "FadeToBlack");
    } // end of constructor
} // end of TransitionFTB
