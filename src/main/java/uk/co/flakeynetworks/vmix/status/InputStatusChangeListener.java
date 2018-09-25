package uk.co.flakeynetworks.vmix.status;

public abstract class InputStatusChangeListener {

    public void isProgramChange() { } // end of isProgramChange
    public void isPreviewChange() { } // end of isPreviewChange

    public void inputRemoved() { } // end of inputRemoved
    public void dataChanged() { } // end of dataChanged
} // end of InputStatusChangeListener
