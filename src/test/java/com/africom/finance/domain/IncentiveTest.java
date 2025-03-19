package com.africom.finance.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.africom.finance.web.rest.TestUtil;

public class IncentiveTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Incentive.class);
        Incentive incentive1 = new Incentive();
        incentive1.setId(1L);
        Incentive incentive2 = new Incentive();
        incentive2.setId(incentive1.getId());
        assertThat(incentive1).isEqualTo(incentive2);
        incentive2.setId(2L);
        assertThat(incentive1).isNotEqualTo(incentive2);
        incentive1.setId(null);
        assertThat(incentive1).isNotEqualTo(incentive2);
    }
}
