package application;

import java.net.Socket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnection {
	Connection connection;
    Statement statement;
    String SQL;
    String url;
    String username;
    String password;
    Socket client;
    int Port;
    String Host;

  
  public DBConnection(String url, String username, String password, String Host, int Port) {   
	    this.url = url;
        this.username = username;
        this.password = password;
        this.Host = Host; 
        this.Port = Port;
  }

   
 public Connection connexionDatabase() {

      
  try {
            Class.forName("com.mysql.jdbc.Driver");
          
  connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) 
{System.err.println(e.getMessage());//
        }
        return connection;
    }

}
