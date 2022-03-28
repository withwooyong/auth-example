package com.example.auth.controller;

import com.example.auth.dto.NoteDTO;
import com.example.auth.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/notes/")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping(value = "")
    public ResponseEntity<Long> register(@RequestBody NoteDTO noteDTO) {
        log.debug("-------------------note register----------------------------");
        log.debug("noteDTO={}", noteDTO.toString());
        Long num = noteService.regsiter(noteDTO);
        return new ResponseEntity<>(num, HttpStatus.OK);
    }

    @GetMapping(value = "/{num}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NoteDTO> read(@PathVariable("num") Long num) {
        log.debug("-------------------note read----------------------------");
        log.debug("num={}", num);
        return new ResponseEntity<>(noteService.get(num), HttpStatus.OK);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NoteDTO>> getList(String email) {
        log.info("-------------------note getList----------------------------");
        log.info("email={}", email);
        return new ResponseEntity<>(noteService.getAllWithWriter(email), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{num}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> remove(@PathVariable("num") Long num) {
        log.debug("-------------------note remove----------------------------");
        log.debug("num={}", num);
        noteService.remove(num);
        return new ResponseEntity<>("removed", HttpStatus.OK);
    }

    @PutMapping(value = "/{num}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> modify(@RequestBody NoteDTO noteDTO) {
        log.debug("-------------------note modify----------------------------");
        log.debug(noteDTO.toString());
        noteService.modify(noteDTO);
        return new ResponseEntity<>("modified", HttpStatus.OK);
    }
}
