package uk.co.flakeynetworks.vmix.api.protocol;

import uk.co.flakeynetworks.vmix.VMixHost;
import uk.co.flakeynetworks.vmix.VMixVersion;
import uk.co.flakeynetworks.vmix.api.TCPAPI;
import uk.co.flakeynetworks.vmix.status.Input;
import uk.co.flakeynetworks.vmix.status.VMixStatus;

import java.util.StringTokenizer;

public class Version20Protocol implements VMixTCPProtocol {

    private TCPAPI connection;

    public Version20Protocol(TCPAPI connection) {

        this.connection = connection;
    } // end of constructor


    @Override
    public void processMessage(String message) {

        System.err.println(message);

        if(message == null) return;

        StringTokenizer tokenizer = new StringTokenizer(message, PROTOCOL_DELIMITER);
        if(!tokenizer.hasMoreTokens()) return;

        String header = tokenizer.nextToken();
        switch (header) {

            case "SUBSCRIBE":

                // Check the next status
                if(!tokenizer.hasMoreTokens()) return;

                String subscribeStatus = tokenizer.nextToken();
                switch(subscribeStatus) {

                    case "OK":
                        break;

                    case "ER":
                        break;
                } // end of switch
                break;

            case "TALLY":

                // Get the status
                if(!tokenizer.hasMoreTokens()) return;

                String tallyStatus = tokenizer.nextToken();
                if(tallyStatus.equals("OK")) {

                    if(!tokenizer.hasMoreTokens()) return;
                    String tallyString = tokenizer.nextToken();

                    VMixHost host = connection.getHost();
                    VMixStatus status = host.getStatus();

                    if(tallyString.length() != status.getNumberOfInputs()) {

                        if(!host.update()) {
                            // TODO DATA INCONSISTANCY HERE HANDLE THIS!
                            return;
                        } // end of if
                    } // end of if

                    // Update the vmix status
                    System.err.println("TALLY update:" + tallyString);


                    for(int i = 0; i < tallyString.length(); i++) {

                        Input input = status.getInput(i);

                        switch(tallyString.charAt(i)) {
                            case '0':
                                input.setIsPreview(false);
                                input.setIsProgram(false);
                                break;

                            case '1':
                                input.setIsPreview(true);
                                input.setIsProgram(false);
                                break;

                            case '2':
                                input.setIsPreview(false);
                                input.setIsProgram(true);
                                break;
                        } // end of switch
                    } // end of for
                } // end of if

                break;
        } // end of switch
    } // end of processMessage


    @Override
    public VMixVersion decodeVersion(String response) {

        System.out.println("Decoding: " + response);

        StringTokenizer tokenizer = new StringTokenizer(response, PROTOCOL_DELIMITER);
        if(tokenizer.countTokens() != 3) return null;

        String command = tokenizer.nextToken();
        if(!command.equals("VERSION")) return null;

        String status = tokenizer.nextToken();
        if(!status.equals("OK")) return null;

        String version = tokenizer.nextToken();
        return new VMixVersion(version);
    } // end of readVersion


    @Override
    public void subscribeTally() {

        String command = "SUBSCRIBE TALLY\r\n";
        connection.write(command);
    } // end of subscribeTally
} // end of Version20Protocol
