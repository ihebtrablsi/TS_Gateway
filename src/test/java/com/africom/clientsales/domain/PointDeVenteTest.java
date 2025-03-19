package com.africom.clientsales.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.africom.clientsales.web.rest.TestUtil;

public class PointDeVenteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PointDeVente.class);
        PointDeVente pointDeVente1 = new PointDeVente();
        pointDeVente1.setId(1L);
        PointDeVente pointDeVente2 = new PointDeVente();
        pointDeVente2.setId(pointDeVente1.getId());
        assertThat(pointDeVente1).isEqualTo(pointDeVente2);
        pointDeVente2.setId(2L);
        assertThat(pointDeVente1).isNotEqualTo(pointDeVente2);
        pointDeVente1.setId(null);
        assertThat(pointDeVente1).isNotEqualTo(pointDeVente2);
    }
}
