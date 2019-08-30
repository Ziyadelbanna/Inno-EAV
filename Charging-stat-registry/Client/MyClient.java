package Client;

import java.net.*;
import java.io.*;
import java.util.concurrent.TimeUnit;
public class MyClient {

    InputStream Sinput;
    OutputStream Soutput;
    ObjectInputStream OSinput;
    ObjectOutputStream OSoutput;
    Socket s;

    MyClient (int port)
    {
        /// Connecting with server

        try {
            s = new Socket ("10.240.22.7",port);
       }
        catch (Exception e) {

            System.out.println(e);
        }
        System.out.println("Connection with vehicle on port "+ s.getPort());//+ s.getInetAddress()+ ":" + s.getPort() + "successfully established\n");

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

        int information_files_no =1;
        File file = new File ("/home/innopolis/IdeaProjects/Client/src/test.json");

        try {
            System.out.println("Client sends >> sending vehicle information to Charging station\n");

           // OSoutput.writeObject("Client sends >> sending vehicle information to Charging station\n");
            ByteStreamClient obj = new ByteStreamClient ();
            //String str = (String) OSinput.readObject();

           // System.out.println(str);

                obj.toStream (Soutput, "test.json");
                obj.toStream(Soutput, new File ("test.json"));


            Soutput.flush();
            s.shutdownOutput();
        }catch (Exception e) {
            System.out.println("Problem when reading from server: " + e);
        }

        // -------------------------------------- STEP 6 in document ------------------------------------*/
        String AmountOfElc = "amount needed";
        try {
            int valid = ByteStreamClient.toInt(Sinput);
            if(valid == 1 ) {
                System.out.println("read back from server " + AmountOfElc);
                TimeUnit.SECONDS.sleep(2);

            }
            else {
                System.out.println("Please register your car in the charging station");
            }
        }
        catch (Exception e)
        {
            System.out.println("Problem when reading from server" +e);
        }
        try {
            Sinput.close();
            Soutput.close();
            System.out.println("Session closed.");
        }
        catch (Exception e){
        }
    }

    public static void main (String[] args)
    {
        new MyClient (1500);
    }


}
