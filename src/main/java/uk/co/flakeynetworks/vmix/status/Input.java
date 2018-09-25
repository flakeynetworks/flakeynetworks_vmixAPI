package uk.co.flakeynetworks.vmix.status;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.util.HashSet;
import java.util.Set;

@Root(strict=false)
public class Input implements Comparable<Input> {

    @Attribute(name="key")
    private String key;

    @Attribute(name="duration")
    private int duration;

    @Attribute(name="number")
    private int number;

    @Attribute(required = false)
    private double meterF1 = 0;

    @Attribute(required = false)
    private double meterF2 = 0;

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

    private boolean program = false;
    private boolean preview = false;

    private boolean inputValid = true;

    private Set<InputStatusChangeListener> listeners = new HashSet<>();


    public String getName() { return title; } // end of getName
    public String getKey() { return key; } // end of getKey
    public int getDuration() { return duration; } // end of getDuration
    public int getNumber() { return number; } // end of getNumber
    public String getType() { return type; } // end of getType
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

        if(oldValue != program) {
            for(InputStatusChangeListener listener: listeners) {

                listener.isProgramChange();
                listener.dataChanged();
            } // end for
        } // end of if
    } // end of setIsActive


    public void setIsPreview(boolean active) {

        boolean oldValue = this.preview;
        this.preview = active;

        if(oldValue != preview) {

            for(InputStatusChangeListener listener: listeners) {

                listener.isPreviewChange();
                listener.dataChanged();
            } // end for
        } // end of if
    } // end of setIsPreview

    @Override
    public boolean equals(Object object) {

        if(!(object instanceof Input)) return false;

        return equals((Input) object);
    } // end of equals

    public boolean equals(Input input) {

        System.out.println("Checking");
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
} // end of Input
