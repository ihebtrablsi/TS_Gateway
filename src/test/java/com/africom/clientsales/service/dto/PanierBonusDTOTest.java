package com.africom.clientsales.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.africom.clientsales.web.rest.TestUtil;

public class PanierBonusDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PanierBonusDTO.class);
        PanierBonusDTO panierBonusDTO1 = new PanierBonusDTO();
        panierBonusDTO1.setId(1L);
        PanierBonusDTO panierBonusDTO2 = new PanierBonusDTO();
        assertThat(panierBonusDTO1).isNotEqualTo(panierBonusDTO2);
        panierBonusDTO2.setId(panierBonusDTO1.getId());
        assertThat(panierBonusDTO1).isEqualTo(panierBonusDTO2);
        panierBonusDTO2.setId(2L);
        assertThat(panierBonusDTO1).isNotEqualTo(panierBonusDTO2);
        panierBonusDTO1.setId(null);
        assertThat(panierBonusDTO1).isNotEqualTo(panierBonusDTO2);
    }
}
