import org.junit.Test;
import uk.co.flakeynetworks.vmix.VMixHost;
import uk.co.flakeynetworks.vmix.api.TCPAPI;

import java.io.IOException;
import java.net.MalformedURLException;

public class vMixTCPTest {

    @Test
    public void testTCPConnection() {

        try {
            VMixHost host = new VMixHost("127.0.0.1", 8088);

            TCPAPI tcpConnection = new TCPAPI(host);

            if(!tcpConnection.connect()) assert false;

            tcpConnection.getProtocol().subscribeTally();

            try {
                Thread.sleep(2000);
            } catch(InterruptedException e) {} // end of catch
        } catch(MalformedURLException e) {
            assert false;
        } // end of catch
    } // end of testTCPConnection
} // end of vMixTCPTest
