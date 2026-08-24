package com.tungduong.pawnmanagement.dto.request.baseFileFilter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class FileFilterRequest {

    private String contentType;
    private String extension;
    private Long minFileSize;
    private Long maxFileSize;
}
