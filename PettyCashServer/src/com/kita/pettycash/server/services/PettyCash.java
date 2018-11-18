package com.kita.pettycash.server.services;

import com.kita.lib.rpc.server.DatabaseAdapter;
import com.kitap.lib.bean.BEANPettyCashTransaction;

import java.math.BigDecimal;
import java.sql.*;
import java.text.Bidi;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        payerUserID = rs.getInt("UserID");

        rs = databaseAdapter.getResultSet(conn, sqlGetPayeeUserID);
        payeeUserID = rs.getInt("UserID");

        String sqlNewTransaction = "INSERT INTO PettyCash_tbl (Date_Posting, Amount, UserID_Payer, UserID_Payee, Note) VALUES (" +
                datePosting + ", " + bdAmount + ", " + payerUserID + ", " + payeeUserID + strNote + ");";

        databaseAdapter.update(conn, sqlNewTransaction);

        conn.close();
    }

    public List<BEANPettyCashTransaction> getPettyCashTransactions(String strUsername) throws SQLException {
        List<BEANPettyCashTransaction> lsBEANPettyCashTransaction = new ArrayList<>();

        DatabaseAdapter databaseAdapter = new DatabaseAdapter();

        Connection conn = databaseAdapter.establishConnection(DB_URL, USER, PASSWORD);

        String sqlGetPayerUserID = "SELECT UserID FROM User_tbl WHERE username = " + "\'" + strUsername + "\'";

        int userID;

        ResultSet rs = databaseAdapter.getResultSet(conn, sqlGetPayerUserID);
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
                strPayeeUsername = resultSet.getString("Username");

            } else if(payeeUserID == userID) {
                strPayeeUsername = strUsername;

                String sqlGetPayerUsername = "SELECT Username FROM User_tbl WHERE UserID = " + payerUserID + ";";
                resultSet = databaseAdapter.getResultSet(conn, sqlGetPayerUsername);
                strPayerUsername = resultSet.getString("Username");

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
            strReturnUsername = resultSet.getString("Username");

            resultSet = databaseAdapter.getResultSet(conn, sqlGetReturnReceivedUsername);
            strReturnReceivedUsername = resultSet.getString("Username");

            String strNote = rs.getString("Note");

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

}
