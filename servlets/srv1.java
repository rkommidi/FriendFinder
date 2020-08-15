import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class srv1 extends HttpServlet  
{
	Connection con;
	ResultSet rs;
	Statement st;
	ServletOutputStream sos;
	public void init(ServletConfig sc)
           { try
	          {
		   System.out.println("init");
		      super.init(sc);
		      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		      System.out.println("class loaded");
		}catch(Exception e)
		     {   System.out.println(e);
		     }
            }
	    public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException
	     {
	        doPost(request,response);
		}
		     public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException
	              {
		        System.out.println("post method");
/*		response.setContentType("text/html");
		try
		{*/
			sos=response.getOutputStream();
		     try
		     {   con=DriverManager.getConnection("Jdbc:Odbc:sho","small","shopping");
		            st=con.createStatement();
			    rs=st.executeQuery("select * from accesory");
			    while(rs.next())
			     {
			        String accname=rs.getString(2);
					sos.println(accname);
			     }	 
			    }catch(Exception e)
			    { 
			    System.out.println(e);
			    }
              }
         
}


		           
	
	