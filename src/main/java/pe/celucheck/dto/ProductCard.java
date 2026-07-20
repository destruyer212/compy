package pe.celucheck.dto;

import pe.celucheck.domain.Phone;
import java.math.BigDecimal;

public record ProductCard(Long id, String slug, String brand, String name, String imageUrl,
                          BigDecimal bestPrice, BigDecimal previousPrice, int savingsPercent,
                          int storeCount, String bestStore, String processor, int batteryMah,
                          int ramGb, int cameraMp, BigDecimal screenInches) {
    public static ProductCard from(Phone phone) {
        var offers = phone.getOffers().stream().filter(o -> o.isInStock()).toList();
        var best = offers.stream().min(java.util.Comparator.comparing(o -> o.getPrice())).orElse(null);
        BigDecimal price = best == null ? BigDecimal.ZERO : best.getPrice();
        BigDecimal previous = best == null ? null : best.getPreviousPrice();
        int saving = 0;
        if (previous != null && previous.signum() > 0) {
            saving = previous.subtract(price).multiply(BigDecimal.valueOf(100))
                    .divide(previous, 0, java.math.RoundingMode.HALF_UP).intValue();
        }
        return new ProductCard(phone.getId(), phone.getSlug(), phone.getBrand().getName(), phone.getName(),
                phone.getImageUrl(), price, previous, saving, offers.size(),
                best == null ? "" : best.getStore().getName(), phone.getProcessor(), phone.getBatteryMah(),
                phone.getRamGb(), phone.getCameraMp(), phone.getScreenInches());
    }
}
