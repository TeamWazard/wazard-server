package shop.wazard.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class MemberAccountController {

    private final ControllerConverter controllerConverter;

}
