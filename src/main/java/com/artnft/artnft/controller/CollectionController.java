package com.artnft.artnft.controller;

import com.artnft.artnft.entity.Collection;
import com.artnft.artnft.service.CollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/collection")
public class CollectionController {

    private final CollectionService collectionService;

    @PostMapping
    public Collection addCollection(@RequestBody Collection collection) {
        return collectionService.addCollection(collection);
    }

    @GetMapping("/{name}")
    public Collection getCollection(@PathVariable String name) {
//        char ch = '-';
//        name = name.replace(' ', ch);
//        System.out.println(name);
        return collectionService.getCollection(name);
    }

    @GetMapping("/getbyid/{marketId}")
    public Collection getCollectionById(@PathVariable Long marketId) {
        return collectionService.getCollectionById(marketId);
    }

    @GetMapping("/getbynftId/{nftId}")
    public Collection getCollectionBynftId(@PathVariable Long nftId) {
        return collectionService.getCollectionBynftId(nftId);
    }
}
