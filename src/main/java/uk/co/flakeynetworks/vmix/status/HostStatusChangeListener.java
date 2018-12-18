package uk.co.flakeynetworks.vmix.status;

public abstract class HostStatusChangeListener {

    public void inputRemoved(Input input) {};
    public void inputAdded(Input input) {};
    public void statusChanged() {};
} // end of HostStatusChangeListener
