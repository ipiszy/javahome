/*
 * SnifferApp.java
 */

package sniffer;

import core.Capture;
import core.Storage;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class SnifferApp extends SingleFrameApplication {
    private SnifferView view;
    private Storage packetStorage;
    private Capture packetCapturer;

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        packetStorage = new Storage();
        packetCapturer = new Capture(packetStorage);
        view = new SnifferView(this);
        view.setStorage(packetStorage);
        view.setCapturer(packetCapturer);
        show(view);
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of SnifferApp
     */
    public static SnifferApp getApplication() {
        return Application.getInstance(SnifferApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(SnifferApp.class, args);
    }
}
