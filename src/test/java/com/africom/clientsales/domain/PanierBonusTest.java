package com.africom.clientsales.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.africom.clientsales.web.rest.TestUtil;

public class PanierBonusTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PanierBonus.class);
        PanierBonus panierBonus1 = new PanierBonus();
        panierBonus1.setId(1L);
        PanierBonus panierBonus2 = new PanierBonus();
        panierBonus2.setId(panierBonus1.getId());
        assertThat(panierBonus1).isEqualTo(panierBonus2);
        panierBonus2.setId(2L);
        assertThat(panierBonus1).isNotEqualTo(panierBonus2);
        panierBonus1.setId(null);
        assertThat(panierBonus1).isNotEqualTo(panierBonus2);
    }
}
