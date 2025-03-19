package com.africom.productinventory.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.africom.productinventory.web.rest.TestUtil;

public class FamilleProduitTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FamilleProduit.class);
        FamilleProduit familleProduit1 = new FamilleProduit();
        familleProduit1.setId(1L);
        FamilleProduit familleProduit2 = new FamilleProduit();
        familleProduit2.setId(familleProduit1.getId());
        assertThat(familleProduit1).isEqualTo(familleProduit2);
        familleProduit2.setId(2L);
        assertThat(familleProduit1).isNotEqualTo(familleProduit2);
        familleProduit1.setId(null);
        assertThat(familleProduit1).isNotEqualTo(familleProduit2);
    }
}
