import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.lang.*;
import java.io.*;
import java.sql.*;
public class   GetNotification extends HttpServlet
{
	public void service(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException
	{
		Connection con;
		PreparedStatement pst;
		BufferedReader kb;
		String notification,date,validity,code;
	    PrintWriter pw=res.getWriter();
		try
		{
		    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con=DriverManager.getConnection("jdbc:odbc:MyDsn","wireless","camp");
			pst=con.prepareStatement("SELECT NOTICE,NDATE,VALIDITY FROM ENOTICE WHERE CODE=?");
			code=req.getParameter("CODE");
			pst.setString(1,code);
			ResultSet rs=pst.executeQuery();
			while(rs.next())
			{
				notification=rs.getString(1);
				date=rs.getString(2);
			    validity=rs.getString(3);
				pw.print(notification +"given on " +date +"last date"+validity);
			}
			rs.close();
			pst.close();
			con.close();
		}
		    catch(Exception e)
		    {
			    e.printStackTrace();
		    }
	     
    
    }
}
