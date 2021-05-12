import java.sql.*;

/**
 * This program is designed to introduce students into JDBC programming. Students are required to fill the body of each
 * of the following methods:
 * 
 * (1) protected static Connection getConnectionToYourDB() throws SQLException
 * 
 * (2) protected static ResultSet getResultOfPart3A(Connection connection) throws SQLException
 * 
 * (3) protected ResultSet getResultOfPart3B(Connection connection) throws SQLException
 * 
 * Once all of the above methods are implemented correctly, this program will display the results of queries in Part 3
 * (a) and (b) of Programming Assignment 1.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 *
 */
public class JDBCTest {

	/**
	 * Attempts to establish a connection to your database.
	 * 
	 * @return a Connection to your database.
	 * @throws SQLException
	 *             if a database access error occurs.
	 */

	protected static Connection getConnectionToYourDB() throws SQLException {
		// (4 points) complete the following line to establish a connection to your database.
		/* 									REFERENCED: 
			 * https://www.javaguides.net/2020/02/how-to-connect-to-postgresql-with-java.html 
			 * https://www.tutorialspoint.com/postgresql/postgresql_java.htm
			 *  https://alvinalexander.com/java/java-mysql-select-query-example/	
		 */			
		
			
		Connection connection = null;
		
			try {
				//getting the host
				connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Plz I love you", "postgres", "N<<-KxME6n25`_4");	
				//tell me if i do a good job
				System.out.println("Successfully, connected to the Database");
				return connection;
			}
			
			catch (SQLException e) {
				//Tell me where I went wrong
				System.out.println(e.getMessage());
				System.exit(0);
			} 
			
			return connection; 
	}

	/**
	 * Executes the query in Part 3 (a).
	 * 
	 * @param connection
	 *            a Connection to your database.
	 * @return the ResultSet an object that contains the data produced by the query.
	 * @throws SQLException
	 *             if a database access error occurs or the given SQL statement produces anything other than a single
	 *             ResultSet object
	 */
	
protected static ResultSet getResultOfPart3A(Connection connection) throws SQLException {
		// (4 points) complete the following line to return a ResultSet obtained by running
		// the query in Part 3 (a).
			
		//Java statement
		Statement st = connection.createStatement();	

	try{
			
			String sqlC = "  SELECT customer_name\n" + 
					"FROM customer\n" + 
					"WHERE\n" + 
					"	customer_name IN(\n" + "		\n" + 
					"	SELECT customer_name\n" + 
					"	FROM \n" + 
					"		depositor\n" + 
					"	WHERE \n" + 
					"		customer_id IN\n" + 
					"	(\n" + 
					"		SELECT\n" + 
					"			customer_id\n" + 
					"		FROM\n" + 
					"			account\n" + 
					"		WHERE\n" + 
					"			balance > 6000\n" + 
					"	)\n" + 
					"	ORDER BY customer_id\n" + 
					")ORDER BY customer_name;";
			
			
			//execute the query
			ResultSet rs = st.executeQuery(sqlC); 
			return rs;
			//st.close();
	} 
		
		
			
	catch(SQLException e) {
			System.out.println("Error");
		} 
		st.close();
		return null;
		
}


	/**
	 * Executes the query in Part 3 (b).
	 *  @param connection
	 *            a Connection to your database.
	 * @return the ResultSet an object that contains the data produced by the query.
	 * @throws SQLException
	 *             if a database access error occurs or the given SQL statement produces anything other than a single
	 *             ResultSet object
	 */
	
	protected static ResultSet getResultOfPart3B(Connection connection) throws SQLException {
		// (4 points) complete the following line to return a ResultSet obtained by running
		// the query in Part 3 (b).
		
		try {
			String sqlB = "SELECT x.account_number\n" + 
					      "FROM depositor as X,\n" + 
						"	 depositor as Y\n" +  "	 \n" + "WHERE \n" + 
						"	X.account_number = Y.account_number AND X.customer_id != Y.customer_id;";
			
			//Java statement
			Statement st = connection.createStatement();
			
			//execute the query
			ResultSet rs = st.executeQuery(sqlB); 
			
			//st.close(); //why won't you work
			return rs;		
	}
		catch(SQLException e) {
			System.out.println("Error " +e.getMessage());
		}		
		return null;
				
			
}		
		
			
	public static void main(String[] argv) throws SQLException {
		
			Connection connection = getConnectionToYourDB();

			// Part 3 (a)
			System.out.println("The names of all all depositors who have an account with a balance greater than $6,000:");
			ResultSet rs1 = getResultOfPart3A(connection);
			
			// (4 points) complete a loop that prints (using System.out.println()) all the relevant customer names.

			while(rs1.next()) {
				String customerName = rs1.getString("customer_name");
				//Print name
				System.out.println(customerName);
			}
				
			
			// Part 3 (b)
			System.out.println("The the account numbers of all joint accounts (i.e., accounts owned by at least two customers):");
			ResultSet rs = getResultOfPart3B(connection);
						
			// (4 points) complete a loop that prints (using System.out.println()) all the relevant account numbers.
			while(rs.next()) {
				String accountNumber = rs.getString("account_number");
				
				//Print name
				System.out.println(accountNumber);
			} 

	}
}