package uk.co.flakeynetworks.vmix.api;

import uk.co.flakeynetworks.vmix.VMixHost;
import uk.co.flakeynetworks.vmix.VMixVersion;
import uk.co.flakeynetworks.vmix.api.protocol.VMixTCPProtocol;
import uk.co.flakeynetworks.vmix.api.protocol.Version20Protocol;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;


public class TCPAPI {

    public static final int TCP_PORT = 8099;

    private VMixHost host;
    private Socket socket;
    private OutputStreamWriter output;
    private BufferedReader input;
    private VMixTCPProtocol protocol = new Version20Protocol(this);
    private IncomingThread listeningThread;

    private Set<TCPAPIListener> listeners = new HashSet<>();


    class IncomingThread extends Thread {

        @Override
        public void run() {

            while(true) {

                try {

                    String line = input.readLine();
                    if(line == null) {

                        close();
                        // TODO Assume the connection was closed? and try to reconnect
                    } // end of if

                    protocol.processMessage(line);
                } catch (IOException e) {

                    // Attempt to reconnect
                    close();

                    // TODO loop and keep trying to connect
                    return;
                } // end of catch
            } // end of while
        } // end of run
    } // end of IncomingThread


    public TCPAPI(VMixHost host) {

        this.host = host;
    } // end of constructor


    public boolean addListener(TCPAPIListener listener) {

        return listeners.add(listener);
    } // end of addListener


    public boolean removeListener(TCPAPIListener listener) {

        return listeners.remove(listener);
    } // end of removeListener


    public void removeAllListeners() {

        listeners.clear();
    } // end of removeAllListeners


    public synchronized boolean connect() {

        // TODO Do a check to see if we are already connected. Improve this to include other checks
        if(socket != null) return true;

        try {

            socket = new Socket(host.getAddress(), TCP_PORT);

            output = new OutputStreamWriter(new BufferedOutputStream(socket.getOutputStream()));
            input = new BufferedReader(new InputStreamReader(new BufferedInputStream(socket.getInputStream())));

            // Wait for the first line which should be the version number

            // TODO MAYBE SHOULD ADD A TIMEOUT HERE
            String firstResponse = input.readLine();
            if(firstResponse == null) {

                close();
                return false;
            } // end of if

            VMixVersion version = protocol.decodeVersion(firstResponse);

            if(version == null) {

                close();
                return false;
            } // end of if

            host.setVersion(version);

            // Notify all the listeners that a connection was successfully made
            for(TCPAPIListener listener: listeners)
                listener.connected();

            listeningThread = new IncomingThread();
            listeningThread.start();
        } catch (IOException e) {

            close();
            return false;
        } // end of catch

        return true;
    } // end of connect


    public synchronized void close() {

        if(listeningThread != null) {

            listeningThread.stop();
            listeningThread = null;
        } // end of if

        if(output != null) {
            try {
                output.close();
                output = null;
            } catch (IOException ignored) {
            } // end of catch
        } // end of if

        if(input != null) {
            try {
                input.close();
                input = null;
            } catch (IOException ignored) {
            } // end of catch
        } // end of if

        if(socket != null) {
            try {
                socket.close();
                socket = null;
            } catch (IOException ignored) {
            } // end of catch
        } // end of if

        // Notify all the listeners that the connection was disconnected
        for(TCPAPIListener listener: listeners)
            listener.disconnected();
    } // end of close


    public void write(String writeTo) {

        try {
            output.write(writeTo);
            output.flush();
        } catch (IOException ignored) {

            // Restart the connection
            close();
            ignored.printStackTrace();
        } // end of catch
    } // end of write


    public VMixTCPProtocol getProtocol() { return protocol; } // end of getProtocol


    public VMixHost getHost() { return host; } // end of getHost
} // end of TCPAPI
