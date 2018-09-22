package uk.co.flakeynetworks.vmix.api.command.audio;

import uk.co.flakeynetworks.vmix.api.command.VMixCommand;

/**
 * Command to toggle on and off the Master Audio
 */
public class AudioMasterToggle extends VMixCommand {

    public AudioMasterToggle() {

        parameters.put("Function", "MasterAudio");
    } // end of constructor
} // end of AudioMasterToggle
