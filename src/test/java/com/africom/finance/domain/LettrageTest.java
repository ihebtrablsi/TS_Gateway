package com.africom.finance.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.africom.finance.web.rest.TestUtil;

public class LettrageTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lettrage.class);
        Lettrage lettrage1 = new Lettrage();
        lettrage1.setId(1L);
        Lettrage lettrage2 = new Lettrage();
        lettrage2.setId(lettrage1.getId());
        assertThat(lettrage1).isEqualTo(lettrage2);
        lettrage2.setId(2L);
        assertThat(lettrage1).isNotEqualTo(lettrage2);
        lettrage1.setId(null);
        assertThat(lettrage1).isNotEqualTo(lettrage2);
    }
}
