package uk.co.flakeynetworks.vmix.api;

import uk.co.flakeynetworks.vmix.VMixHost;
import uk.co.flakeynetworks.vmix.VMixVersion;
import uk.co.flakeynetworks.vmix.api.protocol.VMixTCPProtocol;
import uk.co.flakeynetworks.vmix.api.protocol.Version20Protocol;

import java.io.*;
import java.net.Socket;


public class TCPAPI {

    public static final int TCP_PORT = 8099;

    private VMixHost host;
    private OutputStreamWriter output;
    private BufferedReader input;
    private VMixTCPProtocol protocol = new Version20Protocol(this);
    private IncomingThread listeningThread = new IncomingThread();


    class IncomingThread extends Thread {

        @Override
        public void run() {

            while(true) {

                try {

                    String line = input.readLine();
                    if(line == null) {
                        // TODO Assume the connection was closed? and try to reconnect
                        // NOTIFY A LISTENER THE CONNECTION WAS CLOSED
                    }

                    protocol.processMessage(line);
                } catch (IOException e) {

                    // Attempt to reconnect

                    // TODO loop and keep trying to connect
                    // NOTIFY A LISTENER THE CONNECTION WAS CLOSED
                    return;
                } // end of catch
            } // end of while
        } // end of run
    } // end of IncomingThread


    public TCPAPI(VMixHost host) {

        this.host = host;
    } // end of constructor


    public boolean connect() {

        try {

            Socket socket = new Socket(host.getAddress(), TCP_PORT);

            output = new OutputStreamWriter(new BufferedOutputStream(socket.getOutputStream()));
            input = new BufferedReader(new InputStreamReader(new BufferedInputStream(socket.getInputStream())));

            // Wait for the first line which should be the version number

            // TODO MAYBE SHOULD ADD A TIMEOUT HERE
            String firstResponse = input.readLine();
            if(firstResponse == null) return false;

            VMixVersion version = protocol.decodeVersion(firstResponse);

            if(version == null) return false;
            host.setVersion(version);

            // TODO NOTIFY A LISTENER THE CONNECTION WAS OPENED

            listeningThread.start();
        } catch (IOException e) {

            return false;
        } // end of catch

        return true;
    } // end of connect


    public void write(String writeTo) {

        try {
            output.write(writeTo);
            output.flush();
        } catch (IOException ignored) {

            ignored.printStackTrace();
            // TODO might need to check on the network connection here.
        } // end of catch
    } // end of write


    public VMixTCPProtocol getProtocol() { return protocol; } // end of getProtocol


    public VMixHost getHost() { return host; } // end of getHost
} // end of TCPAPI
