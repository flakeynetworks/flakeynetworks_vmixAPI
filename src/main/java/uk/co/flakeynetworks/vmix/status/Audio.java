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
    private int meterF1;

    @Attribute
    private int meterF2;

    @Attribute
    private int headphonesVolume;
} // end of Audio
