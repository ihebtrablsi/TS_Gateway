package com.africom.finance.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.africom.finance.web.rest.TestUtil;

public class IncentiveDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncentiveDTO.class);
        IncentiveDTO incentiveDTO1 = new IncentiveDTO();
        incentiveDTO1.setId(1L);
        IncentiveDTO incentiveDTO2 = new IncentiveDTO();
        assertThat(incentiveDTO1).isNotEqualTo(incentiveDTO2);
        incentiveDTO2.setId(incentiveDTO1.getId());
        assertThat(incentiveDTO1).isEqualTo(incentiveDTO2);
        incentiveDTO2.setId(2L);
        assertThat(incentiveDTO1).isNotEqualTo(incentiveDTO2);
        incentiveDTO1.setId(null);
        assertThat(incentiveDTO1).isNotEqualTo(incentiveDTO2);
    }
}
