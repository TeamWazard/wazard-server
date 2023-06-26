package shop.wazard.application.port.out;

import shop.wazard.dto.RegisterContractInfoReqDto;

public interface ContractForWorkerManagementPort {
    void registerContractInfo(RegisterContractInfoReqDto registerContractInfoReqDto);
}
