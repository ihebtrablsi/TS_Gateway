package com.africom.clientsales.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.africom.clientsales.web.rest.TestUtil;

public class ActionCommercialeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActionCommercialeDTO.class);
        ActionCommercialeDTO actionCommercialeDTO1 = new ActionCommercialeDTO();
        actionCommercialeDTO1.setId(1L);
        ActionCommercialeDTO actionCommercialeDTO2 = new ActionCommercialeDTO();
        assertThat(actionCommercialeDTO1).isNotEqualTo(actionCommercialeDTO2);
        actionCommercialeDTO2.setId(actionCommercialeDTO1.getId());
        assertThat(actionCommercialeDTO1).isEqualTo(actionCommercialeDTO2);
        actionCommercialeDTO2.setId(2L);
        assertThat(actionCommercialeDTO1).isNotEqualTo(actionCommercialeDTO2);
        actionCommercialeDTO1.setId(null);
        assertThat(actionCommercialeDTO1).isNotEqualTo(actionCommercialeDTO2);
    }
}
