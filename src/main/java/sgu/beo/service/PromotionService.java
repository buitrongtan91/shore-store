package sgu.beo.service;

import java.time.LocalDateTime;
import java.util.List;

import sgu.beo.DAO.PromotionDAO;
import sgu.beo.model.Promotion;

public class PromotionService {

    public static PromotionDAO promotionDAO = PromotionDAO.getInstance();

    public static Promotion getBestPromotion(long total) {
        List<Promotion> promotions = promotionDAO.findAll();
        LocalDateTime now = LocalDateTime.now();
        Promotion bestPromotion = null;
        long maxDiscountValue = 0;

        for (Promotion promo : promotions) {
            if (!promo.is_active() || promo.is_deleted())
                continue;

            // Kiểm tra điều kiện áp dụng
            if (total >= promo.getMin_invoice_value()
                    && now.isAfter(promo.getStart_date())
                    && now.isBefore(promo.getEnd_date())) {

                long discount = 0;
                if ("flat".equalsIgnoreCase(promo.getPromotion_type())) {
                    discount = promo.getPromotion_value();
                } else if ("percent".equalsIgnoreCase(promo.getPromotion_type())) {
                    discount = promo.getPromotion_value() * total / 100;
                    if (discount > promo.getMax_discount_value()) {
                        discount = promo.getMax_discount_value();
                    }
                }

                if (discount > maxDiscountValue) {
                    maxDiscountValue = discount;
                    bestPromotion = promo;
                }
            }
        }

        return bestPromotion;
    }
}
