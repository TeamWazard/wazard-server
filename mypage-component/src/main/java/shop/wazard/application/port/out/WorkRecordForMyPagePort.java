package shop.wazard.application.port.out;

import shop.wazard.application.domain.WorkRecordForMyPage;

public interface WorkRecordForMyPagePort {

    WorkRecordForMyPage getMyPastWorkRecord(Long accountId, Long companyId);

}
