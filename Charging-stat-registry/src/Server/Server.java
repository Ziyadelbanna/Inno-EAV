package Server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import org.json.*;
import org.apache.commons.io.IOUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;  
import java.util.Date;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import com.mysql.jdbc.ResultSetMetaData;

import Client.ByteStreamClient;
import provider.ConnectionProvider;

public class Server {

    private ServerSocket ss;

    Server (int port)
    {
        /// creating the server socket and wait for a connection to accept
        try {
            ss = new ServerSocket(port);
            // must wait infinitely for a client
            while (true)
            {
                System.out.println("Server waiting for client on port: " + ss.getLocalPort());
                Socket s = ss.accept();
                System.out.println("New client asked for a connection ...");
                Tcpthread t = new Tcpthread(s);
                System.out.println("Starting a thread for the client ...");
                t.start();
            }

        }
        catch (IOException e){
            System.out.println("Exception on new Server socket :" + e);
        }
    }

    public static void main (String [] args) throws IOException, ParseException
    {
        new Server (1500);
    }
    // As many threads as it might take ( threads represent sessions with cars)

    class Tcpthread extends Thread{
        // now we want to listen/talk on this socket

        Socket socket;
        InputStream Sinput;
        OutputStream Soutput;
        ObjectInputStream OSinput;
        ObjectOutputStream OSoutput;

        Tcpthread(Socket s)
        {
            this.socket=s;
        }

        public void run(){

            System.out.println("Thread trying to create Object Input/Output stream ....");

            try {
                Soutput = (socket.getOutputStream());
                Soutput.flush();
                Sinput = (socket.getInputStream());
   
            }
            catch (IOException e){
                System.out.println("Exception creating new input/output streams" +e );
            }

            System.out.println("Server waits for a file from client");

            // now server needs to read a string from client
            // ------------------------------------STEP 3,4,5 in document ------------------------------------------------//
            int valid =0;

            try {

                System.out.println("Server sends >>" + "Retrieving the vehicle's information");
                
                TimeUnit.SECONDS.sleep(6);

                File file;

                    String file_name = ByteStream.toString(Sinput);
                    StringTokenizer st = new StringTokenizer(file_name, "\\");
                    ArrayList<String> strings = new ArrayList<String>();

                    while (st.hasMoreElements()) {
                        strings.add(st.nextToken().toString());
                    }

                    System.out.println("C:\\wamp64\\www\\Charging station\\src\\Server\\Sent documents\\" + strings.get(0) +" recieved");
                    String path = "C:\\wamp64\\www\\Charging station\\src\\Server\\Sent documents\\";
                    file = new File(path + strings.get(0));
                    
                    ByteStream.toFile(Sinput, file);
                    
                    String Action;

                	if (!checkCar(file))
                	{
                		Action = new String ("Car is not registered in the station");
                	}
                	else {
                		Action = new String("Car approved, Wait while we send the amount of electricity the car needs by EVSE");
                		valid = 1;
                	}
                    System.out.println("Server sends >> " + Action);
                    ByteStream.toStream(Soutput,valid);
                    
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("Session Closed, waiting for another client on port "+ ss.getLocalPort());

                                    	
            }
            catch (Exception e )
            {
                System.out.println("Exception reading/writing Streams:"+e);
                return;
            }
            finally {
                try {
                	Soutput.close();
                    Sinput.close();
                }
                catch (Exception e)
                {

                }
            }
        }
        
        boolean checkCar(File file) throws IOException, ParseException
        		
        		{
        			parseFile(file);
        			return checkDatabase();
        		}
        
        String Name;
        String Email;
        String Phone;
        String PID;
        String Model_name;
        int Model_year;
        String pur_date;
        int Car_ID;
        
        void parseFile(File file) throws IOException, ParseException {
        	              
              if (file.exists()){
                  InputStream is = new FileInputStream(file);
                  String jsonTxt = IOUtils.toString(is, "UTF-8");
                 // System.out.println(jsonTxt);
                  JSONObject json = new JSONObject(jsonTxt);
                  System.out.println(json);   

                   Name= json.getString("Name");
                   Email= json.getString("Email");
                   Phone= json.getString("Phone");
                   PID= json.getString("Personal-ID");
                   Model_name= json.getString("Model_name");
                   Model_year= json.getInt("Model_year");
                   pur_date= json.getString("Date_purchased");
                   Date date1=new SimpleDateFormat("yyyy/M/d").parse(pur_date);
                   Car_ID= json.getInt("Car-ID");
              } 
        }
        
        boolean checkDatabase ()
        {
        	boolean isValid =false;

        	try{  
    			   Connection con= ConnectionProvider.getConnection();
    			   PreparedStatement ps=con.prepareStatement(  
    "select * from owner,car,`charging station` where car.ID=? and owner.ID=? and `charging station`.ID=9978");  
    		
    			   ps.setLong(1, Car_ID); 
    			   ps.setString(2, PID);
    			   
    			   ResultSet rs=ps.executeQuery();
    			   
    			   if(rs.next()) {
    				   isValid=true;
    			   }
    			                 
    		   }catch(Exception e){
    			   e.printStackTrace();
    		   }  
        	
        	return isValid;
        }

    }
}
