package com.africom.finance.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.africom.finance.web.rest.TestUtil;

public class RistourneDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RistourneDTO.class);
        RistourneDTO ristourneDTO1 = new RistourneDTO();
        ristourneDTO1.setId(1L);
        RistourneDTO ristourneDTO2 = new RistourneDTO();
        assertThat(ristourneDTO1).isNotEqualTo(ristourneDTO2);
        ristourneDTO2.setId(ristourneDTO1.getId());
        assertThat(ristourneDTO1).isEqualTo(ristourneDTO2);
        ristourneDTO2.setId(2L);
        assertThat(ristourneDTO1).isNotEqualTo(ristourneDTO2);
        ristourneDTO1.setId(null);
        assertThat(ristourneDTO1).isNotEqualTo(ristourneDTO2);
    }
}
