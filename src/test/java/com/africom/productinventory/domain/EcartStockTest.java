package com.africom.productinventory.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.africom.productinventory.web.rest.TestUtil;

public class EcartStockTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EcartStock.class);
        EcartStock ecartStock1 = new EcartStock();
        ecartStock1.setId(1L);
        EcartStock ecartStock2 = new EcartStock();
        ecartStock2.setId(ecartStock1.getId());
        assertThat(ecartStock1).isEqualTo(ecartStock2);
        ecartStock2.setId(2L);
        assertThat(ecartStock1).isNotEqualTo(ecartStock2);
        ecartStock1.setId(null);
        assertThat(ecartStock1).isNotEqualTo(ecartStock2);
    }
}
