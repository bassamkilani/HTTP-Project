/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/Servlet"})
public class Servlet extends HttpServlet {
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
        Class.forName("com.mysql.jdbc.Driver");  
        con=DriverManager.getConnection(  
        "jdbc:mysql://localhost:3306/sonoo","root","");  
//        Statement stmt=con.createStatement();  
//        ResultSet rs=stmt.executeQuery("select * from emp");  
//        while(rs.next())  
//        System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
//        con.close();  
        }catch(Exception e){ System.out.println(e);}  
//        }  
    }
	
	
	
	public static boolean login (HttpServletResponse response,String name ,String pass) throws SQLException {
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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
        
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Servlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Servlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
        processRequest(request, response);
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
