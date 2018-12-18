package uk.co.flakeynetworks.vmix.status;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class Audio extends XMLParseable {

    @Attribute
    @VMixStatusAttribute(name = "volume", type = Integer.class)
    private double volume;

    @Attribute
    @VMixStatusAttribute(name = "muted", type = Boolean.class)
    private boolean muted;

    @Attribute
    @VMixStatusAttribute(name = "meterF1", type = Double.class)
    private double meterF1;

    @Attribute
    @VMixStatusAttribute(name = "meterF2", type = Double.class)
    private double meterF2;

    @Attribute
    @VMixStatusAttribute(name = "headphonesVolumes", type = Double.class)
    private double headphonesVolume;
} // end of Audio
