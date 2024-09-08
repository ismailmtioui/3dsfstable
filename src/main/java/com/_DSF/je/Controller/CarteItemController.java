package com._DSF.je.Controller;

import com._DSF.je.Entity.CarteItem;
import com._DSF.je.Service.CarteItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carteItems")
public class CarteItemController {

    @Autowired
    private CarteItemService carteItemService;

    // Get all items in an order
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<CarteItem>> getCarteItemsByOrderId(@PathVariable Long orderId) {
        List<CarteItem> carteItems = carteItemService.getCarteItemsByOrderId(orderId);
        return ResponseEntity.ok(carteItems);
    }

    // Get a specific carte item by ID
    @GetMapping("/{carteItemId}")
    public ResponseEntity<CarteItem> getCarteItemById(@PathVariable Long carteItemId) {
        CarteItem carteItem = carteItemService.getCarteItemById(carteItemId);
        return ResponseEntity.ok(carteItem);
    }

    // Delete a specific carte item by ID
    @DeleteMapping("/{carteItemId}")
    public ResponseEntity<Void> deleteCarteItem(@PathVariable Long carteItemId) {
        carteItemService.deleteCarteItem(carteItemId);
        return ResponseEntity.noContent().build();
    }
}
