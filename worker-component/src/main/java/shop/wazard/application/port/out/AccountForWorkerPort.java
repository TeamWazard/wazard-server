package shop.wazard.application.port.out;

import shop.wazard.application.domain.AccountForWorker;

public interface AccountForWorkerPort {

    AccountForWorker findAccountByEmail(String email);
}
