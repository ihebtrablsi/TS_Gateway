package com.africom.productinventory.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.africom.productinventory.web.rest.TestUtil;

public class EcartStockDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EcartStockDTO.class);
        EcartStockDTO ecartStockDTO1 = new EcartStockDTO();
        ecartStockDTO1.setId(1L);
        EcartStockDTO ecartStockDTO2 = new EcartStockDTO();
        assertThat(ecartStockDTO1).isNotEqualTo(ecartStockDTO2);
        ecartStockDTO2.setId(ecartStockDTO1.getId());
        assertThat(ecartStockDTO1).isEqualTo(ecartStockDTO2);
        ecartStockDTO2.setId(2L);
        assertThat(ecartStockDTO1).isNotEqualTo(ecartStockDTO2);
        ecartStockDTO1.setId(null);
        assertThat(ecartStockDTO1).isNotEqualTo(ecartStockDTO2);
    }
}
