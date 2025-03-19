package com.africom.clientsales.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ActionCommercialeMapperTest {

    private ActionCommercialeMapper actionCommercialeMapper;

    @BeforeEach
    public void setUp() {
        actionCommercialeMapper = new ActionCommercialeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(actionCommercialeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(actionCommercialeMapper.fromId(null)).isNull();
    }
}
