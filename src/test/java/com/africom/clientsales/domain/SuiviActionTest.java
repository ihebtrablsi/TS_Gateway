package com.africom.clientsales.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.africom.clientsales.web.rest.TestUtil;

public class SuiviActionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SuiviAction.class);
        SuiviAction suiviAction1 = new SuiviAction();
        suiviAction1.setId(1L);
        SuiviAction suiviAction2 = new SuiviAction();
        suiviAction2.setId(suiviAction1.getId());
        assertThat(suiviAction1).isEqualTo(suiviAction2);
        suiviAction2.setId(2L);
        assertThat(suiviAction1).isNotEqualTo(suiviAction2);
        suiviAction1.setId(null);
        assertThat(suiviAction1).isNotEqualTo(suiviAction2);
    }
}
