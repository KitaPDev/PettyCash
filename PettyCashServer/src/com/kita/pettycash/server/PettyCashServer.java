package com.kita.pettycash.server;

import com.kita.lib.rpc.server.RPCServer;
import com.kita.pettycash.server.services.PettyCash;
import com.kita.pettycash.server.services.PettyCashUsage;
import com.kita.pettycash.server.services.User;

public class PettyCashServer {

    private static int PORT = 45678;

    public static void main(String[] args) {

        try {
            RPCServer.registerHandler(User.class);
            RPCServer.registerHandler(PettyCash.class);
            RPCServer.registerHandler(PettyCashUsage.class);
            System.out.println("Handlers registered.");

            RPCServer server = new RPCServer();

            System.out.println("Starting PettyCashServer");
            server.startServer(PORT);

        } catch(Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
