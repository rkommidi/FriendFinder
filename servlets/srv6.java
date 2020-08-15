  import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class srv6 extends HttpServlet  
{
	Connection con;
	ResultSet rs,rs1;
	Statement st,st1;
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
					st1=con.createStatement();
					String cat=request.getParameter("category");
                     String cat1=request.getParameter("pwd");
                       String cat2=request.getParameter("gender");
					    
                        int cat3=Integer.parseInt(request.getParameter("age"));
			    int k=st.executeUpdate("insert into p values('"+cat+"','"+cat1+"','"+cat2+"',"+cat3+",'ect')");
				String q="insert into idtab values('"+cat+"','"+cat1+"')";
				System.out.println(q);
				int y=st1.executeUpdate(q);

		//	    while(rs.next())
			//     {
			    //    String pcat=rs.getString(1);
					//String pcat1=rs.getString(2);
					//if(pcat.equals(cat1))
					//sos.println("y");
                    //else
					//sos.println("n");
			     //}	 
			    }catch(Exception e)
			    { 
			    System.out.println(e);
			    }
              }
         
}


		           