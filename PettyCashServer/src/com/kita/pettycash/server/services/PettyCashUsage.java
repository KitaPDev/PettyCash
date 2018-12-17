package com.kita.pettycash.server.services;

import com.kita.lib.rpc.server.DatabaseAdapter;
import com.kitap.lib.bean.BEANPettyCashUsage;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PettyCashUsage {

    private final String DB_URL = "jdbc:postgresql://bridsys.com:57206/pettycash";
    private final String USER = "ise_dev";
    private final String PASSWORD = "icepwd12";

    public void createNewUsage(BEANPettyCashUsage beanPettyCashUsage) throws SQLException {
        int pettyCashID = beanPettyCashUsage.getPettyCashID();
        Timestamp stUsage = beanPettyCashUsage.getDateTimeUsage();
        BigDecimal bdUsageAmount = beanPettyCashUsage.getUsageAmount();
        String strUsage = beanPettyCashUsage.getUsage();

        DatabaseAdapter databaseAdapter = new DatabaseAdapter();

        Connection conn = databaseAdapter.establishConnection(DB_URL, USER, PASSWORD);

        String sqlNewUsage = "INSERT INTO PettyCashUsage_tbl (PettyCashID, DateTime_Usage, Amount, Usage) VALUES (" +
                pettyCashID + ", \'" + stUsage + "\', " + bdUsageAmount + ", \'" + strUsage + "\'" + ");";

        databaseAdapter.update(conn, sqlNewUsage);

        conn.close();
    }

    public List<BEANPettyCashUsage> getPettyCashUsages(String p_strPettyCashTransactionID) throws SQLException {
        List<BEANPettyCashUsage> lsBEANPettyCashUsage = new ArrayList<>();

        DatabaseAdapter databaseAdapter = new DatabaseAdapter();

        Connection conn = databaseAdapter.establishConnection(DB_URL, USER, PASSWORD);

        int pettyCashTransactionID = Integer.parseInt(p_strPettyCashTransactionID);
        String sqlGetPettyCashUsages = "SELECT * FROM PettyCashUsage_tbl WHERE PettyCashID = " + pettyCashTransactionID
                + ";";
        ResultSet resultSet = databaseAdapter.getResultSet(conn, sqlGetPettyCashUsages);

        while(resultSet.next()) {
            BEANPettyCashUsage beanPettyCashUsage = new BEANPettyCashUsage();

            int pettyCashUsageID = resultSet.getInt("PettyCashUsageID");
            int pettyCashID = resultSet.getInt("PettyCashID");
            Timestamp stUsage = resultSet.getTimestamp("DateTime_Usage");
            BigDecimal bdUsageAmount = resultSet.getBigDecimal("Amount");
            String strUsage = resultSet.getString("Usage");

            beanPettyCashUsage.setPettyCashUsageID(pettyCashUsageID);
            beanPettyCashUsage.setPettyCashID(pettyCashID);
            beanPettyCashUsage.setDateTimeUsage(stUsage);
            beanPettyCashUsage.setUsageAmount(bdUsageAmount);
            beanPettyCashUsage.setUsage(strUsage);

            lsBEANPettyCashUsage.add(beanPettyCashUsage);
        }

        return lsBEANPettyCashUsage;
    }

    public void deletePettyCashUsage(BEANPettyCashUsage p_BEANPettyCashUsage) throws SQLException {
        DatabaseAdapter databaseAdapter = new DatabaseAdapter();

        Connection conn = databaseAdapter.establishConnection(DB_URL, USER, PASSWORD);

        int PettyCashUsageID = p_BEANPettyCashUsage.getPettyCashUsageID();

        String sqlDeletePettyCashUsage = "DELETE FROM PettyCashUsage_tbl WHERE PettyCashID = " + PettyCashUsageID + ";";

        databaseAdapter.update(conn, sqlDeletePettyCashUsage);
    }

    public void storeImage() {

    }

}
