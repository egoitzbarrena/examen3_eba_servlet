package com.zubiri.jsp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class anadir
 */
public class anadir extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost/";

	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "zubiri";
	   
	   Connection conn = null;
	   Statement stmt = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public anadir() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=iso-8859-1");
	    PrintWriter out = response.getWriter();
		   

		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL, USER, PASS);

		      //STEP 4: Execute a query
		      System.out.println("Creating database...");
		      stmt = conn.createStatement();
		      //CREAR BASE DE DATOS: EMPLEADOS
		      String sql = "CREATE DATABASE IF NOT EXISTS EMPLEADOS";
		      stmt.executeUpdate(sql);
		      System.out.println("Database created successfully...");
		      System.out.println("Creando la tabla en la base de datos...");
		      stmt = conn.createStatement();
		      //USAR BASE DE DATOS EMPLEADOS.SI NO ESCRIBES ESTA LINEA NO PODRAS CREAR LA TABLA.
		      String sql3 = "USE EMPLEADOS ";
		      stmt.executeUpdate(sql3);
		      //CREAR TABLA EMPLEADOS
		      String sql2 = "CREATE TABLE IF NOT EXISTS PERSONA (DNI VARCHAR(20) ,  NOMBRE VARCHAR(50), APELLIDO VARCHAR(50),AÑO_NACIMIENTO VARCHAR(20), PRIMARY KEY ( DNI ))";

		      stmt.executeUpdate(sql2);
		      
		      
		      //INSERTAR LOS DATOS EN LA TABLA
		      stmt.executeUpdate("INSERT INTO PERSONA (DNI,NOMBRE,APELLIDO,AÑO_NACIMIENTO) VALUES ('" + 
		        request.getParameter("dni") + "', '" + request.getParameter("nombre") + "','" + request.getParameter("apellido") + "', '" + request.getParameter("ano") + "')");
		      //MOSTRAR DATOS DE LA TABLA
		      String sqlselect = "SELECT * FROM PERSONA";
		      ResultSet rs = stmt.executeQuery(sqlselect);
		      out.print("<html>");
		      out.print("<head><title></title>");
		      out.print("</head>");
		      out.print("<body>");
		      out.println("<table align='center' width='40%' border='10' >  ");
		      out.println("<td><FONT COLOR='#00BFFF' SIZE='5'>	DNI</FONT> </td>");
		      out.println("<td><FONT COLOR='#00BFFF' SIZE='5'>	NOMBRE</FONT> </td>");
		      out.println("<td><FONT COLOR='#00BFFF' SIZE='5'> APELLIDO</FONT> </td>");
		      out.println("<td><FONT COLOR='#00BFFF' SIZE='5'> AÑO_NACIMIENTO</FONT> </td>");
		      out.println("</tr>");
		      while (rs.next()) {
		    	String dniselect = rs.getString("DNI");
		    	String nombreselect = rs.getString("NOMBRE");
		        String apellidoselect = rs.getString("APELLIDO");
		        String anoselect = rs.getString("AÑO_NACIMIENTO");
		        

		        out.println("<tr>");
		        out.println("<td> <FONT COLOR='BLUE' SIZE='3'>" + dniselect + "</FONT></td>");
		        out.println("<td> <FONT COLOR='BLUE' SIZE='3'>" + nombreselect + "</FONT></td>");
		        out.println("<td> <FONT COLOR='BLUE' SIZE='3'>" + apellidoselect + "</FONT></td>");
		        out.println("<td> <FONT COLOR='BLUE' SIZE='3'>" + anoselect + "</FONT></td>");
		        out.println("</tr>");
		      }
		      rs.close();
		      out.println("</table");
		      out.print("</body>");
		      out.print("</html>");
		    }
		    catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		   System.out.println("adios!");
		}//end JDBCExample
}
	