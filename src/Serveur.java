import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur extends Thread{
    int NumClient;
    public static void main(String[] args) {
        new Serveur().start();
    }
    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(1234);
            System.out.println("Server Start-up ...");
            while (true){
             Socket socket = ss.accept();
             ++this.NumClient;
             new Conversation(socket,NumClient).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public class Conversation extends Thread{
        Socket socket;
        int numClient;
        Conversation(Socket socket , int numClient){
            this.socket = socket;
            this.numClient = numClient;
        }

        @Override
        public void run() {
            try {
                OutputStream os = socket.getOutputStream();
                InputStream is = socket.getInputStream();
                InputStreamReader ISR = new InputStreamReader(is);
                BufferedReader BR = new BufferedReader(ISR);
                PrintWriter pw = new PrintWriter(os,true);
                System.out.println("Client of @IP = "+socket.getRemoteSocketAddress()+" Was connected");
                pw.println("Welcome you are the client "+numClient);
                while (true){
                    String s = BR.readLine();
                    System.out.println("Client " + numClient +" of @IP = "+socket.getRemoteSocketAddress()+
                            " send the request "+s);
                    pw.println("Server response : "+s.toUpperCase());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }
}
