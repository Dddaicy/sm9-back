package com.sm9.boot.service;

import com.sm9.boot.dao.UkeyDao;
import com.sm9.boot.pojo.Ukey;
import org.springframework.stereotype.Service;

@Service
public class UkeyService {

    private final UkeyDao ukeyDao;

    public UkeyService(UkeyDao ukeyDao) {
        this.ukeyDao = ukeyDao;
    }

    public Ukey getUkeyByCenterDeviceId(String centerDeviceId){
        return ukeyDao.getUkeyByCenterDeviceId(centerDeviceId);
    }
}
