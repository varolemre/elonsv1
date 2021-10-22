package com.artnft.artnft.service;

import com.artnft.artnft.entity.Changed;
import com.artnft.artnft.entity.Market;
import com.artnft.artnft.repository.ChangedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;


@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class Scheduled {


    private final MarketService marketService;
    private final ChangedRepository changedRepository;

    private String qtype = "Rare";
    private Long prevPrice = 100L;
    private String nftName = "SERA";
    private Long l2;

    String[] nameArray = {"SERA", "Darvin", "LOST"};
    String[] variantArray = {"Common", "Uncommon", "Rare", "Secret Rare"};

    @org.springframework.scheduling.annotation.Scheduled(fixedRate = 900000L)
    void findChangedValue() {
        for (int i = 0; i < nameArray.length; i++) {
            for (int k = 0; k < variantArray.length; k++) {
                List<Market> byNftNameAndVariantList = marketService.findByNftNameAndVariant(nameArray[i], variantArray[k]);
                if (!byNftNameAndVariantList.isEmpty()) {
                    Changed byNftNameAndVariant = changedRepository.findByNftNameAndVariant(nameArray[i], variantArray[k]);
                    Long oldPrice = byNftNameAndVariant.getOldPrice();
                    Long newPrice = marketService.findFloorPriceByNameAndVariant(nameArray[i], variantArray[k]);
                    if (oldPrice == 0) {
                        oldPrice = newPrice;
                    } else {
                        if (oldPrice <= newPrice) {
                            //Önceki floor price ile şimdiki arasındaki yüzde değişimini bul
                            long l = newPrice - oldPrice;
                            long l1 = oldPrice / 100;
                            l2 = l / l1;
                            byNftNameAndVariant.setChangedValue(l2);
                            byNftNameAndVariant.setOldPrice(newPrice);
                            changedRepository.save(byNftNameAndVariant);
                        } else {
                            long l = oldPrice - newPrice;
                            long l1 = oldPrice / 100;
                            l2 = l / l1;
                            byNftNameAndVariant.setChangedValue(l2);
                            byNftNameAndVariant.setOldPrice(newPrice);
                            changedRepository.save(byNftNameAndVariant);
                        }
                    }
                }
            }
        }


    }


//    @org.springframework.scheduling.annotation.Scheduled(fixedRate = 19000L)
//    Integer findChangedValue(){
//        Changed changed = new Changed();
//        for(int i=0; i<array.length;i++){
//            System.out.println("Döngü sonrası if öncesi");
//            if(marketService.findFloorPriceByNameAndVariant(array[i],"Rare")!=null) {
//                System.out.println(array[i]+" floor price "+marketService.findFloorPriceByNameAndVariant(array[i],"Rare"));
//                Changed nft = changedRepository.findByNftNameAndVariant(array[i], "Rare");
//                Long nftFloor = marketService.findFloorPriceByNameAndVariant(array[i], "Rare");
//                System.out.println(array[i]+"OLD PRICE"+ nft.getOldPrice());
//                if (nft.getOldPrice() == 0) {
//                    System.out.println("OLD PRICE "+nft.getOldPrice());
//                    nft.setOldPrice(nftFloor);
//                    nft.setChangedValue(0L);
//                    changedRepository.save(nft);
//                } else {
//                    System.out.println("ELSENİN içi");
//                    if (nft.getOldPrice() < nftFloor) {
//                        long l = nftFloor - nft.getOldPrice();
//                        long l1 = nft.getOldPrice() / 100;
//                        l2 = l / l1;
//                        System.out.println(l2);
//                        nft.setChangedValue(l2);
//                        nft.setOldPrice(nftFloor);
//                        changedRepository.save(nft);
//                    } else {
//                            long l = nft.getOldPrice() - nftFloor;
//                            long l1 = nft.getOldPrice() / 100;
//                            l2 = l / l1;
//                            System.out.println(l2);
//                            nft.setChangedValue(l2);
//                            nft.setOldPrice(nftFloor);
//                            changedRepository.save(nft);
//
//                    }
//                }
//            }else
//                return 1;
//        }return 1;
//    }


//    @org.springframework.scheduling.annotation.Scheduled(fixedRate = 9000L)
//    Integer doIt(){
//        Changed changed = new Changed();
//        Long oldPrice = prevPrice;
//        for (int i=0; i<array.length; i++){
//            Long newPrice = marketService.findFloorPriceByNameAndVariant(array[i], qtype);
//
//        changed.setNftName(array[i]);
//        changed.setVariant(qtype);
//        if(oldPrice<newPrice){
//            long l = newPrice - oldPrice;
//            long l1 = oldPrice / 100;
//             l2 = l / l1;
//            System.out.println(l2);
//            prevPrice=newPrice;
//            Changed changedName = getChangedName(array[i], qtype);
//            changedName.setChanged(l2);
//            System.out.println(changedName);
//            changedRepository.save(changedName);
//        }else{
//            long l = oldPrice - newPrice;
//            long l1 = oldPrice / 100;
//             l2 = l / l1;
//            System.out.println(l2);
//            prevPrice=newPrice;
//            Changed changedName = getChangedName(array[i], qtype);
//            changedName.setChanged(l2);
//            System.out.println(changedName);
//            changedRepository.save(changedName);
//        }}
//        return l2.intValue();
//    }

    public Changed getChangedName(String nftName, String variant) {
        return changedRepository.findByNftNameAndVariant(nftName, variant);
    }


    public Long getL2() {
        return l2;
    }

    public void setL2(Long l2) {
        this.l2 = l2;
    }
}
