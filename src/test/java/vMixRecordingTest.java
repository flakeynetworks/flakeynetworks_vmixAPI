import org.junit.Test;
import uk.co.flakeynetworks.vmix.VMixHost;
import uk.co.flakeynetworks.vmix.api.command.recording.RecordingStart;
import uk.co.flakeynetworks.vmix.api.command.recording.RecordingStop;
import uk.co.flakeynetworks.vmix.api.exceptions.FeatureNotAvailableException;
import uk.co.flakeynetworks.vmix.status.VMixStatus;

import java.io.IOException;

public class vMixRecordingTest {

    @Test
    public void testRecording() {

        VMixHost host = new VMixHost();

        try {

            assert host.update();

            // Make sure vmix isn't recording
            if(host.getStatus().isRecording())
                host.runCommand(new RecordingStop());

            // Start vmix recording
            if(!host.runCommand(new RecordingStart()))
                assert false;

            // Wait for the command to take affect
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) { } // end of catch

            assert host.update();

            // Check that vmix is recording
            assert host.getStatus().isRecording();

            // Stop vmix recording
            host.runCommand(new RecordingStop());

            // Wait for the command to take effect
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) { } // end of catch

            // Check that vmix isn't recording
            assert !host.getStatus().isRecording();
        } catch (IOException | FeatureNotAvailableException e) {
            assert false;
        } // end of catch
    } // end of testRecording
} // end of vMixRecordingTest
