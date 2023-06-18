package com.sof_eng.Util;

import java.io.IOException;
import java.net.ServerSocket;
/*用于寻找39000-40000之间可用的端口*/
public class RandomFreePort {
    public RandomFreePort() {
    }

    public static int findRandomFreePort(int minPort, int maxPort) {
        int portRange = maxPort - minPort + 1;

        for(int i = 0; i < portRange; ++i) {
            int randomPort = (int)(Math.random() * (double)portRange) + minPort;
            if (isPortAvailable(randomPort)) {
                return randomPort;
            }
        }

        throw new RuntimeException("No free port available in the specified range.");
    }

    public static boolean isPortAvailable(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.close();
            return true;
        } catch (IOException var2) {
            return false;
        }
    }
}
