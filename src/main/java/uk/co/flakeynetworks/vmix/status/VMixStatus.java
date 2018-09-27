package uk.co.flakeynetworks.vmix.status;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.*;

@Root(name="vmix",strict=false)
public class VMixStatus {

    @Element(name="version")
    private String vMixVersion;

    @Element(name="edition")
    private String vMixEdition;

    @Element(name="preset",required = false)
    private String preset;

    @ElementList(name="inputs")
    private List<Input> inputs;

    @ElementList(name="overlays")
    private List<Overlay> overlays;

    @Element(name="preview")
    private int previewInput;

    @Element(name="active")
    private int activeInput;

    @Element(name="fadeToBlack")
    private boolean isFadeToBlack;

    @ElementList(name="transitions")
    private List<Transition> transitions;

    @Element(name="recording")
    private boolean isRecording;

    @Element(name="external")
    private boolean isExternal;

    @Element(name="streaming")
    private boolean isStreaming;

    @Element(name="playList")
    private boolean isPlaylist;

    @Element(name="multiCorder")
    private boolean isMultiRecording;

    @Element(name="fullscreen")
    private boolean isFullScreen;

    @ElementList(name="audio")
    private List<Audio> audio;


    private Set<HostStatusChangeListener> listeners;

    public void setListeners(Set<HostStatusChangeListener> listeners) {

        this.listeners = listeners;
    } // end of setListeners


    public boolean isRecording() {

        return isRecording;
    } // end of isRecording


    public void update(VMixStatus newStatus) {

        vMixVersion = newStatus.vMixVersion;
        vMixEdition = newStatus.vMixEdition;
        preset = newStatus.preset;

        // Go through the last known inputs, if they are still there then update them
        // TODO Put this into a function so that we don't have repeatative code below
        Iterator<Input> currentInputs = inputs.iterator();
        while(currentInputs.hasNext()) {

            Input input = currentInputs.next();

            if(newStatus.inputs.contains(input)) {

                Input newInput = newStatus.getInput(input.getKey());
                input.update(newInput);
            } else {
                currentInputs.remove();

                // Notify all the listeners
                input.notifyInputWasRemoved();

                // Notify all the listeners that an input was added
                if(listeners != null) {
                    for (HostStatusChangeListener listener : listeners)
                        listener.inputRemoved(input);
                } // end of if
            } // end of else
        } // end of for

        // See if there are any new inputs
        for(Input newInput: newStatus.inputs) {

            if(inputs.contains(newInput)) continue;

            inputs.add(newInput);

            // Notify all the listeners that an input was added
            if(listeners != null) {
                for (HostStatusChangeListener listener : listeners)
                    listener.inputAdded(newInput);
            } // end of if
        } // end of for


        // Sort the inputs based on number
        Collections.sort(inputs);


        previewInput = newStatus.previewInput;
        activeInput = newStatus.activeInput;
        isFadeToBlack = newStatus.isFadeToBlack;
        isRecording = newStatus.isRecording;
        isStreaming = newStatus.isStreaming;
        isExternal = newStatus.isExternal;
        isPlaylist = newStatus.isPlaylist;
        isMultiRecording = newStatus.isMultiRecording;
        isFullScreen = newStatus.isFullScreen;


        // Go through the last known overlays, if they are still there then update them
        Iterator<Overlay> currentOverlays = overlays.iterator();
        while(currentOverlays.hasNext()) {

            Overlay overlay = currentOverlays.next();

            if(newStatus.overlays.contains(overlay)) {

                Overlay newOverlay = newStatus.getOverlay(overlay.getNumber());
                overlay.update(newOverlay);
            } else {

                currentOverlays.remove();
            } // end of else
        } // end of for


        // See if there are any new overlays
        for(Overlay newOverlay: newStatus.overlays) {

            if(overlays.contains(newOverlay)) continue;

            overlays.add(newOverlay);
        } // end of for

        // Sort the overlays based on number
        Collections.sort(overlays);


        // Go through the last known transitions, if they are still there then update them
        Iterator<Transition> currentTransitions = transitions.iterator();
        while(currentTransitions.hasNext()) {

            Transition transition = currentTransitions.next();

            if(newStatus.transitions.contains(transition)) {

                Transition newTransition = newStatus.getTransition(transition.getNumber());
                transition.update(newTransition);
            } else {

                currentTransitions.remove();
            } // end of else
        } // end of for


        // See if there are any new overlays
        for(Transition newTranition: newStatus.transitions) {

            if(transitions.contains(newTranition)) continue;

            transitions.add(newTranition);
        } // end of for

        // Sort the overlays based on number
        Collections.sort(transitions);

        // TODO have to find a way to update the audio
    } // end of update


    public Transition getTransition(int number) {

        for(Transition transition: transitions) {

            if(transition.getNumber() == number)
                return transition;
        } // end of for

        return null;
    } // end of getTransition


    public int getNumberOfInputs() { return inputs.size(); } // end of getNumberOfInputs


    public Input getInput(int index) {

        if(index < 0 || index >= inputs.size()) return null;

        return inputs.get(index);
    } // end of getInput


    public Input getInput(String key) {

        for(Input input: inputs) {
            if(input.getKey().equals(key)) return input;
        } // end of for

        return null;
    } // end of getInput


    public Overlay getOverlay(int number) {

        for(Overlay overlay: overlays) {

            if(overlay.getNumber() == number) return overlay;
        } // end of for

        return null;
    } // end of getOverlay
} // end of VMixStatus