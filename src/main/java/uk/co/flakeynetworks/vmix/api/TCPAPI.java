package uk.co.flakeynetworks.vmix.api;

import uk.co.flakeynetworks.vmix.VMixHost;
import uk.co.flakeynetworks.vmix.VMixVersion;
import uk.co.flakeynetworks.vmix.api.protocol.VMixTCPProtocol;
import uk.co.flakeynetworks.vmix.api.protocol.Version20Protocol;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Set;


public class TCPAPI {

    public static final int TCP_PORT = 8099;
    public static final long DEFAULT_ECHO_PERIOD = 5000;

    private VMixHost host;
    private Socket socket;
    private OutputStreamWriter output;
    private BufferedReader input;
    private VMixTCPProtocol protocol = new Version20Protocol(this, host);
    private IncomingThread listeningThread;
    private CheckClosedThread checking;

    private int timeout = -1;

    private Set<TCPAPIListener> listeners = new HashSet<>();


    class CheckClosedThread extends Thread {

        @Override
        public void run() {

            while(true) {

                // Send out an echo
                if(!write("XMLTEXT vmix/version\r\n"))
                    return;

                try {
                    Thread.sleep(DEFAULT_ECHO_PERIOD);
                } catch(InterruptedException e) { return; } // end of catch

                if(Thread.interrupted()) return;

                // Check the time of the last mesasge
                long time = System.currentTimeMillis() - getProtocol().getLastMessageTimestamp();
                if(time > DEFAULT_ECHO_PERIOD + 2000) {

                    close();
                    return;
                } // end of if

                if(Thread.interrupted()) return;
            } // end of while
        } // end of run
    } // end of CheckClosedThread


    class IncomingThread extends Thread {

        @Override
        public void run() {

            while(true) {

                try {

                    if(input == null) {
                        close();
                        return;
                    } // end of if

                    String line = input.readLine();
                    if(line == null) {
                        close();
                        return;
                    } // end of if

                    protocol.processMessage(line);
                } catch (IOException e) {

                    // Attempt to reconnect
                    close();
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


    public void setTimeout(int timeout) {

        try {
            if (socket != null)
                socket.setSoTimeout(timeout);
            else
                this.timeout = timeout;
        } catch(SocketException ignored) { } // end of catch
    } // end of setTimeoutValue


    public void setKeepAlive(boolean truth) {

        try {
            if (socket != null)
                socket.setKeepAlive(truth);
        } catch(SocketException ignored) { } // end of catch
    } // end of setKeepAlive


    public synchronized boolean connect() {

        // TODO Do a check to see if we are already connected. Improve this to include other checks
        if(socket != null) return true;

        try {

            socket = new Socket();
            if(timeout > -1)
                socket.connect(new InetSocketAddress(host.getAddress(), TCP_PORT), timeout);
            else
                socket.connect(new InetSocketAddress(host.getAddress(), TCP_PORT));

            //socket.setTrafficClass(16);

            output = new OutputStreamWriter(new BufferedOutputStream(socket.getOutputStream()));
            input = new BufferedReader(new InputStreamReader(new BufferedInputStream(socket.getInputStream())));

            // Wait for the first line which should be the version number
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

            // Run something that can echo something
            checking = new CheckClosedThread();
            checking.start();
        } catch (IOException e) {

            close();
            return false;
        } // end of catch

        return true;
    } // end of connect


    public synchronized void close() {

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

        if(listeningThread != null)
            listeningThread = null;

        if(checking != null) {

            checking.interrupt();
            checking = null;
        } // end of if

        // Notify all the listeners that the connection was disconnected
        for(TCPAPIListener listener: listeners)
            listener.disconnected();
    } // end of close


    public boolean write(String writeTo) {

        try {
            output.write(writeTo);
            output.flush();

            return true;
        } catch (IOException | NullPointerException ignored) {

            // Restart the connection
            close();
            ignored.printStackTrace();

            return false;
        } // end of catch
    } // end of write


    public VMixTCPProtocol getProtocol() { return protocol; } // end of getProtocol


    public VMixHost getHost() { return host; } // end of getHost
} // end of TCPAPI
