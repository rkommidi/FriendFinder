import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.lang.*;
import java.io.*;
import java.sql.*;
public class   GetTimeTable extends HttpServlet
{
	public void service(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException
	{
		Connection con;
		PreparedStatement pst,pst1;
		BufferedReader kb;
		String[] param=new String[4];
		String[] sname=new String[10];
		String date,subject,timings;
	    PrintWriter pw=res.getWriter();
		int i=0;
		try
		{
		    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con=DriverManager.getConnection("jdbc:odbc:MyDsn","wireless","camp");
			param[0]=req.getParameter("SID");
			param[1]=req.getParameter("TYPE");
			if(param[1].equals("EEXSP"))
			{
				pst=con.prepareStatement("SELECT EDATE,SUBCODE,TIMINGS FROM EXAMTIMETABLE WHERE (BID=(SELECT BID FROM STUDENT WHERE SID=?) AND YEAR=? AND TYPE=?)");
				pst1=con.prepareStatement("SELECT NAME FROM SUBJECT WHERE (YEAR=? AND SEM=?)");
				param[2]=req.getParameter("YEAR");
		        param[3]=req.getParameter("SEM");
				pst.setString(1,param[0]);
		        pst.setString(2,param[1]);
		        pst.setString(3,param[2]);
			    pst.setString(4,param[3]);
				pst1.setString(1,param[2]);
		        pst1.setString(2,param[3]);
		        
			}		
			else
			{
				pst=con.prepareStatement("SELECT EDATE,SUBCODE,TIME FROM EXAMTIMETABLE WHERE (BID=(SELECT BID FROM STUDENT WHERE SID=?) AND YEAR=(SELECT SYEAR FROM SYEAR WHERE SID=?) AND TYPE=?)");
				pst1=con.prepareStatement("SELECT NAME FROM SUBJECT WHERE (YEAR=(SELECT YEAR FROM SYEAR WHERE SID=?) AND SEM=(SELECT YEAR FROM SYEAR WHERE SID=?) )");
				pst.setString(1,param[0]);
				pst.setString(2,param[1]);
			}
			ResultSet rs=pst1.executeQuery();
			while(rs.next())
			{
				sname[i]=rs.getString(1);
				i++;
			}
			i=0;
			rs=pst.executeQuery();
			pw.println("DATE  -SUB CODE-SUB NAME-TIME");
			while(rs.next())
			{
				date=rs.getString(1);
				subject=rs.getString(2);
				timings=rs.getString(3);
				pw.print(date);
				pw.print(subject);
				pw.print(sname[i]);
				pw.print(timings);
				i++;
			}
			rs.close();
			pst.close();
			pst1.close();
			con.close();
		}
		    catch(Exception ex)
		    {
			    ex.printStackTrace();
		    }
	     
    
    }
}
