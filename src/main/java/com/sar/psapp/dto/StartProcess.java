package com.sar.psapp.dto;

import com.sar.psapp.provider.Category;
import com.sar.psapp.provider.Provider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class StartProcess {
    private Provider provider;
    private Category category;
    private Long bonusPerc;
    private Long minProductPrice;
    private Long maxProductPrice;
}
