package com._DSF.je.Service;

import com._DSF.je.Entity.CarteItem;
import com._DSF.je.Repository.CarteItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarteItemService {

    @Autowired
    private CarteItemRepository carteItemRepository;

    public List<CarteItem> getCarteItemsByOrderId(Long orderId) {
        return carteItemRepository.findByOrderId(orderId);
    }

    public CarteItem getCarteItemById(Long carteItemId) {
        return carteItemRepository.findById(carteItemId).orElseThrow(() -> new RuntimeException("Carte Item not found"));
    }

    public void deleteCarteItem(Long carteItemId) {
        CarteItem carteItem = carteItemRepository.findById(carteItemId).orElseThrow(() -> new RuntimeException("Carte Item not found"));
        carteItemRepository.delete(carteItem);
    }
}
