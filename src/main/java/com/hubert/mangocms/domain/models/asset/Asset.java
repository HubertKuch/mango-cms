package com.hubert.mangocms.domain.models.asset;

import com.hubert.mangocms.domain.models.app.Application;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.util.MimeType;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity(name = "asssets")
public class Asset {
    @Id
    private String id = UUID.randomUUID().toString();
    private String name;
    private String mime;
    @Transient
    private MimeType mimeType;
    @Transient
    @DataSizeUnit(DataUnit.BYTES)
    private DataSize size;
    private DataUnit sizeUnit;
    private Long sizeInBytes;
    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    @PostPersist
    private void postPersist() {
        this.size = DataSize.of(sizeInBytes, sizeUnit);
        this.mimeType = MimeType.valueOf(mime);
    }
}
