package com.africom.productinventory.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EcartStockMapperTest {

    private EcartStockMapper ecartStockMapper;

    @BeforeEach
    public void setUp() {
        ecartStockMapper = new EcartStockMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(ecartStockMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ecartStockMapper.fromId(null)).isNull();
    }
}
