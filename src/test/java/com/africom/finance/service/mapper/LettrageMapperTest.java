package com.africom.finance.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LettrageMapperTest {

    private LettrageMapper lettrageMapper;

    @BeforeEach
    public void setUp() {
        lettrageMapper = new LettrageMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(lettrageMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(lettrageMapper.fromId(null)).isNull();
    }
}
