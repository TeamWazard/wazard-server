package shop.wazard.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.wazard.application.port.in.AttendanceService;
import shop.wazard.dto.MarkingAbsentReqDto;
import shop.wazard.dto.MarkingAbsentResDto;
import shop.wazard.dto.RecordEnterTimeReqDto;
import shop.wazard.dto.RecordEnterTimeResDto;

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

    @PostMapping("/enter/{accountId}")
    public ResponseEntity<RecordEnterTimeResDto> recordEnterTime(@PathVariable Long accountId, @Valid @RequestBody RecordEnterTimeReqDto recordEnterTimeReqDto) {
        RecordEnterTimeResDto recordEnterTimeResDto = attendanceService.recordEnterTime(recordEnterTimeReqDto);
        return ResponseEntity.ok(recordEnterTimeResDto);
    }

}
