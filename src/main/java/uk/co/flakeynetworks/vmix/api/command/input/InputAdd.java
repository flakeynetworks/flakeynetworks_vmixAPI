package uk.co.flakeynetworks.vmix.api.command.input;

import uk.co.flakeynetworks.vmix.api.command.VMixCommand;

public class InputAdd extends VMixCommand {

    public InputAdd() {

        parameters.put("Function", "AddInput");
    } // end of constructor


    public InputAdd addColor(String color) {

        parameters.put("Value", "Colour|" + color);
        return this;
    } // end of addColor
} // end of Input.add
