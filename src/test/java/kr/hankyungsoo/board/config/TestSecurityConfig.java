package kr.hankyungsoo.board.config;
import kr.hankyungsoo.board.domain.UserAccount;
import kr.hankyungsoo.board.dto.UserAccountDto;
import kr.hankyungsoo.board.repository.UserAccountRepository;
import kr.hankyungsoo.board.service.UserAccountService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import(SecurityConfig.class)
public class TestSecurityConfig {

    @MockBean private UserAccountRepository userAccountRepository;

    @BeforeTestMethod
    public void securitySetUp() {
        given(userAccountRepository.findById(anyString())).willReturn(Optional.of(UserAccount.of(
                "hanksTest",
                "pw",
                "hanks-test@email.com",
                "hanks-test",
                "test memo"
        )));
    }

}
