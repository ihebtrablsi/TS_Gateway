package com.africom.finance.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RistourneMapperTest {

    private RistourneMapper ristourneMapper;

    @BeforeEach
    public void setUp() {
        ristourneMapper = new RistourneMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(ristourneMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ristourneMapper.fromId(null)).isNull();
    }
}
