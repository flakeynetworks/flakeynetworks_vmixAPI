package uk.co.flakeynetworks.vmix;

import uk.co.flakeynetworks.vmix.api.command.VMixCommand;
import uk.co.flakeynetworks.vmix.api.exceptions.FeatureNotAvailableException;
import uk.co.flakeynetworks.vmix.api.service.VMixAPIService;
import uk.co.flakeynetworks.vmix.api.service.VMixAPIServicePOJ;
import uk.co.flakeynetworks.vmix.api.web.VMixWebAPI;
import uk.co.flakeynetworks.vmix.status.HostStatusChangeListener;
import uk.co.flakeynetworks.vmix.status.VMixStatus;
import uk.co.flakeynetworks.web.ParameterStringBuilder;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class VMixHost {

    public static final String DEFAULT_VMIX_ADDRESS = "127.0.0.1";
    public static final int DEFAULT_VMIX_PORT = 8088;
    public static final String DEFAULT_VMIX_PROTOCOL = "http://";

    private String vMixAddress = DEFAULT_VMIX_ADDRESS;
    private URL vMixUrl;
    private int vMixPort = DEFAULT_VMIX_PORT;
    private VMixVersion version = VMixVersion.VERSION_13;
    private VMixStatus lastKnownStatus;


    private final Set<HostStatusChangeListener> listeners = new HashSet<>();


    public boolean addListener(HostStatusChangeListener listener) {

        return listeners.add(listener);
    } // end of addListener


    public boolean removeListener(HostStatusChangeListener listener) {
        return listeners.remove(listener);
    } // end of removeListener


    public void removeAllListeners() {
        listeners.clear();
    } // end of removeAllListener


    public VMixHost(String address, int webControllerPort) throws MalformedURLException {

        this.vMixAddress = address;
        this.vMixPort = webControllerPort;

        vMixUrl = new URL(DEFAULT_VMIX_PROTOCOL + vMixAddress + ":" + vMixPort + "/api/");
    } // end of constructor


    public VMixHost() {

        try {
            vMixUrl = new URL(DEFAULT_VMIX_PROTOCOL + vMixAddress + ":" + vMixPort + "/api/");
        } catch (MalformedURLException ignored) { }
    } // end of default constructor


    public boolean runCommand(VMixCommand command) throws IOException, FeatureNotAvailableException {

        // Basic validation
        if(command == null) return false;

        HttpURLConnection con = (HttpURLConnection) vMixUrl.openConnection();
        con.setRequestMethod("GET");

        con.setDoOutput(true);

        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(ParameterStringBuilder.getParamsString(command.getParameters()));
        out.flush();
        out.close();

        int status = con.getResponseCode();

        if(status == 200) return true;
        if(status == 500) return false;

        return false;
    } // end of runCommand


    public boolean runCommands(VMixCommand[] commands) throws IOException, FeatureNotAvailableException {

        // Try and run each command in order. If a command isn't successful return.
        for(VMixCommand command: commands) {
            if (!runCommand(command))
                return false;
        } // end of for

        return true;
    } // end of runCommands


    public boolean update() {

        VMixAPIService apiService = new VMixAPIServicePOJ();
        VMixWebAPI api = apiService.connect(vMixUrl);

        try {

            VMixStatus newStatus = api.getStatus();

            if(newStatus == null) return false;

            if(lastKnownStatus == null) {

                newStatus.setListeners(listeners);
                lastKnownStatus = newStatus;
            } else {
                lastKnownStatus.update(newStatus);
            } // end of else
        } catch (IOException ignored){ return false; } // end of catch

        return true;
    } // end of update


    public String getAddress() { return vMixAddress; } // end of getAddress

    public void setVersion(VMixVersion version) { this.version = version; } // end of setVersion

    public VMixVersion getVersion() { return version; } // end of getVersion

    public VMixStatus getStatus() { return lastKnownStatus; } // end of getStatus
} // end of VmixAPI
