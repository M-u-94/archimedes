package la.archimedes.common.service.impl;

import lombok.extern.slf4j.Slf4j;
import la.archimedes.common.service.IDemoDaoTarget;

@Slf4j
public class DemoDaoTarget implements IDemoDaoTarget {

    @Override
    public boolean save(String msg){
        log.info("save to mysql :{}",msg);
        return true;
    }
}
