package uk.co.flakeynetworks.vmix.api.exceptions;

import uk.co.flakeynetworks.vmix.VMixVersion;

public class FeatureNotAvailableException extends Exception {

    public FeatureNotAvailableException(VMixVersion minimumVersion, VMixVersion currentVersion) {

        super("This feature is not available in this current version of vMix. Minimum version: " + minimumVersion.toString() + ", Current Version: " + currentVersion.toString());
    } // end of constructor
} // end of FeatureNotAvailbaleException
