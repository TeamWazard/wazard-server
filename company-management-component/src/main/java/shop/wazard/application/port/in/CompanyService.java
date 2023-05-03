package shop.wazard.application.port.in;

import org.springframework.dao.PermissionDeniedDataAccessException;
import shop.wazard.dto.RegisterCompanyReqDto;
import shop.wazard.dto.RegisterCompanyResDto;

import java.nio.file.AccessDeniedException;

public interface CompanyService {

    RegisterCompanyResDto registerCompany(RegisterCompanyReqDto registerCompanyReqDto) throws AccessDeniedException, PermissionDeniedDataAccessException, IllegalAccessException;

}
