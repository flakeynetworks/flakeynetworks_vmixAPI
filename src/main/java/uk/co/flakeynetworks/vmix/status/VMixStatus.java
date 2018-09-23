package uk.co.flakeynetworks.vmix.status;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

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

    public boolean isRecording() {

        return isRecording;
    } // end of isRecording


    public void update(VMixStatus newStatus) {

        vMixVersion = newStatus.vMixVersion;
        vMixEdition = newStatus.vMixEdition;
        preset = newStatus.preset;

        // Go through the last known inputs, if they are still there then update them
        for(Input input: inputs) {

            if(newStatus.inputs.contains(input));

            // TODO COMPLETE THE UPDATING
        } // end of for
    } // end of update


    public int getNumberOfInputs() { return inputs.size(); } // end of getNumberOfInputs


    public Input getInput(int index) { return inputs.get(index); } // end of getInput
} // end of VMixStatus