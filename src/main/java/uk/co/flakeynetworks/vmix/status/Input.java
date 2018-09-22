package uk.co.flakeynetworks.vmix.status;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class Input {

    @Attribute(name="key")
    private String key;

    @Attribute(name="duration")
    private int duration;

    @Attribute(name="number")
    private int number;

    @Attribute(required = false)
    private int meterF1 = 0;

    @Attribute(required = false)
    private int meterF2 = 0;

    @Attribute(required = false)
    private boolean solo = false;

    @Attribute(required = false)
    private String audiobusses;

    @Attribute(required = false)
    private int volume = 0;

    @Attribute(required = false)
    private int balance = 0;

    @Attribute
    private String type;

    @Attribute
    private String title;

    @Attribute
    private String state;

    @Attribute
    private int position;

    @Attribute(name="loop")
    private boolean isLooped;


    public String getName() { return title; } // end of getName
    public String getKey() { return key; } // end of getKey
    public int getDuration() { return duration; } // end of getDuration
    public int getNumber() { return number; } // end of getNumber
    public String getType() { return type; } // end of getType
    public String getState() { return state; } // end of getState
    public int getPosition() { return position; } // end of getPosition
    public boolean isLooped() { return isLooped; } // end of isLooped
} // end of Input
