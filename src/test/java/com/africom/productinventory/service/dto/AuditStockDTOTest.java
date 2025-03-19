package com.africom.productinventory.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.africom.productinventory.web.rest.TestUtil;

public class AuditStockDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuditStockDTO.class);
        AuditStockDTO auditStockDTO1 = new AuditStockDTO();
        auditStockDTO1.setId(1L);
        AuditStockDTO auditStockDTO2 = new AuditStockDTO();
        assertThat(auditStockDTO1).isNotEqualTo(auditStockDTO2);
        auditStockDTO2.setId(auditStockDTO1.getId());
        assertThat(auditStockDTO1).isEqualTo(auditStockDTO2);
        auditStockDTO2.setId(2L);
        assertThat(auditStockDTO1).isNotEqualTo(auditStockDTO2);
        auditStockDTO1.setId(null);
        assertThat(auditStockDTO1).isNotEqualTo(auditStockDTO2);
    }
}
