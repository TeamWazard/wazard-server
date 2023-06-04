package shop.wazard.application.port.in;

import shop.wazard.dto.InviteWorkerReqDto;

public interface EmailService {

    String sendEmail(String email);

    String sendInvitationCode(InviteWorkerReqDto inviteWorkerReqDto);
}
