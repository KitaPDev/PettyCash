package com.kitap.lib.bean;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class BEANPettyCashTransaction implements Externalizable {

    private int m_pettyCashID;
    private Date m_datePosting;
    private BigDecimal m_bdAmount;
    private String m_strUsernamePayer;
    private String m_strUsernamePayee;
    private BigDecimal m_bdAmountReturn;
    private Timestamp m_stReturn;
    private String m_strUsernameReturn;
    private String m_strUsernameReturnReceived;
    private String m_strNote;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(m_pettyCashID);
        out.writeObject(m_datePosting);
        out.writeObject(m_bdAmount);
        out.writeUTF(m_strUsernamePayer);
        out.writeUTF(m_strUsernamePayee);
        out.writeObject(m_bdAmountReturn);
        out.writeObject(m_stReturn);
        out.writeUTF(m_strUsernameReturn);
        out.writeUTF(m_strUsernameReturnReceived);
        out.writeUTF(m_strNote);
    }

    @Override
    public void readExternal(ObjectInput in) throws ClassNotFoundException, IOException {
        m_pettyCashID = in.readInt();
        m_datePosting = (Date) in.readObject();
        m_bdAmount = (BigDecimal) in.readObject();
        m_strUsernamePayer = in.readUTF();
        m_strUsernamePayee = in.readUTF();
        m_bdAmountReturn = (BigDecimal) in.readObject();
        m_stReturn = (Timestamp) in.readObject();
        m_strUsernameReturn = in.readUTF();
        m_strUsernameReturnReceived = in.readUTF();
        m_strNote = in.readUTF();
    }

    public int getPettyCashID() { return m_pettyCashID; }
    public void setPettyCashID(int p_pettyCashID) { m_pettyCashID = p_pettyCashID; }

    public Date getDatePosting() {
        return m_datePosting;
    }
    public void setDatePosting(Date p_datePosting) {
        m_datePosting = p_datePosting;
    }

    public BigDecimal getAmount() {
        return m_bdAmount;
    }
    public void setAmount(BigDecimal p_bdAmount) {
        m_bdAmount = p_bdAmount;
    }

    public String getUsernamePayer() {
        return m_strUsernamePayer;
    }
    public void setUsernamePayer(String p_strUsernamePayer) {
        m_strUsernamePayer = p_strUsernamePayer;
    }

    public String getUsernamePayee() {
        return m_strUsernamePayee;
    }
    public void setUsernamePayee(String p_strUsernamePayee) {
        m_strUsernamePayee = p_strUsernamePayee;
    }

    public Timestamp getTimestampReturn() {
        return m_stReturn;
    }
    public void setTimestampReturn(Timestamp p_stReturn) {
        m_stReturn = p_stReturn;
    }

    public BigDecimal getAmountReturn() {
        return m_bdAmountReturn;
    }
    public void setAmountReturn(BigDecimal p_bdAmountReturn) {
        m_bdAmountReturn = p_bdAmountReturn;
    }

    public String getUsernameReturn() {
        return m_strUsernameReturn;
    }
    public void setUsernameReturn(String p_strUsernameReturn) {
        m_strUsernameReturn = p_strUsernameReturn;
    }

    public String getUsernameReturnReceived() {
        return m_strUsernameReturnReceived;
    }
    public void setUsernameReturnReceived(String p_strUsernameReturnReceived) {
        m_strUsernameReturnReceived = p_strUsernameReturnReceived;
    }

    public String getNote() {
        return m_strNote;
    }
    public void setNote(String p_strNote) {
        m_strNote = p_strNote;
    }

    public boolean IsReturned() {
        boolean isReturned = false;

        if(m_strUsernameReturn.length() > 0 && !IsReceived()) {
            isReturned = true;
        }

        return isReturned;
    }

    public boolean IsReceived() {
        boolean isReceived = false;

        if(m_strUsernameReturnReceived.length() > 0) {
            isReceived = true;
        }

        return isReceived;
    }
}
