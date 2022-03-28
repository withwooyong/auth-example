package com.example.auth.service;

import com.example.auth.dto.NoteDTO;
import com.example.auth.entity.Member;
import com.example.auth.entity.Note;

import java.util.List;

public interface NoteService {

    Long regsiter(NoteDTO noteDTO);
    NoteDTO get(Long num);
    void modify(NoteDTO noteDTO);
    void remove(Long num);
    List<NoteDTO> getAllWithWriter(String writerEmail);

    default Note dtoToEntity(NoteDTO noteDTO) {
        return Note.builder()
                .num(noteDTO.getNum())
                .title(noteDTO.getTitle())
                .content(noteDTO.getContent())
                .writer(Member.builder()
                        .email(noteDTO.getWriterEmail())
                        .build())
                .build();
    }

    default NoteDTO entityToDTO(Note note) {
        return NoteDTO.builder()
                .num(note.getNum())
                .title(note.getTitle())
                .content(note.getContent())
                .writerEmail(note.getWriter()
                        .getEmail())
                .createdDate(note.getCreatedDate())
                .modifiedDate(note.getModifiedDate())
                .build();
    }
}
