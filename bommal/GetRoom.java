import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.lang.*;
import java.io.*;
import java.sql.*;
public class   GetRoom extends HttpServlet
{
	public void service(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException
	{
		Connection con;
		PreparedStatement pst;
		String rnum;
		String[] param=new String[4];
	    PrintWriter pw=res.getWriter();
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con=DriverManager.getConnection("jdbc:odbc:MyDsn","WIRELESS","CAMP");
		    param[0]=req.getParameter("SID");
			if(param[0]==null)
			{
				param[0]=req.getParameter("YEAR");
				param[1]=req.getParameter("BID");
				pst=con.prepareStatement("SELECT RNO FROM SARRANGE WHERE YEAR=? AND BID=?");
				pst.setString(1,param[0]);
				pst.setString(2,param[1]);
			}
			else
			{
				pst=con.prepareStatement("SELECT RNO FROM SARRANGE WHERE SID=? BETWEEN STNO AND ENNO");
				pst.setString(1,param[0]);
			}
			ResultSet rs=pst.executeQuery();
			while(rs.next())
			{
				rnum=rs.getString(1);
				pw.println("room number:"+rnum);
			}
			rs.close();
			pst.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		pw.close();
    }
}
