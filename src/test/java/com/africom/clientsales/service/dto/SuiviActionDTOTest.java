package com.africom.clientsales.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.africom.clientsales.web.rest.TestUtil;

public class SuiviActionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SuiviActionDTO.class);
        SuiviActionDTO suiviActionDTO1 = new SuiviActionDTO();
        suiviActionDTO1.setId(1L);
        SuiviActionDTO suiviActionDTO2 = new SuiviActionDTO();
        assertThat(suiviActionDTO1).isNotEqualTo(suiviActionDTO2);
        suiviActionDTO2.setId(suiviActionDTO1.getId());
        assertThat(suiviActionDTO1).isEqualTo(suiviActionDTO2);
        suiviActionDTO2.setId(2L);
        assertThat(suiviActionDTO1).isNotEqualTo(suiviActionDTO2);
        suiviActionDTO1.setId(null);
        assertThat(suiviActionDTO1).isNotEqualTo(suiviActionDTO2);
    }
}
