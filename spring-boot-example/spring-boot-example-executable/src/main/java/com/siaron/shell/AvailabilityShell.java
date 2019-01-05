package com.siaron.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

/**
 * @author xielongwang
 * @create 2019-01-0512:40 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@ShellComponent
public class AvailabilityShell {
    private boolean connected;

    @ShellMethod("Connect to the server.")
    public void connect(String user, String password) {
        connected = true;
    }

    @ShellMethod("Download the nuclear codes.")
    @ShellMethodAvailability("downloadAvailability")
    public void download() {
    }

    public Availability downloadAvailability() {
        return connected
                ? Availability.available()
                : Availability.unavailable("you are not connected");
    }

//    @ShellMethodAvailability({"download", "disconnect"})
//    public Availability availabilityCheck() {
//        return connected
//                ? Availability.available()
//                : Availability.unavailable("you are not connected");
//    }
}