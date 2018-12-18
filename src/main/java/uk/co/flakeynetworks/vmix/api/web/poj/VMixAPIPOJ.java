package uk.co.flakeynetworks.vmix.api.web.poj;

import uk.co.flakeynetworks.vmix.api.web.VMixWebAPI;
import uk.co.flakeynetworks.vmix.status.VMixStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class VMixAPIPOJ implements VMixWebAPI {


    private URL url;


    public VMixAPIPOJ(URL url) {

        this.url = url;
    } // end of VMixAPIPOJ


    @Override
    public VMixStatus getStatus() throws IOException {

        // Make a connection to vmix
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        con.setDoOutput(false);

        int responseCode = con.getResponseCode();

        if(responseCode != 200) throw new IOException("Exception, got an unexpected response code: " + responseCode);


        // Get the response
        BufferedReader response = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String line;
        StringBuilder builder = new StringBuilder();

        while((line = response.readLine()) != null)
            builder.append(line);

        String message = builder.toString();

        // Parse the response
        VMixStatus status = VMixStatus.decode(message);
        return status;
    } // end of getStatus
} // end of VMixAPIPOJ
