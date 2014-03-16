// Copyright (c) 2005 William Bogg Cecil. All rights reserved. Use is
// subject to license terms.
// 
// This program is free software; you can redistribute it and/or modify
// it under the terms of the Lesser GNU General Public License as
// published by the Free Software Foundation; either version 2 of the
// License, or (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
// USA

// This code was originally written and compiled on the personal computer
// owned and operated by William Bogg Cecil and no other party may claim 
// ownership of the original code without written consent of William Bogg 
// Cecil. 

// Code maintained by SAS. Students Against Segregation.  
/**	Title:
    SQLCommand
Purpose:
    Provide simple sql support for java.
Coders :
    Wil Cecil
Created:
    February 13, 2007, 12:58 PM
Change Log: 
 */
package SASLib.SQL;

import java.sql.*;
import java.util.*;

/**
 * Provide simple sql support for java.
 * @author Wil Cecil
 */
public class SQLLoad {

    Statement stmt;
    Connection con;
    String login, password, myclass, connectionCode;

    /** 
     * Creates a new instance of SQLCommand with default username and password
     */
    public SQLLoad() {
        this("sa", "", "org.hsqldb.jdbcDriver", "jdbc:hsqldb:file:testdb");
    }

    /** 
     * Creates a new instance of SQLCommand with specified username and password
     * @param log
     * @param pass
     * @param driver Class and Driver to load
     */
    public SQLLoad(String log, String pass, String driver, String connectionCode) {
        login = log;
        password = pass;
        myclass = driver;
        this.connectionCode = connectionCode;
    }

    /**
     * Open The Connection, will throw any vendor specific exceptions if they occur.
     * @throws java.lang.Exception
     */
    public void activate() throws Exception {
        //LOAD Driver
        //try {
        Class.forName(myclass);
        //} catch (Exception e) {
        //    System.out.println("ERROR: failed to load "+myclass+" driver.");
        //    e.printStackTrace();
        //    throw new IllegalStateException("SQL DATABASE ERROR.");
        //}

        //open conection
        //try {
        con = DriverManager.getConnection(connectionCode, login, password);
        System.out.println("Class con :" + con.getClass().toString());
        stmt = con.createStatement();
        System.out.println("Class stmt:" + stmt.getClass().toString());
    //} catch (Exception e) {
    //    System.out.println("ERROR: Failed to initiate");
    //    e.printStackTrace();
    //    throw new IllegalStateException("SQL DATABASE ERROR.");
    //}
    }

    /**
     * Attempts to return a statement to this objects SQL database
     * @return returns a statement to the connection
     * @throws java.lang.IllegalAccessException 
     */
    public Statement getStatement() throws IllegalAccessException {
        if (stmt != null) {
            return stmt;
        } else {
            throw new IllegalAccessException("Statement not ready");
        }
    }

    /**
     * Attempts to return a connection to this objects SQL database
     * @return returns a Connection object of this connection
     * @throws java.lang.IllegalAccessException 
     */
    public Connection getConnection() throws IllegalAccessException {
        if (con != null) {
            return con;
        } else {
            throw new IllegalAccessException("Connection not ready");
        }
    }
}
