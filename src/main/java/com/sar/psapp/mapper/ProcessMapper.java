package com.sar.psapp.mapper;

import com.sar.psapp.dto.response.ProcessView;
import com.sar.psapp.model.ProcessInfo;

public interface ProcessMapper {
    public ProcessView mapToView(ProcessInfo process);
}
