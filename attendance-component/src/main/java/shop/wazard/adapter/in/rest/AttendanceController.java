package shop.wazard.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.wazard.application.port.in.AttendanceService;
import shop.wazard.dto.*;

import javax.validation.Valid;
import java.time.LocalDate;

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

    @GetMapping("/employee/{accountId}/{year}/{month}/{day}")
    public ResponseEntity<GetAttendanceResDto> getMyAttendance(@PathVariable Long accountId, @PathVariable int year, @PathVariable int month, @PathVariable int day, @Valid @RequestBody GetAttendanceReqDto getAttendanceReqDto) {
        LocalDate date = LocalDate.of(year, month, day);
        GetAttendanceResDto getAttendanceResDto = attendanceService.getMyAttendance(getAttendanceReqDto, date);
        return ResponseEntity.ok(getAttendanceResDto);
    }

}
