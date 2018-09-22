package uk.co.flakeynetworks.vmix.api.command;

import uk.co.flakeynetworks.vmix.VMixVersion;

/**
 * Subscribe will set this to listen for certain changes in data. Only available in versions 20 and above.
 */
public class Subscribe extends VMixCommand {


    {
        minVersion = VMixVersion.VERSION_20;
    }
}
