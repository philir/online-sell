package com.ocean.sell.service.imp;

import com.ocean.sell.dataobject.ProductInfo;
import com.ocean.sell.dto.CarDTO;
import com.ocean.sell.enums.ProductStatusEnum;
import com.ocean.sell.enums.ResultEnum;
import com.ocean.sell.exception.SellException;
import com.ocean.sell.repository.ProductInfoRepository;
import com.ocean.sell.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品
 */
@Service
public class ProductInfoServiceImp implements ProductInfoService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {
        return repository.findById(productId).orElse(null);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CarDTO> carDTOList) {
        for(CarDTO carDTO : carDTOList){
            ProductInfo productInfo = repository.findById(carDTO.getProductId()).orElse(null);
            if(productInfo == null){
                new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + carDTO.getProductQuantity();
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CarDTO> carDTOList) {
        for(CarDTO carDTO : carDTOList){
            ProductInfo productInfo = repository.findById(carDTO.getProductId()).orElse(null);
            if(productInfo == null){
                new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() - carDTO.getProductQuantity();
            if(result<0){
                new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = repository.findById(productId).orElse(null);
        if(productInfo == null){
            throw  new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatus() == ProductStatusEnum.UP.getCode()){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return repository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = repository.findById(productId).orElse(null);
        if(productInfo == null){
            throw  new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatus() == ProductStatusEnum.DOWN.getCode()){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return repository.save(productInfo);
    }
}
