 import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class srv3 extends HttpServlet  
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
					String cat=request.getParameter("category");
			    rs=st.executeQuery("select shopname,shoploc from shops where accid=(select distinct accid from products where prodcat='"+cat+"')");
			    while(rs.next())
			     {
			        String pcat=rs.getString(1);
					String pcat1=rs.getString(2);
					sos.println(pcat+"                                                              "+pcat1+":");
//					sos.println();
			     }	 
			    }catch(Exception e)
			    { 
			    System.out.println(e);
			    }
              }
         
}


		           
	
	