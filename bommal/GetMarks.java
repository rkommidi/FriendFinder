import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.lang.*;
import java.io.*;
import java.sql.*;
public class   GetMarks extends HttpServlet
{
	public void service(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException
	{
		Connection con;
		PreparedStatement pst,pst1;
		BufferedReader kb;
		String[] param=new String[4];
		String[] sname=new String[10];
		String subcode,marks,maxmarks;
	    PrintWriter pw=res.getWriter();
		int i;
		try
		{
		    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con=DriverManager.getConnection("jdbc:odbc:MyDsn","wireless","camp");
			param[0]=req.getParameter("SID");
			param[1]=req.getParameter("TYPE");
			if(param[1].equals("MEXSP") ||param[1].equals("MEXRE")||param[1].equals("MINT"))
			{
				pst=con.prepareStatement("SELECT SUBCODE,MARKS,MAXMARKS FROM MARKS WHERE (STUDID=? AND TYPE=? AND YEAR=? AND SEM=?)");
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
				pst=con.prepareStatement("SELECT SUBCODE,MARKS,MAXMARKS FROM SUBJECT WHERE (STUDID=? AND TYPE=? AND YEAR=(SELECT YEAR FROM SYEAR WHERE STUDID=?) AND SEM=(SELECT SEM FROM SYEAR WHERE STUDID=?))");
				pst1=con.prepareStatement("SELECT NAME FROM SUBJECT WHERE (YEAR=(SELECT YEAR FROM SYEAR WHERE STUDID=?) AND SEM=(SELECT SEM FROM SYEAR WHERE STUDID=?))");
				pst.setString(1,param[1]);
				pst.setString(2,param[2]);
				pst.setString(3,param[1]);
				pst.setString(4,param[1]);
				pst1.setString(1,param[1]);
				pst1.setString(2,param[1]);
			}
			i=0;
			ResultSet rs=pst1.executeQuery();
				while(rs.next())
				{
					sname[i]=rs.getString(1);
					i++;
				}
			rs=pst.executeQuery();
			i=0;
			pw.println("NO  SCODE  SNAME MARKS MAX");
			pw.println("---------------------------------");
			while(rs.next())
			{
				subcode=rs.getString(1);
				marks=rs.getString(2);
				maxmarks=rs.getString(3);
				pw.print(i+"     ");
				pw.print(subcode+" ");
				pw.print(sname[i]+"     ");
				pw.print(marks+"    ");
				pw.print(maxmarks);
				i++;
				pw.println(" ");
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
