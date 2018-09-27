import org.junit.Test;
import uk.co.flakeynetworks.vmix.VMixHost;
import uk.co.flakeynetworks.vmix.api.TCPAPI;
import uk.co.flakeynetworks.vmix.api.command.input.InputAdd;
import uk.co.flakeynetworks.vmix.api.exceptions.FeatureNotAvailableException;
import uk.co.flakeynetworks.vmix.status.HostStatusChangeListener;
import uk.co.flakeynetworks.vmix.status.Input;

import java.io.IOException;

public class TestListeners {

    @Test
    public void testHostListener() {

        try {

            VMixHost host = new VMixHost("127.0.0.1", 8088);

            TCPAPI tcpConnection = new TCPAPI(host);
            assert tcpConnection.connect();

            assert host.update();

            host.addListener(new HostStatusChangeListener() {
                @Override
                public void inputRemoved(Input input) {

                    assert false;
                } // end of inputRemoved

                @Override
                public void inputAdded(Input input) {

                    Thread.interrupted();
                } // end of inputAdded
            });

            assert host.getStatus().getNumberOfListeners() == 1;

            // Listen for tally changes
            tcpConnection.getProtocol().subscribeTally();


            host.runCommand((new InputAdd()).addColor("#000"));

            try {
                Thread.sleep(2000);
            } catch(InterruptedException e) {
                assert true;
                return;
            } // end of catch

            assert false;
        } catch (FeatureNotAvailableException | IOException e) {

            assert false;
        } // end of catch
    } // end of testHostListener
} // end of TestListeners
