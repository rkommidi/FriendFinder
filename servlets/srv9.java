import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class srv9 extends HttpServlet  
{
Connection con;
ResultSet rs1,rs2;
Statement st,st1,s;
ServletOutputStream sos;
public void init(ServletConfig sc)
{
		try
		{
		System.out.println("init");
		super.init(sc);
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		System.out.println("class loaded");
		}
		catch(Exception e)
		{   
			System.out.println(e);
		}
}
public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException
{
	doPost(request,response);
}
public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException
{
		String out1="",str3="",str6="",pcat2="";
		int x;
		System.out.println("post method of srv9");

		sos=response.getOutputStream();
		try
		{   
		con=DriverManager.getConnection("Jdbc:Odbc:sho","small","shopping");
		st=con.createStatement();
		st1=con.createStatement();
		s=con.createStatement();
		String str1=request.getParameter("category");

		String str2=request.getParameter("user");
		String str5=request.getParameter("paa");
		int fl=Integer.parseInt(request.getParameter("flag"));

		//System.out.println(fl);

		//System.out.println(str1);

		if(fl==1)
		{
		int h=s.executeUpdate("update p set cusint='ect' where ud='"+str2+"' and pwd='"+str5+"'");
		//System.out.println(h);
		}
		//System.out.println(str1);
		ResultSet rs=st.executeQuery("select distinct accname from accesory where accid=(select distinct accid from products where prodcat='"+str1+"')");

		while(rs.next())
		{ 
			String pcat=rs.getString(1);
			//System.out.println(pcat);
			str3=str3+pcat.substring(0,1);
			//System.out.println(str3);
			str3=str3+str1.substring(0,1);
			rs1=st1.executeQuery("select cusint from p where ud='"+str2+"' and pwd='"+str5+"'");
			while(rs1.next())
			{     
				pcat2=rs1.getString(1);
				//System.out.println(pcat2);
				str6=str6+pcat2;
				str6=str6.trim();
				//System.out.println(str3);
			}
			if(pcat2.equals("ect"))
			{  
				str6="";
				str6=str6+str3;
				str6=str6+',';
				//System.out.println(str6);
			}
			else
			{

				str6=str6+',';
				str6=str6+str3;
			}
			int g=st.executeUpdate("update p set cusint='"+str6+"' where ud='"+str2+"' and pwd='"+str5+"'"); 
			System.out.println("--------------------------");

		}
		System.out.println("Ecited from the method");
		}



		catch(Exception e)
		{ 
		System.out.println(e);
		}
		}

}


