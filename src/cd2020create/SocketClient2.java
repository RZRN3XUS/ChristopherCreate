package cd2020create;

import java.io.*;
import java.net.*;
import java.util.*;

public class SocketClient2 {
    public static void main(String args[]) throws IOException, NumberFormatException, InterruptedException, NullPointerException {
        Socket sklocal = null;
        Date date = new Date();
        long timeMilli = date.getTime();
        System.out.print("Connecting...");
        long timeMilli2 = date.getTime();
        while (true) {
            if (timeMilli2 - timeMilli > 10000)
            {
                System.err.println("Error: Connection Timeout");
            }
            try {
                sklocal = new Socket(InetAddress.getByName("10.12.98.56"), 1);
                if (sklocal.getRemoteSocketAddress() != null) {
                    break;
                }
            } catch (Exception e) {
                timeMilli2 = date.getTime();
                System.out.print(".");
            }
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Connected to " + sklocal.getRemoteSocketAddress());
        System.out.print("Please enter a username (Must not contain spaces): ");
        String username = sc.next();
        System.out.println();
        DataOutputStream dataOut = new DataOutputStream(sklocal.getOutputStream());
        DataInputStream dataIn = new DataInputStream(sklocal.getInputStream());
        boolean useless = false;
        String broadcast = "";
        int x = 0;
        while (true) {
            if (x > 0) {
                System.out.print("[" + username + "]" + ": ");
            }
            String input = "[" + username + "]" + ": " + sc.nextLine();
            useless = true;
            dataOut.writeUTF(input);
            if (dataOut.equals(null))
                broadcast = dataIn.readUTF();
            if (!(broadcast.equals(input)))
                System.out.println(broadcast);
            System.out.print(broadcast);
            if (!(broadcast.equals("")))
            {
                System.out.println();
            }
            x++;
        }
    }
}