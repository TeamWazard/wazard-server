package shop.wazard.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.wazard.application.port.in.CompanyManagementService;
import shop.wazard.dto.*;
import shop.wazard.util.aop.Certification;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
class CompanyController {

    private final CompanyManagementService companyManagementService;

    @Certification
    @PostMapping("/register/{accountId}")
    public ResponseEntity<RegisterCompanyResDto> registerCompany(@PathVariable Long accountId, @Valid @RequestBody RegisterCompanyReqDto registerCompanyReqDto) {
        RegisterCompanyResDto registerCompanyResDto = companyManagementService.registerCompany(registerCompanyReqDto);
        return ResponseEntity.ok(registerCompanyResDto);
    }

    @Certification
    @PatchMapping("/info/{accountId}")
    public ResponseEntity<UpdateCompanyInfoResDto> updateCompanyInfo(@PathVariable Long accountId, @Valid @RequestBody UpdateCompanyInfoReqDto updateCompanyInfoReqDto) {
        UpdateCompanyInfoResDto updateCompanyInfoResDto = companyManagementService.updateCompanyInfo(updateCompanyInfoReqDto);
        return ResponseEntity.ok(updateCompanyInfoResDto);
    }

    @Certification
    @PatchMapping("/delete/{accountId}")
    public ResponseEntity<DeleteCompanyResDto> deleteCompany(@PathVariable Long accountId, @Valid @RequestBody DeleteCompanyReqDto deleteCompanyReqDto) {
        DeleteCompanyResDto deleteCompanyResDto = companyManagementService.deleteCompany(deleteCompanyReqDto);
        return ResponseEntity.ok(deleteCompanyResDto);
    }

}
