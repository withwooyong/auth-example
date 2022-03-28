package com.example.auth.service;

import com.example.auth.dto.NoteDTO;
import com.example.auth.entity.Note;
import com.example.auth.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    public Long regsiter(NoteDTO noteDTO) {
        Note note = dtoToEntity(noteDTO);
        log.debug("=====================================");
        log.debug("Note Register : " + note);
        noteRepository.save(note);
        return note.getNum();
    }

    @Override
    public NoteDTO get(Long num) {
        log.debug("num={}", num);
        Optional<Note> result = noteRepository.getWithWriter(num);
        if (result.isPresent())
            return entityToDTO(result.get());
        return null;
    }

    @Override
    public void modify(NoteDTO noteDTO) {
        log.debug("noteDTO={}", noteDTO.toString());
        Long num = noteDTO.getNum();
        Optional<Note> result = noteRepository.findById(num);
        if (result.isPresent()) {
            Note note = result.get();
            note.changeTitle(noteDTO.getTitle());
            note.changeContent(noteDTO.getContent());
            noteRepository.save(note);
        }
    }

    @Override
    public void remove(Long num) {
        log.debug("num={}", num);
        noteRepository.deleteById(num);
    }

    @Override
    public List<NoteDTO> getAllWithWriter(String writerEmail) {
        log.debug("writerEmail={}", writerEmail);
        List<Note> noteList = noteRepository.getList(writerEmail);
        return noteList.stream().map(note -> entityToDTO(note)).collect(Collectors.toList());
    }
}
