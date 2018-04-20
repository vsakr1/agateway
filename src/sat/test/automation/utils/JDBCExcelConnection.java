package sat.test.automation.utils;

/**
 * @author singhjag
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import sat.test.automation.core.SuperScript;

public class JDBCExcelConnection extends SuperScript{

	private Connection gConnection = null;

	private String gDriver = "sun.jdbc.odbc.JdbcOdbcDriver";

	private String gUrl = "jdbc:odbc";

	private String sMakeDsn = null;

	public void openExcelConnection(String excelFileName) throws Exception {
		// Use this without setting an ODBC Data Source
		System.out.println(excelFileName + "    &&&&&");
		setSMakeDsn("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="
				+ excelFileName + ";READONLY=false");
		try {
			Class.forName(getGDriver());
			setGConnection(DriverManager.getConnection(getSMakeDsn()));
		} catch (ClassNotFoundException e) {
			logger.error("Error while opening database looks like file is not exist '");
			
			throw new Exception(
					"ClassNotFoundException occurred while opening a connection to database.");

		} catch (SQLException e) {
			logger.error("Error while opening database looks like file is not exist '");
			

			throw new Exception(
					"SQLException occurred while opening a connection to database.");
		}
	}

	public void closeExcelConnection() throws Exception {
		try {
			getGConnection().close();
		} catch (SQLException e) {
			throw new Exception(
					"SQLException occurred while closing a connection to database.");
		} finally {

		}
	}

	public ResultSet getResultSet(String squery) throws Exception {
		ResultSet rs;
		try {
			Statement stmt = getGConnection().createStatement();
			rs = stmt.executeQuery(squery);
		} catch (SQLException e) {
			throw new Exception(
					"SQLException occurred while fetching a result set.");
		}
		return rs;
	}

	public void updateRow(String squery) throws Exception {
		try {
			Statement stmt = getGConnection().createStatement();
			stmt.executeUpdate(squery);
		} catch (SQLException e) {
			throw new Exception("SQLException occurred while updating a row.");
		} finally {
			closeExcelConnection();
		}
	}

	public void createRow(String squery) throws Exception {
		try {
			Statement stmt = getGConnection().createStatement();
			stmt.executeUpdate(squery);
		} catch (SQLException e) {
			throw new Exception("SQLException occurred while updating a row.");
		}
	}

	public Connection getGConnection() {
		return gConnection;
	}

	public void setGConnection(Connection connection) {
		gConnection = connection;
	}

	public String getGDriver() {
		return gDriver;
	}

	public void setGDriver(String driver) {
		gDriver = driver;
	}

	public String getGUrl() {
		return gUrl;
	}

	public void setGUrl(String url) {
		gUrl = url;
	}

	public String getSMakeDsn() {
		return sMakeDsn;
	}

	public void setSMakeDsn(String makeDsn) {
		sMakeDsn = makeDsn;
	}

}
