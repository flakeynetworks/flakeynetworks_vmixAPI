package uk.co.flakeynetworks.vmix.status;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.IOException;
import java.util.*;

@Root(name="vmix",strict=false)
public class VMixStatus extends XMLParseable {

    @Element(name="version")
    @VMixStatusNode(name="version")
    private String vMixVersion;

    @Element(name="edition")
    @VMixStatusNode(name="edition")
    private String vMixEdition;

    @Element(name="preset",required = false)
    @VMixStatusNode(name="preset")
    private String preset;

    @ElementList(name="inputs")
    @VMixStatusListNode(name="inputs", type = Input.class)
    private List<Input> inputs = new ArrayList<>();

    @ElementList(name="overlays")
    @VMixStatusListNode(name="overlays", type = Overlay.class)
    private List<Overlay> overlays = new ArrayList<>();

    @Element(name="preview")
    @VMixStatusNode(name="previewInput", type = Integer.class)
    private int previewInput;

    @Element(name="active")
    @VMixStatusNode(name="active", type = Integer.class)
    private int activeInput;

    @Element(name="fadeToBlack")
    @VMixStatusNode(name="fadeToBlack", type = Boolean.class)
    private boolean isFadeToBlack;

    @ElementList(name="transitions")
    @VMixStatusListNode(name="transitions", type = Transition.class)
    private List<Transition> transitions = new ArrayList<>();

    @Element(name="recording")
    @VMixStatusNode(name = "recording", type = Boolean.class)
    private boolean isRecording;

    @Element(name="external")
    @VMixStatusNode(name="external", type = Boolean.class)
    private boolean isExternal;

    @Element(name="streaming")
    @VMixStatusNode(name="streaming", type = Boolean.class)
    private boolean isStreaming;

    @Element(name="playList")
    @VMixStatusNode(name="playList", type = Boolean.class)
    private boolean isPlaylist;

    @Element(name="multiCorder")
    @VMixStatusNode(name="multiCorder", type = Boolean.class)
    private boolean isMultiRecording;

    @Element(name="fullscreen")
    @VMixStatusNode(name="fullscreen", type = Boolean.class)
    private boolean isFullScreen;

    @ElementList(name="audio")
    @VMixStatusListNode(name="audio", type = Audio.class)
    private List<Audio> audio = new ArrayList<>();


    private Set<HostStatusChangeListener> listeners;


    public static VMixStatus decode(String message) {

        VMixStatus status = new VMixStatus();

        try {
            status.parseXML(message);
        } catch(IOException e) { return null; } // end of catch

        return status;
    } // end of decode


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


    public String getVMixVersion() { return vMixVersion; } // end of getVMixVersion
} // end of VMixStatus