   import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class srv10 extends HttpServlet  
{
	Connection con;
	ResultSet rs1,rs3,rs;
	Statement st,st1,st3,st4;
	ServletOutputStream sos;
	public void init(ServletConfig sc)
    { 
			try
	        {
					System.out.println("init");
					super.init(sc);
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					con=DriverManager.getConnection("Jdbc:Odbc:sho","small","shopping");
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
				System.out.println("post method of srv0");
				sos=response.getOutputStream();
				System.out.println("post method of srv0.1");
				try
				{   
						st=con.createStatement();
						st1=con.createStatement();
						st3=con.createStatement();
						st4=con.createStatement();
						System.out.println("srv0.2");
						String u=request.getParameter("un");
						String p=request.getParameter("paw");
						System.out.println("srv0.3");
						rs1=st1.executeQuery("select cusint from p where ud='"+u+"' and pwd='"+p+"'");
						if(rs1.next())
						{   
								System.out.println("srv0.4");
								String pcat3=rs1.getString(1);
								System.out.println(pcat3);
								while( (x=pcat3.indexOf(","))>0)
								{      
									System.out.println("in first while");		
									out1=pcat3.substring(0,x).trim();
									System.out.println("interestsw are:"+out1);
									rs3=st3.executeQuery("select img1d from advertisement where des='"+out1+"'");
									while(rs3.next())
									{  
											System.out.println("in second while");		
											int pcat4=Integer.parseInt(rs3.getString(1));
											System.out.println("Image id is:"+pcat4);
											String q3="select ad.img from advertisement ad,adcust c where ad.img1d=c.img1d and c.status='n' and c.img1d="+pcat4;
											System.out.println(q3);
											rs=st.executeQuery(q3);
											while(rs.next())
											{  
													System.out.println("in third while");		
													sos.println(rs.getString(1)+":");
													int y=st4.executeUpdate("update adcust set status='d' where u='"+u+"' and pawd='"+p+"' and img1d="+pcat4+"");
						                    }
                                   }
					    			pcat3=pcat3.substring(x+1);
					}
				}
			
		//	System.out.println("exited");
			}
			catch(Exception e)
			{ 
					System.out.println(e);
			}
		}
}


