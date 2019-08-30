package Client;

import java.net.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class MyClient {

    InputStream Sinput;
    OutputStream Soutput;
    Socket s;

    MyClient (int port)
    {
        /// Connecting with server

        try {
            s = new Socket ("10.240.19.139",1500);
       }
        catch (Exception e) {

            System.out.println(e);
        }
        System.out.println("Connection with vehicle ");//+ s.getInetAddress()+ ":" + s.getPort() + "successfully established\n");

        /// Creating both data streams (input/output)
        try {

            Sinput = s.getInputStream();
            Soutput = s.getOutputStream();
        }
        catch (IOException e)
        {
            System.out.println("Exception creating new input/output streams" + e);
            return;
        }

        // Input/output streams are ready

        /*------------------------------------- STEP 2 in document---------------------------------------*/

        String Information = "Sending the vehicle information";
        int information_files_no =1;
        File file = new File ("/home/innopolis/IdeaProjects/Client/src/test.txt");

        try {
           // Soutput.writeObject("Client sends >> sending vehicle information to Charging station\n");
           // ByteStream.toStream(Soutput, information_files_no);

            for (int cur_files =0; cur_files<information_files_no ; cur_files++)
            {
                ByteStreamClient.toStream (Soutput, "Client/test.xml");
                ByteStreamClient.toStream(Soutput, new File ("Client/test.xml"));
            }

            //Soutput.flush();
            s.shutdownOutput();
        }catch (Exception e) {
            System.out.println("Problem when reading from server: " + e);
        }

        // -------------------------------------- STEP 6 in document ------------------------------------*/
        String AmountOfElc = "amount needed";
        try {
      //      AmountOfElc = (String) Sinput.readObject();
            System.out.println("read back from server " + AmountOfElc);
            TimeUnit.SECONDS.sleep(2);
            System.out.println("Car accepts the amount.");
        }
        catch (Exception e)
        {
            System.out.println("Problem when reading from server" +e);
        }
        try {
            Sinput.close();
            Soutput.close();

        }
        catch (Exception e){

        }
    }

    public static void main (String[] args)
    {
        new MyClient (1500);
    }


}
