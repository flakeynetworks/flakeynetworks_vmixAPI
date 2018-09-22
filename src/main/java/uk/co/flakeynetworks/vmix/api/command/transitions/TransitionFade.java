package uk.co.flakeynetworks.vmix.api.command.transitions;

import uk.co.flakeynetworks.vmix.api.command.VMixCommand;

public class TransitionFade extends VMixCommand {

    /**
     * Default Fade duration in milliseconds
     */
    private int DEFAULT_FADE_DURATION = 1000;
    private final int fadeDuration;
    private final String input;


    public TransitionFade() {

        fadeDuration = DEFAULT_FADE_DURATION;
        input = null;

        parameters.put("Function", "Fade");
        parameters.put("Duration", String.valueOf(fadeDuration));
    } // end of constructor


    public TransitionFade(int duration) {

        fadeDuration = duration;
        input = null;

        parameters.put("Function", "Fade");
        parameters.put("Duration", String.valueOf(fadeDuration));
    } // end of constructor


    public TransitionFade(String input, int duration) {

        fadeDuration = duration;
        this.input = input;

        parameters.put("Function", "Fade");
        parameters.put("Duration", String.valueOf(fadeDuration));
        parameters.put("Input", input);
    } // end of constructor
} // end of TransitionFade
