package com.sar.psapp.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseDataResponse<T> {
    private Boolean success;
    private List<String> errors;
    private List<String> warnings;
    private T data;
    public void addError(String error){
        if(errors == null){
            errors = new ArrayList<>();
        }
        errors.add(error);
    }

    public void addWarning(String warning){
        if(warnings == null){
            warnings = new ArrayList<>();
        }
        warnings.add(warning);
    }

    public void addErrors(List<String> errorsList){
        if(errors == null){
            errors = new ArrayList<>();
        }
        this.errors.addAll(errorsList);
    }

    public BaseDataResponse build() {
        if (CollectionUtils.isEmpty(errors)) {
            this.success = true;
        } else {
            this.success = false;
        }
        return this;
    }
}
