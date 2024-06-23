package com.sar.psapp.dto.generals;

import com.sar.psapp.commons.goods.Category;
import com.sar.psapp.commons.goods.Provider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class StartProcess {
    private Provider provider;
    private Category category;
    private int pageLimit;
    private Long bonusPerc;
    private Long minProductPrice;
    private Long maxProductPrice;
}
