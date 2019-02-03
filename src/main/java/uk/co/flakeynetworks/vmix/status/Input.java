package uk.co.flakeynetworks.vmix.status;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("WeakerAccess")
@Root(strict=false)
public class Input extends XMLParseable implements Comparable<Input> {


    public static final String TYPE_BLANK = "blank";
    public static final String TYPE_VIDEO = "video";
    public static final String TYPE_IMAGE = "image";
    public static final String TYPE_PHOTO = "photos";
    public static final String TYPE_XAML = "xaml";
    public static final String TYPE_VIDEO_LIST = "videolist";
    public static final String TYPE_COLOR = "colour";
    public static final String TYPE_AUDIO_FILE = "audiofile";
    public static final String TYPE_FLASH = "flash";
    public static final String TYPE_POWERPOINT = "powerpoint";
    public static final String TYPE_VALUE = "value";

    @Attribute(name="key")
    @VMixStatusAttribute(name = "key")
    private String key;

    @Attribute(name="duration")
    @VMixStatusAttribute(name = "duration", type = Integer.class)
    private int duration;

    @Attribute(name="number")
    @VMixStatusAttribute(name = "number", type = Integer.class)
    private int number;

    @Attribute(required = false)
    @VMixStatusAttribute(name = "meterF1", type = Double.class)
    private double meterF1 = 0;

    @Attribute(required = false)
    @VMixStatusAttribute(name = "meterF2", type = Double.class)
    private double meterF2 = 0;

    @Attribute(required = false)
    @VMixStatusAttribute(name = "solo", type = Boolean.class)
    private boolean solo = false;

    @Attribute(required = false)
    @VMixStatusAttribute(name = "audiobusses")
    private String audiobusses;

    @Attribute(required = false)
    @VMixStatusAttribute(name = "volume", type = Double.class)
    private double volume = 0;

    @Attribute(required = false)
    @VMixStatusAttribute(name = "balance", type = Double.class)
    private double balance = 0;

    @Attribute
    @VMixStatusAttribute(name = "type")
    private String type;

    @Attribute
    @VMixStatusAttribute(name = "title")
    private String title;

    @Attribute
    @VMixStatusAttribute(name = "state")
    private String state;

    @Attribute
    @VMixStatusAttribute(name = "position", type = Integer.class)
    private int position;

    @Attribute(name="loop")
    @VMixStatusAttribute(name = "loop", type = Boolean.class)
    private boolean isLooped;

    private boolean program = false;
    private boolean preview = false;

    private boolean inputValid = true;

    private Set<InputStatusChangeListener> listeners = new HashSet<>();


    public String getName() { return title; } // end of getName
    public String getKey() { return key; } // end of getKey
    public int getDuration() { return duration; } // end of getDuration
    public int getNumber() { return number; } // end of getNumber

    protected void setName(String name) { this.title = name; } // end of setName
    protected void setType(String type) { this.type = type; } // end of setType

    public String getType() {

        String casetype = type.toLowerCase();
        switch (casetype) {
            case TYPE_AUDIO_FILE:
                return TYPE_AUDIO_FILE;
            case TYPE_BLANK:
                return TYPE_BLANK;
            case TYPE_COLOR:
                return TYPE_COLOR;
            case TYPE_FLASH:
                return TYPE_FLASH;
            case TYPE_IMAGE:
                return TYPE_IMAGE;
            case TYPE_PHOTO:
                return TYPE_PHOTO;
            case TYPE_POWERPOINT:
                return TYPE_POWERPOINT;
            case TYPE_VALUE:
                return TYPE_VALUE;
            case TYPE_VIDEO:
                return TYPE_VIDEO;
            case TYPE_VIDEO_LIST:
                return TYPE_VIDEO_LIST;
            case TYPE_XAML:
                return TYPE_XAML;
            default:
                return casetype;
        } // end of switch
    } // end of getType


    public String getState() { return state; } // end of getState
    public int getPosition() { return position; } // end of getPosition
    public boolean isLooped() { return isLooped; } // end of isLooped
    public boolean isProgram() { return program; } // end of isProgram
    public boolean isPreview() { return preview; } // end of isPreview
    public boolean isInputValid() { return inputValid; } // end of isValid


    public boolean addStatusChangeListener(InputStatusChangeListener listener) {

        return listeners.add(listener);
    } // end of addStatusChangeListener


    public boolean removeStatusChangeListener(InputStatusChangeListener listener) {

        return listeners.remove(listener);
    } // end of removeStatusChangeListener


    public void removeAllListeners() {
        listeners.clear();
    } // end of removeAllListeners


    public void setIsProgram(boolean active) {

        boolean oldValue = this.program;
        this.program = active;

        //if(oldValue != program) {
            for(InputStatusChangeListener listener: listeners) {

                listener.isProgramChange();
                listener.dataChanged();
            } // end for
        //} // end of if
    } // end of setIsActive


    public void setIsPreview(boolean active) {

        boolean oldValue = this.preview;
        this.preview = active;

        //if(oldValue != active) {

            for(InputStatusChangeListener listener: listeners) {

                listener.isPreviewChange();
                listener.dataChanged();
            } // end for
        //} // end of if
    } // end of setIsPreview

    @Override
    public boolean equals(Object object) {

        if(!(object instanceof Input)) return false;

        return equals((Input) object);
    } // end of equals

    public boolean equals(Input input) {

        return key.equals(input.key);
    } // end of equals


    public void notifyInputWasRemoved() {

        inputValid = false;

        for(InputStatusChangeListener listener: listeners) {

            listener.inputRemoved();
            listener.dataChanged();
        } // end for
    } // end of notifyInputWasRemoved


    public void update(Input newInput) {

        if(newInput == null) return;

        // Make sure the keys are the same
        if(!equals(newInput)) return;

        // Update all the values
        duration = newInput.duration;
        number = newInput.number;
        meterF1 = newInput.meterF1;
        meterF2 = newInput.meterF2;
        solo = newInput.solo;
        audiobusses = newInput.audiobusses;
        volume = newInput.volume;
        balance = newInput.balance;
        type = newInput.type;
        title = newInput.title;
        state = newInput.state;
        position = newInput.position;
        isLooped = newInput.isLooped;
        program = newInput.program;
        preview = newInput.preview;
        inputValid = newInput.inputValid;

        for(InputStatusChangeListener listener: listeners)
            listener.dataChanged();
    } // end of update


    @Override
    public int compareTo(Input o) {

        return number - o.number;
    } // end of compareTo


    @Override
    public int hashCode() {

        return key.hashCode();
    } // end of hashCode


    @Override
    public String toString() { return getNumber() + ": " + getName(); } // end of toString
} // end of Input
