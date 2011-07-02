// package com.github.ariel17;

import java.lang.String;

import java.sql.*;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;


/** MSSQL Test Connection script.
 * @author ariel17
 * @version 1    
*/
public class MSSQLTestConnection { 

    /** Tries to connect to MSSQL server with given parameters.
     * @param IP
     * @param PORT
     * @param USERNAME
     * @param PASSWORD
     * @return boolean
    */
    // private boolean test_connection(final String IP, final int PORT, 
    //         final String USERNAME, final String PASSWORD) {
    private boolean testConnection(String IP, int PORT, 
            String USERNAME, String PASSWORD, String DBNAME) {
        
        System.out.println(">>> Testing MSSQL connection");
        String params = String.format(
                "ip: %s, port: %d, username: '%s', password: '%s', " + 
                "dbname: '%s'", IP, PORT, USERNAME, PASSWORD, DBNAME);
        System.out.println(params);

        String connString = String.format("jdbc:jtds:sqlserver://%s:%d/%s", IP, PORT, DBNAME);
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            System.out.println("Connecting...");
            Connection conn = DriverManager.getConnection(connString, USERNAME, PASSWORD);
            System.out.println(">>> Connected! :)");            
            conn.close();
            return true;
        }
        catch (Exception e)
        {
            System.out.println(">>> Failed trying to connect: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    private void showHelp(Options options) {
        new HelpFormatter().printHelp(
                MSSQLTestConnection.class.getCanonicalName(), options);
    }

    private CommandLine loadArgs(String[] args) {
        // create the Options
        Options options = new Options();

        Option opt = new Option("h", "help", false, "This help message.");
        options.addOption(opt);

        opt = new Option("i", "ip", true, "MSSQL server IP.");
        opt.setRequired(true);
        options.addOption(opt);

        opt = new Option("p", "port", true, "MSSQL server port.");
        opt.setRequired(true);
        options.addOption(opt);

        opt = new Option("u", "user", true, "Username to test connection.");
        opt.setRequired(true);
        options.addOption(opt);

        opt = new Option("w", "password", true, "Password to use with " + 
                "username to test connection.");
        opt.setRequired(true);
        options.addOption(opt);

        opt = new Option("b", "db", true, "Database name to connect.");
        opt.setRequired(true);
        options.addOption(opt);

		CommandLineParser parser = new BasicParser();
        CommandLine cmd = null;

		try {
			cmd = parser.parse(options, args);
		} catch (Exception e) {
			System.err.println(e.getMessage());
            this.showHelp(options);
            return null;
        }

        if (cmd.hasOption("h")) { 
            this.showHelp(options);
            return null;
        }

        return cmd;
	}

    public static void main (String [] args) {
        MSSQLTestConnection testconn = new MSSQLTestConnection();
    	CommandLine cmd = testconn.loadArgs(args);
        if (cmd == null) { 
            System.exit(1);
        }

        testconn.testConnection(
                cmd.getOptionValue("ip"), 
                Integer.parseInt(cmd.getOptionValue("port")), 
                cmd.getOptionValue("user"), 
                cmd.getOptionValue("password"),
                cmd.getOptionValue("db"));
    }
}
