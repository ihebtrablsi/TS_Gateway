package com.africom.clientsales.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.africom.clientsales.web.rest.TestUtil;

public class ActionCommercialeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActionCommerciale.class);
        ActionCommerciale actionCommerciale1 = new ActionCommerciale();
        actionCommerciale1.setId(1L);
        ActionCommerciale actionCommerciale2 = new ActionCommerciale();
        actionCommerciale2.setId(actionCommerciale1.getId());
        assertThat(actionCommerciale1).isEqualTo(actionCommerciale2);
        actionCommerciale2.setId(2L);
        assertThat(actionCommerciale1).isNotEqualTo(actionCommerciale2);
        actionCommerciale1.setId(null);
        assertThat(actionCommerciale1).isNotEqualTo(actionCommerciale2);
    }
}
