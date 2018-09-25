package uk.co.flakeynetworks.vmix.status;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class Audio {

    @Attribute
    private double volume;

    @Attribute
    private boolean muted;

    @Attribute
    private double meterF1;

    @Attribute
    private double meterF2;

    @Attribute
    private double headphonesVolume;
} // end of Audio
