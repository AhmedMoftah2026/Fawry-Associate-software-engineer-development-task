package com.ahmed.move.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GeneralResponseDto {
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime time;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<?,?> data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<?,?> error;
}
