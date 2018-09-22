package uk.co.flakeynetworks.vmix.api.protocol;

import uk.co.flakeynetworks.vmix.VMixVersion;
import uk.co.flakeynetworks.vmix.api.TCPAPI;

import java.util.StringTokenizer;

public class Version20Protocol implements VMixTCPProtocol {


    private TCPAPI connection;


    public Version20Protocol(TCPAPI connection) {

        this.connection = connection;
    } // end of constructor


    @Override
    public void processMessage(String message) {

        System.out.println(message);
    } // end of processMessage


    @Override
    public VMixVersion decodeVersion(String response) {

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
