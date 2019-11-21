package cd2020create;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
public class Main {
    public static ArrayList<Socket> clients = new ArrayList<Socket>(0);
    public static ArrayList<DataOutputStream> douts = new ArrayList<DataOutputStream>(0);
    private ServerSocket socket;
    public static void main(String[] args) throws IOException
    {
        ServerSocket server = new ServerSocket(1, 50, InetAddress.getLocalHost());
        System.out.println(InetAddress.getLocalHost());
        while (true)
        {
            Socket sklocal = null;
            try
            {
                sklocal = server.accept();
                DataInputStream din = new DataInputStream(sklocal.getInputStream());
                DataOutputStream dout = new DataOutputStream(sklocal.getOutputStream());
                Thread thread = new ClientHandler(sklocal, din, dout);
                thread.start();
            }
            catch (Exception e){
                sklocal.close();
                e.printStackTrace();
            }
        }
    }
    static class ClientHandler extends Thread
    {
        DataInputStream dcin;
        DataOutputStream dcout;
        Socket client;
        public ClientHandler(Socket sklocal, DataInputStream din, DataOutputStream dout)
        {
            clients.add(client);
            System.out.println("Client connected from " + sklocal.getRemoteSocketAddress());
            this.client = sklocal;
            this.dcin = din;
            douts.add(dout);
            this.dcout = dout;
        }
        @Override
        public void run()
        {
            String out;
            while (true)
            {
                try {
//                    System.out.println("here");
                    String in = dcin.readUTF();
                    System.out.println(in); //printing in server for room testing
                    for (int x = 0; x < clients.size() - 1; x++) {
                        dcout.flush();
                        (douts.get(x)).writeUTF(in);
                    }
                    if(in.equalsIgnoreCase("logout"))
                    {
                        this.client.close();
                        break;
                    }
                } catch (Exception e) {
                    System.err.println("Error Code 1");
                }
            }
//            try {
//                this.dcin.close();
//                this.dcout.close();
//            } catch(IOException e) {
//                e.printStackTrace();
//            }
        }
    }
}
