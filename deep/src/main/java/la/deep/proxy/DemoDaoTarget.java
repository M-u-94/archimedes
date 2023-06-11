package la.deep.proxy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DemoDaoTarget implements IDemoDaoTarget{

    @Override
    public boolean save(String msg){
        log.info("save to mysql :{}",msg);
        return true;
    }
}
