package com.sar.psapp.dto.response;


import com.sar.psapp.commons.states.ProcessState;
import com.sar.psapp.dto.generals.StartProcess;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProcessView {
    private Long id;
    private ProcessState state;
    private String fileUrl;
    private StartProcess settings;
}
