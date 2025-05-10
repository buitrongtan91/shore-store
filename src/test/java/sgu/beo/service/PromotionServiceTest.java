package sgu.beo.service;

import org.junit.jupiter.api.Test;

import sgu.beo.model.Promotion;

public class PromotionServiceTest {
    @Test
    public void testGetBestPromotion() {

        Promotion result = PromotionService.getBestPromotion(400); // 20% of 40000 = 8000

        System.out.println(result);
    }
}
