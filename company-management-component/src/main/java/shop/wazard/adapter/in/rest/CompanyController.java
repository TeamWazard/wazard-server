package shop.wazard.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.wazard.application.port.in.CompanyService;
import shop.wazard.dto.RegisterCompanyReqDto;
import shop.wazard.dto.RegisterCompanyResDto;
import shop.wazard.util.aop.Certification;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
class CompanyController {

    private final CompanyService companyService;

    @Certification
    @PostMapping
    public ResponseEntity<RegisterCompanyResDto> registerCompany(@PathVariable Long accountId, @Valid @RequestBody RegisterCompanyReqDto registerCompanyReqDto) throws AccessDeniedException, IllegalAccessException {
        RegisterCompanyResDto registerCompanyResDto = companyService.registerCompany(registerCompanyReqDto);
        return ResponseEntity.ok(registerCompanyResDto);
    }

}
