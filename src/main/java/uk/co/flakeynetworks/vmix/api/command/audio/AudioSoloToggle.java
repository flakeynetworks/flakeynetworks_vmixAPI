package uk.co.flakeynetworks.vmix.api.command.audio;

import uk.co.flakeynetworks.vmix.api.command.VMixCommand;
import uk.co.flakeynetworks.vmix.status.Input;

public class AudioSoloToggle extends VMixCommand {

    private String input;

    public AudioSoloToggle(String input) {

        this.input = input;

        parameters.put("Function", "Solo");
        parameters.put("Input", input);
    } // end of constructor


    public AudioSoloToggle(Input input) {

        this.input = input.getKey();

        parameters.put("Function", "Solo");
        parameters.put("Input", this.input);
    } // end of constructor
} // end of AudioSoloToggle
