package shop.wazard.application.port.out;

import shop.wazard.application.domain.WorkRecordForMyPage;

import java.util.List;

public interface WorkRecordForMyPagePort {

    WorkRecordForMyPage getMyPastWorkRecord(Long accountId, Long companyId);

    List<WorkRecordForMyPage> getMyTotalPastRecord(Long accountId);

}
