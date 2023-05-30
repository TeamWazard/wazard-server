package shop.wazard.application.port.out;

import shop.wazard.application.domain.ReplaceInfo;

public interface WorkerPort {

    void saveReplace(String email, ReplaceInfo replaceInfo);

}
