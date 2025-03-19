package com.africom.finance.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class IncentiveMapperTest {

    private IncentiveMapper incentiveMapper;

    @BeforeEach
    public void setUp() {
        incentiveMapper = new IncentiveMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(incentiveMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(incentiveMapper.fromId(null)).isNull();
    }
}
