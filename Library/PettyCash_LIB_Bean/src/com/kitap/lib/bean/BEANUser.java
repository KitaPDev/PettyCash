package com.kitap.lib.bean;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

public class BEANUser implements Externalizable {

    private static final long serialVersionUID = 1L;

    private String m_strUserName;
    private String m_strPassword;

    public BEANUser() {}

    public BEANUser(String p_strUserName, String p_strPassword) throws NoSuchAlgorithmException {
        m_strUserName = p_strUserName;
        m_strPassword = encryptPassword(p_strPassword);
    }

    public BEANUser(String userName, String password, double money, boolean isAccountPayable) throws NoSuchAlgorithmException {
        m_strUserName = userName;
        m_strPassword = encryptPassword(password);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(m_strUserName);
        out.writeUTF(m_strPassword);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException {
        m_strUserName = in.readUTF();
        m_strPassword = in.readUTF();
    }

    public String toString() {
        String string = "userName: " + m_strUserName + "\npassword: " + m_strPassword;
        return string;
    }

    private String encryptPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashPassword = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        return hashPassword.toString();
    }

    public String getUserName() { return m_strUserName; }

    public void setUserName(String userName) {
        m_strUserName = userName;
    }

    public String getPassword() {
        return m_strPassword;
    }

    public void setPassword(String password) throws NoSuchAlgorithmException {
        m_strPassword = encryptPassword(password);
    }
}
