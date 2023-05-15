package shop.wazard.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.wazard.application.port.in.AttendanceService;
import shop.wazard.dto.GoToWorkReqDto;
import shop.wazard.dto.GoToWorkResDto;
import shop.wazard.dto.MarkingAbsentReqDto;
import shop.wazard.dto.MarkingAbsentResDto;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attendance")
class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/absent/{accountId}")
    public ResponseEntity<MarkingAbsentResDto> markingAbsent(@PathVariable Long accountId, @Valid @RequestBody MarkingAbsentReqDto markingAbsentReqDto) {
        MarkingAbsentResDto markingAbsentResDto = attendanceService.markingAbsent(markingAbsentReqDto);
        return ResponseEntity.ok(markingAbsentResDto);
    }

    @PostMapping("/attend/{accountId}")
    public ResponseEntity<GoToWorkResDto> goToWork(@PathVariable Long accountId, @Valid @RequestBody GoToWorkReqDto recordAttendanceReqDto) {
        GoToWorkResDto goToWorkResDto = attendanceService.goToWork(recordAttendanceReqDto);
        return ResponseEntity.ok(goToWorkResDto);
    }

}
