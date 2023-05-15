package shop.wazard.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.wazard.application.port.in.AttendanceService;
import shop.wazard.dto.CommuteRecordReqDto;
import shop.wazard.dto.CommuteRecordResDto;
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

    @PostMapping("/commute/{accountId}")
    public ResponseEntity<CommuteRecordResDto> recordCommute(@PathVariable Long accountId, @Valid @RequestBody CommuteRecordReqDto commuteRecordReqDto) {
        CommuteRecordResDto commuteRecordResDto = attendanceService.recordCommute(commuteRecordReqDto);
        return ResponseEntity.ok(commuteRecordResDto);
    }

}