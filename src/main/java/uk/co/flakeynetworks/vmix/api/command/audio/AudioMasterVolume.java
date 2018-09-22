package uk.co.flakeynetworks.vmix.api.command.audio;

import uk.co.flakeynetworks.vmix.api.command.VMixCommand;

/**
 * Command to set the master audio level. Values between 0-100
 */
public class AudioMasterVolume extends VMixCommand {


    private int level;
    /**
     *
     * @param level 0 - 100
     */
    public AudioMasterVolume(int level) {

        this.level = level;

        parameters.put("Function", "SetMasterVolume");
        parameters.put("Value", String.valueOf(level));
    } // end of constructor
} // end of AudioMasterVolume
