package pe.celucheck.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.celucheck.domain.*;
import pe.celucheck.dto.AdminPhoneForm;
import pe.celucheck.repository.*;
import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.*;

@Service @RequiredArgsConstructor
public class AdminService {
    private final PhoneRepository phones; private final BrandRepository brands; private final StoreRepository stores;
    private final OfferRepository offers; private final AppUserRepository users;

    @Transactional
    public void savePhone(AdminPhoneForm form) {
        Phone phone = form.getId() == null ? new Phone() : phones.findDetailedById(form.getId()).orElseThrow();
        phone.setBrand(brands.findById(form.getBrandId()).orElseThrow()); phone.setName(form.getName().trim());
        phone.setSlug(slug(form.getName()) + (form.getId() == null ? "-" + System.currentTimeMillis()%100000 : ""));
        phone.setModel(form.getModel()); phone.setStorageGb(form.getStorageGb()); phone.setRamGb(form.getRamGb());
        phone.setScreenInches(form.getScreenInches()); phone.setCameraMp(form.getCameraMp()); phone.setBatteryMah(form.getBatteryMah());
        phone.setProcessor(form.getProcessor()); phone.setImageUrl(form.getImageUrl()); phone.setDescription(form.getDescription());
        phone.setFeatured(form.isFeatured()); phone.setActive(true); phone = phones.save(phone);
        Map<String,BigDecimal> prices = Map.of("falabella", nz(form.getFalabellaPrice()), "ripley", nz(form.getRipleyPrice()), "oechsle", nz(form.getOechslePrice()));
        for (Store store : stores.findAll()) if (prices.getOrDefault(store.getSlug(), BigDecimal.ZERO).signum() > 0) {
            Phone saved = phone;
            Offer offer = phone.getOffers().stream().filter(o -> o.getStore().getId().equals(store.getId())).findFirst().orElseGet(() -> { Offer o=new Offer(); o.setPhone(saved); o.setStore(store); o.setProductUrl("https://"+store.getSlug()+".pe/"); return o; });
            offer.setPrice(prices.get(store.getSlug())); offer.setPreviousPrice(prices.get(store.getSlug()).multiply(BigDecimal.valueOf(1.12))); offer.setInStock(true); offers.save(offer);
        }
    }
    private BigDecimal nz(BigDecimal v){ return v == null ? BigDecimal.ZERO : v; }
    private String slug(String s){ return Normalizer.normalize(s,Normalizer.Form.NFD).replaceAll("\\p{M}","").toLowerCase().replaceAll("[^a-z0-9]+","-").replaceAll("(^-|-$)",""); }
    @Transactional public void deletePhone(Long id){ Phone p=phones.findById(id).orElseThrow(); p.setActive(false); phones.save(p); }
    @Transactional public void toggleUser(Long id){ AppUser u=users.findById(id).orElseThrow(); if(u.getRole()!=Role.ADMIN){u.setEnabled(!u.isEnabled()); users.save(u);} }
    @Transactional public void deleteUser(Long id){ AppUser u=users.findById(id).orElseThrow(); if(u.getRole()!=Role.ADMIN) users.delete(u); }
}
