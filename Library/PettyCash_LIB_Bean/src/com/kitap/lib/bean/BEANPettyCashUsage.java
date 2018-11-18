package com.kitap.lib.bean;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class BEANPettyCashUsage implements Externalizable {

    private int m_pettyCashUsageID;
    private int m_pettyCashID;
    private Timestamp m_dtUsage;
    private String m_strUsage;
    private BigDecimal m_usageAmount;
    private String m_strNote;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(m_pettyCashUsageID);
        out.writeInt(m_pettyCashID);
        out.writeObject(m_dtUsage);
        out.writeUTF(m_strUsage);
        out.writeObject(m_usageAmount);
        out.writeUTF(m_strNote);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        m_pettyCashUsageID = in.readInt();
        m_pettyCashID = in.readInt();
        m_dtUsage = (Timestamp) in.readObject();
        m_strUsage = in.readUTF();
        m_usageAmount = (BigDecimal) in.readObject();
        m_strNote = in.readUTF();
    }

    public int getPettyCashUsageID() { return m_pettyCashUsageID; }
    public void setPettyCashUsageID(int p_pettyCashUsageID) {
        m_pettyCashUsageID = p_pettyCashUsageID;
    }

    public int getPettyCashID() { return m_pettyCashID; }
    public void setPettyCashID(int p_pettyCashID) {
        m_pettyCashID = p_pettyCashID;
    }

    public Timestamp getDateTimeUsage() { return m_dtUsage; }
    public void setDateTimeUsage(Timestamp p_dtUsage) {
        m_dtUsage = p_dtUsage;
    }

    public String getUsage() { return m_strUsage; }
    public void setUsage(String p_strUsage) {
        m_strUsage = p_strUsage;
    }

    public BigDecimal getUsageAmount() { return m_usageAmount; }
    public void setUsageAmount(BigDecimal p_usageAmount) {
        m_usageAmount = p_usageAmount;
    }
}
