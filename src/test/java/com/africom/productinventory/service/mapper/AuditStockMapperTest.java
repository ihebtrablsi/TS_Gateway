package com.africom.productinventory.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AuditStockMapperTest {

    private AuditStockMapper auditStockMapper;

    @BeforeEach
    public void setUp() {
        auditStockMapper = new AuditStockMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(auditStockMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(auditStockMapper.fromId(null)).isNull();
    }
}
