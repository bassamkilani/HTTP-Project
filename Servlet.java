/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@WebServlet(urlPatterns = {"/Servlet"})
public class Servlet extends HttpServlet {
        
    public Servlet(){
            try{  
//            Class.forName("com.mysql.jdbc.Driver");  
            con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/PalCovid","root","12345678");  
            System.out.println("connected");
    //        Statement stmt=con.createStatement();  
    //        ResultSet rs=stmt.executeQuery("select * from emp");  
    //        while(rs.next())  
    //        System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
    //        con.close();  
            }catch(Exception e){ 
                System.out.println("no connection");
                System.out.println(e);}  
    //        }  
    }
    
    
        public static Connection con; 
    
        public static void get_total(HttpServletResponse response,String date) throws SQLException, IOException{
            Statement stmt=con.createStatement();  
            ResultSet rs=stmt.executeQuery("SELECT SUM(Active),SUM(Healed) FROM Cases WHERE TDate = '"+date+"'");
            rs.next();		
            String Active = rs.getString(1);
            String Healed = rs.getString(2);
            PrintWriter out = response.getWriter();
            out.println(Active+","+Healed);        
    //        while(rs.next())    
    //        con.close(); 
        }
    
        public static void get_city(HttpServletResponse response,String city,String date) throws SQLException, IOException{
            Statement stmt=con.createStatement();  
            ResultSet rs=stmt.executeQuery("SELECT Active,Healed FROM Cases WHERE City = '"+city+"' AND TDate = '"+date+"'"); 
            rs.next();
            String Active = rs.getString(1);
            String Healed = rs.getString(2);

            PrintWriter out = response.getWriter();
            out.println(Active+","+Healed);        
    //        while(rs.next())    
    //        con.close(); 
        }

        public static void get_range(HttpServletResponse response,String start,String end) throws SQLException, IOException{
            Statement stmt=con.createStatement();  
            ResultSet rs=stmt.executeQuery("SELECT * FROM Cases WHERE TDate BETWEEN '"+start+"' and'"+end+"'");           
            PrintWriter out = response.getWriter();

            while(rs.next()) {
                out.println(rs.getString(2)+","+rs.getInt(3)+","+rs.getInt(4));
            }   
    //        con.close(); 
        }
        public static void connect_db(){
        try{  
//            Class.forName("com.mysql.jdbc.Driver");  
            con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/PalCovid","root","12345678");  
    //        Statement stmt=con.createStatement();  
    //        ResultSet rs=stmt.executeQuery("select * from emp");  
    //        while(rs.next())  
    //        System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
    //        con.close();  
            }catch(Exception e){ System.out.println(e);}  
    //        }  
        }
	
	
	
	public static boolean login (HttpServletResponse response,String name ,String pass) throws SQLException, IOException {
            Statement stmt=con.createStatement();  
            ResultSet rs=stmt.executeQuery("SELECT * FROM users WHERE Username='"+name+"' and Password = '"+pass+"'");   
            PrintWriter out = response.getWriter();
            if(!rs.next()) {
            out.println("Invalid Username or Password!");
            return false;
            } else {
            out.println("Authenticated");
            return true;
    }
    
}

 public static void edit_city (String city,int id) throws SQLException{
	 
		Statement stmt=con.createStatement();  
		stmt.executeUpdate("UPDATE Cases SET City = '"+city+"' WHERE ID = '"+id+"'"); 
        
 }
	
 public static void Edit_active (int active,int id)throws SQLException{
	 
		Statement stmt=con.createStatement();  
		stmt.executeUpdate("UPDATE Cases SET Active = '"+active+"' WHERE ID = '"+id+"'"); 
        
 }
 
  public static void Edit_healed (int healed,int id) throws SQLException {
	 
		Statement stmt=con.createStatement();  
		stmt.executeUpdate("UPDATE Cases SET Healed = '"+healed+"' WHERE ID = '"+id+"'"); 
        
 }
 
  public static void Edit_date (String date,int id) throws SQLException{
	 
		Statement stmt=con.createStatement();  
		stmt.executeUpdate("UPDATE Cases SET TDate = '"+date+"' WHERE ID = '"+id+"'"); 
        
 }

  public static void Insert_Record (String city,int active,int healed,String date) throws SQLException{
	 
		Statement stmt=con.createStatement();  
		stmt.executeUpdate("INSERT INTO Cases (City,Active,Healed,TDate) Values ('"+city+"','"+active+"','"+healed+"','"+date+"')"); 
        
 }
 
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
       
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
                    try{  
//            Class.forName("com.mysql.jdbc.Driver");  
            con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/PalCovid","root","");  
            out.println("connected");
    //        Statement stmt=con.createStatement();  
    //        ResultSet rs=stmt.executeQuery("select * from emp");  
    //        while(rs.next())  
    //        System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
    //        con.close();  
            }catch(Exception e){ 
                out.println("no connection");
                System.out.println(e);} 
        
        
        
        
        
        try {
            if(request.getMethod().equals("GET")){
                if(request.getParameter("total")!=null){
                   String date = request.getParameter("date");
                    get_total(response,date);
                }
                else if (request.getParameter("city")!=null){
                    String date = request.getParameter("date");
                    String city = request.getParameter("city");
                    get_city(response,city,date);
                }
                else if (request.getParameter("range")!=null){
                    String start = request.getParameter("start");
                    String end = request.getParameter("end");
                    get_range(response,start,end);
                }
            }
            else if (request.getMethod().equals("POST")){
                if(request.getParameter("id")!=null){
                    int id = Integer.parseInt(request.getParameter("id"));
                    if(request.getParameter("city")!= null){
                        String city = request.getParameter("city");
                        edit_city(city, id);
                    }
                    else if(request.getParameter("active")!= null){
                       int active = Integer.parseInt(request.getParameter("active"));
                        Edit_active(active, id);
                       
                    }
                    else if(request.getParameter("healed")!= null){
                       int healed = Integer.parseInt(request.getParameter("healed"));
                        Edit_healed(healed, id);
                       
                    }
                    else if(request.getParameter("date")!= null){
                       String date = request.getParameter("date");
                        Edit_date(date, id);               
                    }
                }
                
                else if(request.getParameter("city")!=null && request.getParameter("date")!=null &&request.getParameter("active") != null&&request.getParameter("active")!=null ){
                     String city = request.getParameter("city");
                     int active = Integer.parseInt(request.getParameter("active"));
                     int healed = Integer.parseInt(request.getParameter("healed"));
                     String date = request.getParameter("date");
                    Insert_Record(city, active, healed, date);
                }
            }
//        
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet Servlet</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet Servlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
        } finally {
            out.close();
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            try {
                processRequest(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            try {
                processRequest(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
