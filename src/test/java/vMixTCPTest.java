import org.junit.Test;
import uk.co.flakeynetworks.vmix.VMixHost;
import uk.co.flakeynetworks.vmix.api.TCPAPI;
import uk.co.flakeynetworks.vmix.api.command.input.InputAdd;
import uk.co.flakeynetworks.vmix.api.command.input.InputRemove;
import uk.co.flakeynetworks.vmix.api.command.input.InputSendToPreview;
import uk.co.flakeynetworks.vmix.api.command.transitions.TransitionCut;
import uk.co.flakeynetworks.vmix.api.command.transitions.TransitionCutDirect;
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


    @Test
    public void testUpdate() {

        try {
            VMixHost host = new VMixHost("127.0.0.1", 8088);

            TCPAPI tcpConnection = new TCPAPI(host);

            assert tcpConnection.connect();
            assert host.update();

            VMixStatus status = host.getStatus();

            // Make sure there are three inputs
            if(status.getNumberOfInputs() != 3) {
                for(int i = 0; i < 3 - status.getNumberOfInputs(); i++)
                    host.runCommand((new InputAdd()).addColor("#000000"));
            } // end of if


            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {} // end of catch


            // Get the first input
            Input firstInput = status.getInput(0);
            Input thirdInput = status.getInput(2);
            if(thirdInput == null)
                assert false;

            // Add a listener to the input
            firstInput.addStatusChangeListener(new InputStatusChangeListener() {

                @Override
                public void inputRemoved() {
                    assert false;
                } // end of inputRemoved
            });


            thirdInput.addStatusChangeListener(new InputStatusChangeListener() {

                @Override
                public void inputRemoved() {
                    assert true;
                } // end of inputRemoved
            });

            // Remove an input
            assert host.runCommand(new InputRemove(thirdInput));

            host.update();

            try {
                Thread.sleep(1000);
            } catch(InterruptedException ignored) {
            } // end of catch
        } catch(FeatureNotAvailableException | IOException e) {
            assert false;
        } // end of catch
    } // end of testUpdate
} // end of vMixTCPTest
