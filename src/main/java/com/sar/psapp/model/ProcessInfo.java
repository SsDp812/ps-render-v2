package com.sar.psapp.model;


import com.sar.psapp.commons.states.ProcessState;
import com.sar.psapp.dto.generals.StartProcess;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "process_info")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class ProcessInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "state_guid")
    private String guid;

    @Enumerated(EnumType.STRING)
    @Column(name = "process_state")
    private ProcessState state;

    @Column(name = "file_guid")
    private String fileGuid;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "app_user_state")
    private AppUser user;

    @Type(type = "jsonb")
    @Column(name = "fixed_settings", columnDefinition = "jsonb")
    private StartProcess fixedSettings;
}
