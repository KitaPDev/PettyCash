package com.kita.pettycash.server.services;

import com.kita.lib.rpc.server.DatabaseAdapter;
import com.kitap.lib.bean.BEANPettyCashTransaction;
import com.kitap.lib.bean.BEANPettyCashUsage;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PettyCash {

    private final String DB_URL = "jdbc:postgresql://bridsys.com:57206/pettycash";
    private final String USER = "ise_dev";
    private final String PASSWORD = "icepwd12";

    public void createNewTransaction(BEANPettyCashTransaction beanPettyCashTransaction) throws SQLException {
        Date datePosting = beanPettyCashTransaction.getDatePosting();
        BigDecimal bdAmount = beanPettyCashTransaction.getAmount();
        String strPayerUsername = beanPettyCashTransaction.getUsernamePayer();
        String strPayeeUsername = beanPettyCashTransaction.getUsernamePayee();
        String strNote = beanPettyCashTransaction.getNote();

        DatabaseAdapter databaseAdapter = new DatabaseAdapter();

        Connection conn = databaseAdapter.establishConnection(DB_URL, USER, PASSWORD);

        String sqlGetPayerUserID = "SELECT UserID FROM User_tbl WHERE username = " + "\'" + strPayerUsername + "\'";
        String sqlGetPayeeUserID = "SELECT UserID FROM User_tbl WHERE username = " + "\'" + strPayeeUsername + "\'";

        int payerUserID;
        int payeeUserID;

        ResultSet rs = databaseAdapter.getResultSet(conn, sqlGetPayerUserID);
        rs.next();
        payerUserID = rs.getInt("UserID");

        rs = databaseAdapter.getResultSet(conn, sqlGetPayeeUserID);
        rs.next();
        payeeUserID = rs.getInt("UserID");

        String sqlNewTransaction = "INSERT INTO PettyCash_tbl (Date_Posting, Amount, UserID_Payer, UserID_Payee, Note) VALUES (\'" +
                datePosting + "\', " + bdAmount + ", " + payerUserID + ", " + payeeUserID + ",\'" + strNote + "\');";

        databaseAdapter.update(conn, sqlNewTransaction);

        conn.close();
    }

    public List<BEANPettyCashTransaction> getPettyCashTransactions(String strUsername) throws SQLException {
        List<BEANPettyCashTransaction> lsBEANPettyCashTransaction = new ArrayList<>();

        DatabaseAdapter databaseAdapter = new DatabaseAdapter();

        Connection conn = databaseAdapter.establishConnection(DB_URL, USER, PASSWORD);

        String sqlGetPayerUserID = "SELECT UserID FROM User_tbl WHERE Username = " + "\'" + strUsername + "\'";

        int userID;

        ResultSet rs = databaseAdapter.getResultSet(conn, sqlGetPayerUserID);
        rs.next();
        userID = rs.getInt("UserID");

        String sqlGetPettyCashTransactions = "SELECT * FROM PettyCash_tbl WHERE UserID_Payer = " + userID +
                " OR UserID_Payee = " + userID + ";";

        rs = databaseAdapter.getResultSet(conn, sqlGetPettyCashTransactions);

        while(rs.next()) {
            BEANPettyCashTransaction beanPettyCashTransaction = new BEANPettyCashTransaction();

            int pettyCashID = rs.getInt("PettyCashID");
            Date datePosting = rs.getDate("Date_Posting");
            BigDecimal bdAmount = rs.getBigDecimal("Amount");
            String strPayerUsername = null;
            String strPayeeUsername = null;

            ResultSet resultSet;

            int payerUserID = rs.getInt("UserID_Payer");
            int payeeUserID = rs.getInt("UserID_Payee");
            if(payerUserID == userID) {
                strPayerUsername = strUsername;

                String sqlGetPayeeUsername = "SELECT Username FROM User_tbl WHERE UserID = " + payeeUserID + ";";
                resultSet = databaseAdapter.getResultSet(conn, sqlGetPayeeUsername);
                if(resultSet.next()) strPayeeUsername = resultSet.getString("Username");

            } else if(payeeUserID == userID) {
                strPayeeUsername = strUsername;

                String sqlGetPayerUsername = "SELECT Username FROM User_tbl WHERE UserID = " + payerUserID + ";";
                resultSet = databaseAdapter.getResultSet(conn, sqlGetPayerUsername);
                if(resultSet.next()) strPayerUsername = resultSet.getString("Username");

            }

            BigDecimal bdAmountReturn = rs.getBigDecimal("Amount_Return");
            Timestamp stReturn = rs.getTimestamp("ST_Return");
            String strReturnUsername = null;
            String strReturnReceivedUsername = null;

            int returnUserID = rs.getInt("UserID_Return");
            int returnReceivedUserID = rs.getInt("UserID_ReturnReceived");

            String sqlGetReturnUsername = "SELECT Username FROM User_tbl WHERE UserID = " + returnUserID + ";";
            String sqlGetReturnReceivedUsername = "SELECT Username FROM User_tbl WHERE UserID = " + returnReceivedUserID
                    + ";";

            resultSet = databaseAdapter.getResultSet(conn, sqlGetReturnUsername);
            if(resultSet.next()) strReturnUsername = resultSet.getString("Username");
            if(strReturnUsername == null) strReturnUsername = "";

            resultSet = databaseAdapter.getResultSet(conn, sqlGetReturnReceivedUsername);
            if(resultSet.next()) strReturnReceivedUsername = resultSet.getString("Username");
            if(strReturnReceivedUsername == null) strReturnReceivedUsername = "";

            String strNote;
            strNote = rs.getString("Note");

            beanPettyCashTransaction.setPettyCashID(pettyCashID);
            beanPettyCashTransaction.setDatePosting(datePosting);
            beanPettyCashTransaction.setAmount(bdAmount);
            beanPettyCashTransaction.setUsernamePayee(strPayeeUsername);
            beanPettyCashTransaction.setUsernamePayer(strPayerUsername);
            beanPettyCashTransaction.setAmountReturn(bdAmountReturn);
            beanPettyCashTransaction.setTimestampReturn(stReturn);
            beanPettyCashTransaction.setUsernameReturn(strReturnUsername);
            beanPettyCashTransaction.setUsernameReturnReceived(strReturnReceivedUsername);
            beanPettyCashTransaction.setNote(strNote);

            lsBEANPettyCashTransaction.add(beanPettyCashTransaction);
        }

        return lsBEANPettyCashTransaction;
    }

    public void deletePettyCashTransaction(BEANPettyCashTransaction p_BEANPettyCashTransaction) throws SQLException {
        DatabaseAdapter databaseAdapter = new DatabaseAdapter();

        Connection conn = databaseAdapter.establishConnection(DB_URL, USER, PASSWORD);

        int pettyCashID = p_BEANPettyCashTransaction.getPettyCashID();
        String sqlDeletePettyCashTransaction = "DELETE FROM PettyCash_tbl WHERE PettyCashID = " + pettyCashID + ";";

        databaseAdapter.update(conn, sqlDeletePettyCashTransaction);
    }

    public void setUserReturn(BEANPettyCashTransaction p_BEANPettyCashTransaction) throws SQLException {
        DatabaseAdapter databaseAdapter = new DatabaseAdapter();

        Connection conn = databaseAdapter.establishConnection(DB_URL, USER, PASSWORD);

        int pettyCashID = p_BEANPettyCashTransaction.getPettyCashID();

        int returnUserID;
        String sqlGetReturnUserID = "SELECT UserID FROM User_tbl WHERE Username = " + "\'" + p_BEANPettyCashTransaction.getUsernameReturn()
                + "\';";
        ResultSet rs = databaseAdapter.getResultSet(conn, sqlGetReturnUserID);
        rs.next();
        returnUserID = rs.getInt("UserID");

        String sqlSetUserReturn = "UPDATE PettyCash_tbl SET UserID_Return = " + returnUserID + " WHERE PettyCashID = "
                + pettyCashID + ";";

        databaseAdapter.update(conn, sqlSetUserReturn);
    }

    public void setUserReturnReceived(BEANPettyCashTransaction p_BEANPettyCashTransaction) throws SQLException {
        DatabaseAdapter databaseAdapter = new DatabaseAdapter();

        Connection conn = databaseAdapter.establishConnection(DB_URL, USER, PASSWORD);

        int pettyCashID = p_BEANPettyCashTransaction.getPettyCashID();

        int returnReceivedUserID;
        String sqlGetReturnReceivedUserID = "SELECT UserID FROM User_tbl WHERE Username = " + "\'" + p_BEANPettyCashTransaction.getUsernameReturnReceived()
                + "\'";
        ResultSet rs = databaseAdapter.getResultSet(conn, sqlGetReturnReceivedUserID);
        rs.next();
        returnReceivedUserID = rs.getInt("UserID");

        String sqlSetUserReturnReceived = "UPDATE PettyCash_tbl SET UserID_ReturnReceived = " + returnReceivedUserID + " WHERE PettyCashID = "
                + pettyCashID + ";";

        databaseAdapter.update(conn, sqlSetUserReturnReceived);
    }

}
