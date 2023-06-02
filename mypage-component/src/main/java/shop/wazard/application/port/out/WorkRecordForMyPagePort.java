package shop.wazard.application.port.out;

import java.util.List;
import shop.wazard.application.domain.WorkRecordForMyPage;

public interface WorkRecordForMyPagePort {

    WorkRecordForMyPage getMyPastWorkRecord(Long accountId, Long companyId);

    List<WorkRecordForMyPage> getMyTotalPastRecord(Long accountId);
}
