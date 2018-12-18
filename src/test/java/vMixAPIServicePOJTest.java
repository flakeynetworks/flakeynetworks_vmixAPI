import org.junit.Test;
import uk.co.flakeynetworks.vmix.VMixHost;
import uk.co.flakeynetworks.vmix.api.web.VMixWebAPI;
import uk.co.flakeynetworks.vmix.api.web.poj.VMixAPIPOJ;
import uk.co.flakeynetworks.vmix.status.VMixStatus;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class vMixAPIServicePOJTest {

    @Test
    public void checkResponseForStatus() {

        URL url = null;
        try {
            url = new URL(VMixHost.DEFAULT_VMIX_PROTOCOL + VMixHost.DEFAULT_VMIX_ADDRESS + ":" + VMixHost.DEFAULT_VMIX_PORT + "/api/");
        } catch (MalformedURLException e) {
            assert false;
        } // end of catch

        VMixWebAPI api = new VMixAPIPOJ(url);

        try {
            VMixStatus status = api.getStatus();
            System.out.println(status.getVMixVersion());
        } catch (IOException e) {
            assert false;
        } // end of catch
    } // end of checkResponseForStatus
} // end of vMixAPIServicePOJTest
