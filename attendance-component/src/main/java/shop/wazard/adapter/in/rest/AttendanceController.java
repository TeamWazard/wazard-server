package shop.wazard.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.wazard.application.port.in.AttendanceService;
import shop.wazard.dto.*;
import shop.wazard.util.aop.Certification;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

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

    @PostMapping("/exit/{accountId}")
    public ResponseEntity<RecordExitTimeResDto> recordExitTime(@PathVariable Long accountId, @Valid @RequestBody RecordExitTimeReqDto recordExitTimeReqDto) {
        RecordExitTimeResDto recordExitTimeResDto = attendanceService.recordExitTime(recordExitTimeReqDto);
        return ResponseEntity.ok(recordExitTimeResDto);
    }

    @Certification
    @GetMapping("/employer/{accountId}/{year}/{month}/{day}")
    public ResponseEntity<List<GetAttendanceByDayOfTheWeekResDto>> getAttendancesByDayOfTheWeek(@PathVariable Long accountId, @PathVariable int year, @PathVariable int month, @PathVariable int day, @Valid @RequestBody GetAttendanceByDayOfTheWeekReqDto getAttendanceByDayOfTheWeekReqDto) {
        LocalDate date = LocalDate.of(year, month, day);
        List<GetAttendanceByDayOfTheWeekResDto> getAttendanceResDtoList = attendanceService.getAttendancesByDayOfTheWeek(getAttendanceByDayOfTheWeekReqDto, date);
        return ResponseEntity.ok(getAttendanceResDtoList);
    }

    @Certification
    @GetMapping("/employee/{accountId}/{year}/{month}/{day}")
    public ResponseEntity<List<GetAttendanceByDayOfTheWeekResDto>> getMyAttendanceByDayOfTheWeek(@PathVariable Long accountId, @PathVariable int year, @PathVariable int month, @PathVariable int day, @Valid @RequestBody GetAttendanceByDayOfTheWeekReqDto getAttendanceByDayOfTheWeekReqDto) {
        LocalDate date = LocalDate.of(year, month, day);
        List<GetAttendanceByDayOfTheWeekResDto> getAttendanceByDayOfTheWeekResDtoList = attendanceService.getMyAttendanceByDayOfTheWeek(getAttendanceByDayOfTheWeekReqDto, date);
        return ResponseEntity.ok(getAttendanceByDayOfTheWeekResDtoList);
    }

}
