/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http_test.java;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Http_testJava {
    
    public static String php_url = "http://localhost/http_test/";
    public static String servlet = "http://localhost:8080/JSP_server/Servlet";
    public static String Username = "";
    public static String Password = "";
    
    public static void get_total() throws MalformedURLException, IOException {
 
    String request = php_url + "?total=?";
    
    URL url = new URL(request);
    
    URLConnection connect = url.openConnection();
    connect.setAllowUserInteraction(true);
    connect.setDoInput(true);
    connect.setDoOutput(true);
    InputStream in = connect.getInputStream();
     int c;
     String Active = "",Healed = "";
     if((c = in.read()) != -1){
     while(c != ','){
         Active += (char)c;
         c = in.read();
     }
     while((c = in.read())!=-1){
         Healed += (char)c;
     }
      System.out.println(Active+" , "+Healed);    
     }
    }
    
    public static void get_Range(String start,String end) throws MalformedURLException, IOException{
        String request = php_url + "?start="+start+"&end="+end;
        
        URL url = new URL(request);
        URLConnection connect = url.openConnection();
        connect.setAllowUserInteraction(true);
        connect.setDoInput(true);
        connect.setDoOutput(true);
        InputStream in = connect.getInputStream();
         int c;       

         String temp = "";
         ArrayList <String> vals = new ArrayList<String>();
         while((c = in.read())!= -1){
             if(c == ','){
                 vals.add(temp);
                 temp = "";
                 continue;
             }
             else
             temp+=(char)c;
         }
         System.out.println("size is : "+vals.size());
         for(int i = 0 ; i < vals.size();i++){
             System.out.println(vals.get(i));
         }

    }
    
    public static void get_City(String city) throws MalformedURLException, IOException{
    String request = php_url + "?city="+city;
    
    URL url = new URL(request);
    
    URLConnection connect = url.openConnection();
    connect.setAllowUserInteraction(true);
    connect.setDoInput(true);
    connect.setDoOutput(true);
    InputStream in = connect.getInputStream();
     int c;
     String Active = "",Healed = "";
     if((c = in.read()) != -1){
     while(c != ','){
         Active += (char)c;
         c = in.read();
     }
     while((c = in.read())!=-1){
         Healed += (char)c;
     }
      System.out.println(Active+" , "+Healed);    
     }
      
    } 
    public static void main(String[] args) throws MalformedURLException, IOException, ClassNotFoundException {
        
        
 
        
    URL url = new URL(servlet);

    URLConnection connect = url.openConnection();
    connect.setAllowUserInteraction(true);
    connect.setDoInput(true);
    connect.setDoOutput(true);
    
//    BufferedOutputStream out = new BufferedOutputStream(connect.getOutputStream());
//    
//    String request = "ID=1&name=hamza&pass=123&city=Tulkarm";
//    
//    out.write(request.getBytes());
//    out.close();
//    
     InputStream in = connect.getInputStream();
     int c;
     while((c = in.read()) != -1){
         System.out.print((char)c);
     }
     
//        get_total();
    }

}
