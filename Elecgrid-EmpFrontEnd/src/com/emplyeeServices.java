package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class emplyeeServices {
	
	//connection
			private Connection connect() {
				Connection con = null;
				try {
					//Class.forName("com.mysql.cj.jdbc.Driver");
					Class.forName("com.mysql.jdbc.Driver");
					
					String url = String.format("jdbc:mysql://127.0.0.1:3306/elecgrid");
					String username = "root";
					String password = "";
					
					con = DriverManager.getConnection(url,username, password);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				return con;
			}

			//insert employee
			public String insertEmp(String e_name, String e_mobile, String designation) {
				
				String output = "";

				try {
					
					Connection con = connect();
					
					if(con == null)
					{return "Error while connecting to the database for inserting data";}
					
					String insertEmp = "insert into employee (`e_id`, `e_name`, `e_mobile`, `designation`)" + "values(?,?,?,?)";
					
					PreparedStatement ps = con.prepareStatement(insertEmp);
					ps.setInt(1, 0);
					ps.setString(2, e_name);
					ps.setString(3, e_mobile);
					ps.setString(4, designation);

					ps.execute();
					con.close();
					
					String newEmployee = viewEmp();
					output ="{\"status\":\"success\",\"data\":\""+newEmployee+"\"}";
					//output = "Inserted "+ e_name + " Successfully";

				} catch(Exception e) {
					output = "{\"status\":\"error\", \"data\":\"Error while inserting the employee.\"}"; 
					//output = "Error While inserting the employee.";
					System.err.println(e.getMessage());
				}

				return output;
			}

			//view employee details
			public String viewEmp() {
				
				String output ="";
				
				try {
					
					Connection con = connect();
					
					if (con==null)
					{ return "Error!! While connecting to the database for read the Employees";}
					
					// Prepare the html table to be displayed
					output = "<table border='1'><tr><th>Employee Name</th><th>Mobile No:</th>" +
					"<th>Designation</th>" +
					"<th>Update</th><th>Remove</th></tr>";
					
					String query = "select * from employee";
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(query);
					
					while(rs.next()) {
						
						String eid = Integer.toString(rs.getInt("e_id"));
						String ename = rs.getString("e_name");
						String emobile = rs.getString("e_mobile");
						String edisignation = rs.getString("designation");
						
						// Add into the html table
						output += "<tr><td><input id='employeeUpdate' name='employeeUpdate' type='hidden' value='"+eid+"'>"+ ename +"</td>"; 
						//output += "<td>" + ename + "</td>";
						output += "<td>" + emobile + "</td>";
						output += "<td>" + edisignation + "</td>";
						
						// buttons
						
						output += "<td><input name='btnUpdate' type='button' value='Update' "
								 + "class='btnUpdate btn btn-secondary' data-e_id='" + eid + "'></td>"
								 + "<td><input name='btnRemove' type='button' value='Remove' "
								 + "class='btnRemove btn btn-danger' data-e_id='" + eid + "'></td></tr>"; 
						
						/*output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
						+ "<td><form method='post' action='items.jsp'>"
						+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
						+ "<input name='cus_id' type='hidden' value='" + eid
						+ "'>" + "</form></td></tr>";
						*/
					}
					
					con.close();
					
					//Complete the html table
					
					output += "</table>";
				} catch (Exception e) {
					output = "Error while fetching emplyees details.";
					System.err.println(e.getMessage());
				}
				return output;
			}
			
			//update employee details
			public String updateEmp(String e_id, String e_name, String e_mobile, String designation) {
				
				String output="";
				
				try {
					
					Connection con = connect();
					
					if (con==null)
					{ return "Error!! While connecting to the database for updating the " + e_name;}
					
					// create a prepared statement
					String query = "UPDATE employee SET e_name=?, e_mobile=?, designation=? WHERE e_id=?";
					
					PreparedStatement preparedStmt = con.prepareStatement(query);
					
					// binding values
					preparedStmt.setString(1, e_name);
					preparedStmt.setString(2, e_mobile);
					preparedStmt.setString(3, designation);
					preparedStmt.setInt(4,Integer.parseInt(e_id));
					
					// execute the statement
					preparedStmt.execute();
					
					con.close();
					String newEmployee = viewEmp();
					output = "{\"status\":\"success\",\"data\":\""+newEmployee+"\"}"; 
					//output = "Updated successfully";
					
				} catch (Exception e) {
					
					output = "{\"status\":\"error\",\"data\":\"Error while updating the employee.\"}"; 
					//output = "Error while updating the " + e_name;
					System.err.println(e.getMessage());
				}
				
				return output;
			}
			
			//delete employee from db
			public String deleteEmp(String e_id)
			{
				String output = "";
				
				try
				{
				Connection con = connect();
				
				if (con == null)
				{return "Error while connecting to the database for deleting."; }
				
				// create a prepared statement
				String query = "delete from employee where e_id=?";
				
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				// binding values
				preparedStmt.setInt(1, Integer.parseInt(e_id));
				
				// execute the statement
				preparedStmt.execute();
				
				con.close();
				String newEmployee = viewEmp();
				output = "{\"status\":\"success\",\"data\":\""+newEmployee+"\"}"; 
				//output = "Deleted successfully";
				}
				catch (Exception e)
				{
					//output = "Error while deleting the employee.";
					output = "{\"status\":\"error\",\"data\":\"Error while deleting the employee.\"}";
					System.err.println(e.getMessage());
				}
			return output;
			}
			
			//get employee by id
		/*	public String getEmpById(int e_id) {
				String output ="";
				
				try {
					
					Connection con = connect();
					
					if (con==null)
					{ return "Error!! While connecting to the database for read the employees";}
					
					
					
					String selquery = "select * from employee WHERE e_id= " + e_id;
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(selquery);
					
					while(rs.next()) {
						
						
						String ename = rs.getString("e_name");
						String emobile = rs.getString("e_mobile");
						String desig = rs.getString("designation");
						
						// view
						output += "Name: " + ename + "<br>" + "Mobile No: " + emobile;
						output += "<br>Job Role: " + desig;
						
						
					}
					
					con.close();
					
				} catch (Exception e) {
					output = "Error while reading employee";
					System.err.println(e.getMessage());
				}
				return output;
			} */
			

}
