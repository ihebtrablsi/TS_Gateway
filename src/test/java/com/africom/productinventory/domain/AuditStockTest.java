package com.africom.productinventory.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.africom.productinventory.web.rest.TestUtil;

public class AuditStockTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuditStock.class);
        AuditStock auditStock1 = new AuditStock();
        auditStock1.setId(1L);
        AuditStock auditStock2 = new AuditStock();
        auditStock2.setId(auditStock1.getId());
        assertThat(auditStock1).isEqualTo(auditStock2);
        auditStock2.setId(2L);
        assertThat(auditStock1).isNotEqualTo(auditStock2);
        auditStock1.setId(null);
        assertThat(auditStock1).isNotEqualTo(auditStock2);
    }
}
