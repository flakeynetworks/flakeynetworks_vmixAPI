package uk.co.flakeynetworks.vmix.api.command.audio;

import uk.co.flakeynetworks.vmix.api.command.VMixCommand;

/**
 * Command to set the master audio level. Values between 0-100
 */
public class AudioInputVolume extends VMixCommand {

    private String input;
    private int level;

    public AudioInputVolume(String input, int level) {

        this.input = input;
        this.level = level;

        parameters.put("Function", "SetVolume");
        parameters.put("Input", input);
        parameters.put("Value", String.valueOf(level));
    } // end of constructor
} // end of AudioInputVolume
