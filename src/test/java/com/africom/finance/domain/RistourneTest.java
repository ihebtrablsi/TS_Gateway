package com.africom.finance.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.africom.finance.web.rest.TestUtil;

public class RistourneTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ristourne.class);
        Ristourne ristourne1 = new Ristourne();
        ristourne1.setId(1L);
        Ristourne ristourne2 = new Ristourne();
        ristourne2.setId(ristourne1.getId());
        assertThat(ristourne1).isEqualTo(ristourne2);
        ristourne2.setId(2L);
        assertThat(ristourne1).isNotEqualTo(ristourne2);
        ristourne1.setId(null);
        assertThat(ristourne1).isNotEqualTo(ristourne2);
    }
}
