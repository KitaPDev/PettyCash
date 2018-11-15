package com.kitap.client.console;

import com.kita.lib.rpc.BEANRemoteExecution;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ConsoleClient {

    private static String HOST = "127.0.0.1";
    private static int PORT = 45678;

    public static void main(String[] args) throws IOException {

        Socket clientSocket = null;
        String strDirectory = "D:/kitap/dev/java/PettyCash/Clients/OutputStream/";
        String strFileName = "test.bin";

        String strClassName = "Greeting";
        String strMethodName = "who";
        String strParameter = "Kita";
        List lsParameters = new ArrayList();

        lsParameters.add(strParameter);

        try {
            BEANRemoteExecution remoteExecution = new BEANRemoteExecution(strClassName, strMethodName, lsParameters);

            clientSocket = new Socket(HOST, PORT);

            ObjectOutputStream clientOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            clientOutputStream.writeObject(remoteExecution);
            clientOutputStream.flush();

            ObjectInputStream clientInputStream = new ObjectInputStream(clientSocket.getInputStream());
            Object objReturn = clientInputStream.readObject();

            System.out.println((String) objReturn);

            clientOutputStream.close();
            clientInputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
