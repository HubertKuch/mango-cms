package com.hubert.mangocms.domain.models.asset;

import com.hubert.mangocms.domain.models.app.Application;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.http.MediaType;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "assets")
public class Asset {
    @Id
    private String id = UUID.randomUUID().toString();
    private String name;
    private String mime;
    @Transient
    private MediaType mediaType;
    @Transient
    @DataSizeUnit(DataUnit.BYTES)
    private DataSize size;
    private Long sizeInBytes;
    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    public Asset(
            String name, MediaType mediaType, DataSize size, Application application
    ) {
        this.name = name;
        this.mime = mediaType.getType();
        this.mediaType = mediaType;
        this.size = size;
        this.application = application;
    }

    @PostPersist
    private void postPersist() {
        this.size = DataSize.of(sizeInBytes, DataUnit.BYTES);
        this.mediaType = MediaType.valueOf(mime);
    }

}
