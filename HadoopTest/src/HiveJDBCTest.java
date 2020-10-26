import java.sql.SQLException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.hadoop.security.UserGroupInformation;

import java.sql.DriverManager;
public class HiveJDBCTest {
  private static String driverName = "org.apache.hive.jdbc.HiveDriver";
  /**
   * @param args
   * @throws SQLException
   */
  public static void main(String[] args) throws SQLException {
      try {
      Class.forName(driverName);
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      System.exit(1);
    }
    //replace "hive" here with the name of the user the queries should run as
    Connection con = DriverManager.getConnection("jdbc:hive2://ayxnbda1dm04.sce.com:10000/default;principal=hive/_HOST@SCET.EIXT.COM", "hive", "");
    
    org.apache.hadoop.conf.Configuration conf = new  org.apache.hadoop.conf.Configuration();
    conf.set("hadoop.security.authentication", "Kerberos");
    UserGroupInformation.setConfiguration(conf);
    try {
    	//replace the keytab location accordingly
		UserGroupInformation.loginUserFromKeytab("SCET.EIXT.COM", "/etc/security/keytabs/sapbodsadm.keytab");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    Statement stmt = con.createStatement();
    String tableName = "testHiveDriverTable";
    stmt.execute("drop table if exists " + tableName);
    stmt.execute("create table " + tableName + " (key int, value string)");
    // show tables
    // String sql = "show tables '" + tableName + "'";
    String sql = ("show tables");
    ResultSet res = stmt.executeQuery(sql);
    if (res.next()) {
        System.out.println(res.getString(1));
      }
  }
}
