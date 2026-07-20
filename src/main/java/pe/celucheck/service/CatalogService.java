package pe.celucheck.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.celucheck.domain.Phone;
import pe.celucheck.dto.ProductCard;
import pe.celucheck.repository.PhoneRepository;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;

@Service @RequiredArgsConstructor
public class CatalogService {
    private final PhoneRepository phones;
    @Transactional(readOnly=true)
    public List<ProductCard> search(String q, String brand, BigDecimal maxPrice) {
        return phones.search(q == null ? "" : q.trim(), brand == null ? "" : brand, maxPrice)
                .stream().map(ProductCard::from).toList();
    }
    @Transactional(readOnly=true)
    public List<ProductCard> search(String q, String brand, BigDecimal maxPrice, String profile) {
        var result = search(q, brand, maxPrice);
        if (profile == null || profile.isBlank()) return result;
        return result.stream().filter(p -> switch (profile) {
            case "camera" -> p.cameraMp() >= 100;
            case "gaming" -> p.ramGb() >= 12;
            case "battery" -> p.batteryMah() >= 5200;
            case "value" -> p.bestPrice().compareTo(BigDecimal.valueOf(2600)) <= 0;
            case "compact" -> p.screenInches().compareTo(BigDecimal.valueOf(6.4)) <= 0;
            default -> true;
        }).toList();
    }
    @Transactional(readOnly=true)
    public Phone detail(String slug) { return phones.findBySlugAndActiveTrue(slug).orElseThrow(); }
    @Transactional(readOnly=true)
    public Phone byId(Long id) { return phones.findDetailedById(id).orElseThrow(); }
    @Transactional(readOnly=true)
    public List<Phone> compare(List<Long> ids) {
        return new LinkedHashSet<>(ids).stream().limit(3)
                .map(phones::findDetailedById).flatMap(java.util.Optional::stream).toList();
    }
}
