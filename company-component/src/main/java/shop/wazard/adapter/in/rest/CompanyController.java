package shop.wazard.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.wazard.application.port.in.CompanyService;
import shop.wazard.dto.*;
import shop.wazard.util.aop.Certification;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
class CompanyController {

    private final CompanyService companyService;

    @Certification
    @PostMapping("/register/{accountId}")
    public ResponseEntity<RegisterCompanyResDto> registerCompany(@PathVariable Long accountId, @Valid @RequestBody RegisterCompanyReqDto registerCompanyReqDto) {
        RegisterCompanyResDto registerCompanyResDto = companyService.registerCompany(registerCompanyReqDto);
        return ResponseEntity.ok(registerCompanyResDto);
    }

    @Certification
    @PatchMapping("/info/{accountId}")
    public ResponseEntity<UpdateCompanyInfoResDto> updateCompanyInfo(@PathVariable Long accountId, @Valid @RequestBody UpdateCompanyInfoReqDto updateCompanyInfoReqDto) {
        UpdateCompanyInfoResDto updateCompanyInfoResDto = companyService.updateCompanyInfo(updateCompanyInfoReqDto);
        return ResponseEntity.ok(updateCompanyInfoResDto);
    }

    @Certification
    @PatchMapping("/delete/{accountId}")
    public ResponseEntity<DeleteCompanyResDto> deleteCompany(@PathVariable Long accountId, @Valid @RequestBody DeleteCompanyReqDto deleteCompanyReqDto) {
        DeleteCompanyResDto deleteCompanyResDto = companyService.deleteCompany(deleteCompanyReqDto);
        return ResponseEntity.ok(deleteCompanyResDto);
    }

    @Certification
    @GetMapping("/own/{accountId}")
    public ResponseEntity<List<GetOwnedCompanyResDto>> getOwnedCompanyList(@PathVariable Long accountId, @Valid @RequestBody GetOwnedCompanyReqDto getOwnedCompanyReqDto) {
        List<GetOwnedCompanyResDto> getOwnedCompanyList = companyService.getOwnedCompanyList(accountId, getOwnedCompanyReqDto);
        return ResponseEntity.ok(getOwnedCompanyList);
    }

}
