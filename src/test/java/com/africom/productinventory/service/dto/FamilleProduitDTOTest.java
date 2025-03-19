package com.africom.productinventory.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.africom.productinventory.web.rest.TestUtil;

public class FamilleProduitDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FamilleProduitDTO.class);
        FamilleProduitDTO familleProduitDTO1 = new FamilleProduitDTO();
        familleProduitDTO1.setId(1L);
        FamilleProduitDTO familleProduitDTO2 = new FamilleProduitDTO();
        assertThat(familleProduitDTO1).isNotEqualTo(familleProduitDTO2);
        familleProduitDTO2.setId(familleProduitDTO1.getId());
        assertThat(familleProduitDTO1).isEqualTo(familleProduitDTO2);
        familleProduitDTO2.setId(2L);
        assertThat(familleProduitDTO1).isNotEqualTo(familleProduitDTO2);
        familleProduitDTO1.setId(null);
        assertThat(familleProduitDTO1).isNotEqualTo(familleProduitDTO2);
    }
}
