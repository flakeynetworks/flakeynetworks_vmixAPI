package uk.co.flakeynetworks.vmix.status;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class Transition {

    @Attribute
    private int number;

    @Attribute
    private String effect;

    @Attribute
    private int duration;
} // end of Transition
