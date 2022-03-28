package com.example.auth.dto;


import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NoteDTO {

    private Long num;

    private String title;

    private String content;

    private String writerEmail; // 연관 관계 없이

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
