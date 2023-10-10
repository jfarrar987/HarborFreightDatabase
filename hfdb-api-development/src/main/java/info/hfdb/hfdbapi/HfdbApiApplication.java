package info.hfdb.hfdbapi;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Note: With the new command line arguments feature in the webscraper
 * working, it doesn't want to work copy/pasting it to this project
 * without excluding the DataSourceAutoConfigurationClass. Why the exclusion is
 * required here and not in the web scraper is beyond me - Justin Blalock
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class HfdbApiApplication {

	// Used for interacting with the database
	private static JdbcTemplate jdbcTemplate;
	private static Connection connection;
	private static String loc;
	private static String port;

	public static void main(String[] args) {

		if (!parametersCorrect(args))
			return;

		DataSource datasource = setupCredentials(args[0], args[1], args[2], args[3]);
		jdbcTemplate = new JdbcTemplate(datasource);
		loc = args[4];
		port = args[5];

		// Check to see if can interact with postgres
		try {
			jdbcTemplate.execute("select 'test' as test;");
		} catch (DataAccessException e) {
			System.out.println(
					"Something went wrong with the test sql query. Are your credentials correct? Does the hfdb-database0 database exist?");
			System.out.println("Printing stack trace:");
			e.printStackTrace();
			return;
		}

		try {
			connection = datasource.getConnection();
		} catch (SQLException e) {
			System.out.println("Something went wrong with setting up the connection variable.");
			e.printStackTrace();
			return;
		}

		SpringApplication.run(HfdbApiApplication.class, args);
	}

	/**
	 * Checks to see if the parameters provided through the command line are valid
	 *
	 * @param args The String array containing the command line parameters
	 * @return A boolean whether the parameters are valid or not
	 */
	private static boolean parametersCorrect(String[] args) {

		final String msg = "Please provide the following parameters as such: postgresIPAddress postgresPort postgresUsername postgresPassword webserverIPAddress webserverPort";
		boolean credsReady = (args != null && args.length == 6);

		// This is literally useless. Its so the linter will shut up about args being
		// null. If it's actually null, it won't get this far.
		if (args == null)
			args = new String[0];

		// Verify postgres IP/domain and Port
		credsReady = credsReady && (ipCheck(args[0]) || domCheck(args[0]));
		credsReady = credsReady && portCheck(args[1]);

		// Verify front end webserver IP/domain and port
		credsReady = credsReady && (ipCheck(args[4]) || domCheck(args[4]));
		credsReady = credsReady && portCheck(args[5]);

		if (!credsReady)
			System.out.println(msg);

		return credsReady;
	}

	private static boolean domCheck(String string) {
		return string.matches("^[0-9A-Za-z][0-9A-Za-z\\.]+[0-9A-Za-z]$");
	}

	private static boolean portCheck(String port) {
		// Port check
		boolean validPort = true;
		validPort &= port.matches("^[1-9][0-9]{0,4}$");
		if (validPort) {
			int portVal = Integer.parseInt(port);
			validPort &= (portVal > 1 && portVal < 65536);
		}

		if (!validPort)
			System.out.println("The port " + port + " is not valid");

		return validPort;
	}

	private static boolean ipCheck(String ip) {
		// IP Address check
		String[] octets = ip.split("\\.");

		// Another useless piece of code to make the linter shut up except it applies to
		// octets this time
		if (octets == null)
			octets = new String[0];

		// IP Address check (cont'd)
		boolean ipPass = ((octets != null) && (octets.length == 4));
		for (int i = 0; i < 4 && ipPass; i++) {
			ipPass &= octets[i].matches("^(2[0-4][0-9]|25[0-5]|1[0-9][0-9]|[1-9]?[0-9])$");
		}
		if (!ipPass) {
			System.out.println("The IP address " + ip + " is not valid");
		}

		return ipPass;
	}

	/**
	 * Sets up a datasource object for the creation of a JdbcTemplate object using
	 * the provided IP, port, username, and password
	 *
	 * @param ipAddr   IP address of the postgresql instance
	 * @param port     port number of the postgres instance
	 * @param username username that the webscraper is to assume
	 * @param password password of the user
	 * @return A datasource object for the creation of a JdbcTemplate object
	 */
	private static DataSource setupCredentials(String ipAddr, String port, String username, String password) {
		DriverManagerDataSource datasource = new DriverManagerDataSource();
		datasource.setDriverClassName("org.postgresql.Driver");
		datasource.setUrl("jdbc:postgresql://" + ipAddr + ":" + port + "/hfdb-database0");
		datasource.setUsername(username);
		datasource.setPassword(password);
		return datasource;
	}

	public static Connection getConnection() {
		return connection;
	}

	public static JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public static String getLoc() {
		return loc;
	}

	public static String getPort() {
		return port;
	}
}
