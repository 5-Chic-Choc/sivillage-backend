package com.chicchoc.sivillage.domain.product.application;

import com.chicchoc.sivillage.domain.product.dto.in.ProductRequestDto;
import com.chicchoc.sivillage.domain.product.vo.out.ProductResponseVo;

import java.util.List;

public interface ProductService {

    List<ProductResponseVo> getFilteredProducts(ProductRequestDto productRequestDto);

}
