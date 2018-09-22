import org.junit.Test;
import uk.co.flakeynetworks.vmix.VMixHost;
import uk.co.flakeynetworks.vmix.api.TCPAPI;
import uk.co.flakeynetworks.vmix.api.command.InputSendToPreview;
import uk.co.flakeynetworks.vmix.api.command.transitions.TransitionCut;
import uk.co.flakeynetworks.vmix.api.command.transitions.TransitionCutDirect;
import uk.co.flakeynetworks.vmix.api.command.transitions.TransitionFade;
import uk.co.flakeynetworks.vmix.api.exceptions.FeatureNotAvailableException;
import uk.co.flakeynetworks.vmix.status.Input;
import uk.co.flakeynetworks.vmix.status.InputStatusChangeListener;
import uk.co.flakeynetworks.vmix.status.VMixStatus;

import java.io.IOException;
import java.net.MalformedURLException;

public class vMixTCPTest {

    @Test
    public void testTCPConnection() {

        try {
            VMixHost host = new VMixHost("127.0.0.1", 8088);

            TCPAPI tcpConnection = new TCPAPI(host);

            assert tcpConnection.connect();
        } catch(MalformedURLException e) {
            assert false;
        } // end of catch
    } // end of testTCPConnection


    @Test
    public void testTallyUpdate() {

        try {
            VMixHost host = new VMixHost("127.0.0.1", 8088);

            TCPAPI tcpConnection = new TCPAPI(host);

            assert tcpConnection.connect();
            assert host.update();

            VMixStatus status = host.getStatus();

            // Get the first input
            Input firstInput = status.getInput(0);

            // Add a listener to the input
            InputStatusChangeListener statusListener = new InputStatusChangeListener() {
                @Override
                public void isProgramChange() {

                    System.out.println("Input 1 changed program status: " + firstInput.isProgram());
                } // end of isProgramChange

                @Override
                public void isPreviewChange() {

                    System.out.println("Input 2 changed program status: " + firstInput.isPreview());
                } // end of isPreviewChange
            };

            firstInput.addStatusChangeListener(statusListener);

            // Subscribe for tally changes
            tcpConnection.getProtocol().subscribeTally();

            // Put the second input into program
            Input secondInput = status.getInput(1);
            assert host.runCommand(new TransitionCutDirect(secondInput));

            // Put the first input into preview
            assert host.runCommand(new InputSendToPreview(firstInput));

            // Perform a cut
            assert host.runCommand(new TransitionCut());

            try {
                Thread.sleep(2000);
            } catch(InterruptedException e) {} // end of catch
        } catch(FeatureNotAvailableException | IOException e) {
            assert false;
        } // end of catch
    } // end of testTallyUpdate
} // end of vMixTCPTest
