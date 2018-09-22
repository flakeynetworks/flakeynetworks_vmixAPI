package uk.co.flakeynetworks.vmix.api.command.audio;

import uk.co.flakeynetworks.vmix.api.command.VMixCommand;

/**
 * Command used to toggle the audio for an audio input
 */
public class AudioInputToggle extends VMixCommand {

    private final String inputName;

    public AudioInputToggle(String inputName) {

        this.inputName = inputName;

        parameters.put("Function", "Audio");
        parameters.put("Input", inputName);
    } // end of constructor
} // end of AudioInputToggle
