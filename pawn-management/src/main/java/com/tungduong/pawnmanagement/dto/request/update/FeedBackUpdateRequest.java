package com.tungduong.pawnmanagement.dto.request.update;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedBackUpdateRequest {
    private Long id;

    private String title;

    private String content;

    private MultipartFile file;
}
