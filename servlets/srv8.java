  import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class srv8 extends HttpServlet  
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
                      String out1="";
					  int x;
				System.out.println("post method");
/*		response.setContentType("text/html");
		try
		{*/
			sos=response.getOutputStream();
		     try
		     {   con=DriverManager.getConnection("Jdbc:Odbc:sho","small","shopping");
		            st=con.createStatement();
			    String str1=request.getParameter("category");
				while( (x=str1.indexOf(","))>0)
    {  
			out1=str1.substring(0,x).trim();
			System.out.println(out1);
		
                    rs=st.executeQuery("select distinct prodcat from products where accid=(select distinct accid from accesory where accname='"+out1+"')");
		while(rs.next())
		           { String pcat=rs.getString(1);
			
				    System.out.println(pcat);
				sos.println(pcat+",");
				}
					str1=str1.substring(x+1);
	}
		
     	 
	 
			    }catch(Exception e)
			    { 
			    System.out.println(e);
			    }
              }
         
}


		           