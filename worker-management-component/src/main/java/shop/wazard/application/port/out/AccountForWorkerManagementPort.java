package shop.wazard.application.port.out;

import shop.wazard.application.domain.AccountForWorkerManagement;

public interface AccountForWorkerManagementPort {

    AccountForWorkerManagement findAccountByEmail(String email);
}
