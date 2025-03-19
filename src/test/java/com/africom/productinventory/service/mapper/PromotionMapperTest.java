package com.africom.productinventory.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PromotionMapperTest {

    private PromotionMapper promotionMapper;

    @BeforeEach
    public void setUp() {
        promotionMapper = new PromotionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(promotionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(promotionMapper.fromId(null)).isNull();
    }
}
