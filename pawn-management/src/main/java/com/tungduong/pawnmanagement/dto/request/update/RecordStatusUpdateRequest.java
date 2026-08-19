package com.tungduong.pawnmanagement.dto.request.update;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.tungduong.pawnmanagement.model.enums.RecordStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecordStatusUpdateRequest {

    @NotNull(message = "Record status cannot be null")
    @JsonAlias("status")
    private RecordStatus recordStatus;
}
