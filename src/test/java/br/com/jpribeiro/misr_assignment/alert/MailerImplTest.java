package br.com.jpribeiro.misr_assignment.alert;

import br.com.jpribeiro.misr_assignment.model.PlotOfLand;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class MailerImplTest {

    @InjectMocks
    MailerImpl mailerImpl = new MailerImpl();

    @Mock
    private JavaMailSender javaMailSender;

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(mailerImpl, "alertRecipient", "recipient@email.com");
        ReflectionTestUtils.setField(mailerImpl, "maxNumberOfAttempts", 10);
    }

    @Test
    public void testSendAlertEmail() {
        mailerImpl.sendAlertEmail(new PlotOfLand(), PlotOfLand.IDLE);
    }

}
