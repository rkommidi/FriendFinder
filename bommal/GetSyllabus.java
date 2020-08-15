import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.lang.*;
import java.io.*;
import java.sql.*;
public class   GetSyllabus extends HttpServlet
{
	public void service(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException
	{
		Connection con;
		PreparedStatement pst,pst1;
		String[] param=new String[4];
		String[] sname= new String[10];
		String startingchapter,endingchapter;
	    PrintWriter pw=res.getWriter();
		int i=0;
		try
		{
		    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con=DriverManager.getConnection("jdbc:odbc:MyDsn","WIRELESS","CAMP");
			param[0]=req.getParameter("SID");
			param[1]=req.getParameter("TYPE");
			param[2]=req.getParameter("SUBJECT");
			if(param[2]==null)
			{
				pst=con.prepareStatement("SELECT SUBCODE,STTOPIC,ENTOPIC FROM SYLLABUS WHERE (SID=? AND TYPE=?)");
				pst1=con.prepareStatement("SELECT NAME FROM SUBJECT WHERE (YEAR=(SELECT YEAR FROM SYEAR WHERE SID=?) AND SEM=(SELECT SEM FROM SYEAR WHERE SID=?))");
				pst.setString(1,param[0]);
				pst.setString(2,param[1]);
				pst1.setString(1,param[1]);
				pst1.setString(2,param[1]);
				ResultSet rs=pst1.executeQuery();
				while(rs.next())
				{
					sname[i]=rs.getString(1);
					i++;
				}
				rs.close();

			}
			else
			{
				pst=con.prepareStatement("SELECT STTOPIC,ENTOPIC FROM SYLLABUS WHERE (SID=? AND TYPE=? AND SUBCODE=(SELECT SUBCODE FROM SUBJECT WHERE NAME=?))");
				pst.setString(1,param[0]);
				pst.setString(2,param[1]);
				sname[i]=param[2];
			}
			ResultSet rs1=pst.executeQuery();
			i=0;
			pw.println("subject--starting chapter--ending chapter");
			while(rs1.next())
			{
				startingchapter=rs1.getString(1);
				endingchapter=rs1.getString(2);
				pw.println(sname[i]+": From "+startingchapter+" to " +endingchapter+".");
			}
			rs1.close();
			pst.close();
			con.close();
		}
		catch(Exception e)
		{
		    e.printStackTrace();
		}
	     
    
    }
}
