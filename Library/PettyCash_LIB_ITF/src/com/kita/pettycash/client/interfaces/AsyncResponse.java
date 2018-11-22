package com.kita.pettycash.client.interfaces;


import com.kitap.lib.bean.BEANPettyCashTransaction;

import java.util.List;

public interface AsyncResponse {

    void processFinish(Object output) throws Exception;
    void processFinish(List<BEANPettyCashTransaction> lsBEANPettyCashTransaction) throws Exception;
}
