package com.africom.finance.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.africom.finance.web.rest.TestUtil;

public class LettrageDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LettrageDTO.class);
        LettrageDTO lettrageDTO1 = new LettrageDTO();
        lettrageDTO1.setId(1L);
        LettrageDTO lettrageDTO2 = new LettrageDTO();
        assertThat(lettrageDTO1).isNotEqualTo(lettrageDTO2);
        lettrageDTO2.setId(lettrageDTO1.getId());
        assertThat(lettrageDTO1).isEqualTo(lettrageDTO2);
        lettrageDTO2.setId(2L);
        assertThat(lettrageDTO1).isNotEqualTo(lettrageDTO2);
        lettrageDTO1.setId(null);
        assertThat(lettrageDTO1).isNotEqualTo(lettrageDTO2);
    }
}
