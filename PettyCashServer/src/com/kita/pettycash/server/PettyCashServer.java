package com.kita.pettycash.server;

import com.kita.lib.rpc.server.RPCServer;
import com.kita.pettycash.server.services.User;

public class PettyCashServer {

    private static int PORT = 45678;

    public static void main(String[] args) {

        try {
            RPCServer.registerHandler(User.class);

            RPCServer server = new RPCServer();

            server.startServer(PORT);

        } catch(Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
