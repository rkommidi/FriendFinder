import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.lang.*;
import java.io.*;
import java.sql.*;
public class   GetFee extends HttpServlet
{
	public void service(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException
	{
		Connection con;
		PreparedStatement pst;
		BufferedReader kb;
		String efee,date,code;
	    PrintWriter pw=res.getWriter();
		try
		{
		    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con=DriverManager.getConnection("jdbc:odbc:MyDsn","wireless","camp");
			pst=con.prepareStatement("SELECT EFEE,LDATE FROM ENOTICE WHERE CODE=?");
			code=req.getParameter("CODE");
			pst.setString(1,code);
			ResultSet rs=pst.executeQuery();
			while(rs.next())
			{
				efee=rs.getString(1);
				date=rs.getString(2);
			    pw.print(efee+" last date "+date);
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
