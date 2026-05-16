package ua.valentino.Project1.services;

import org.springframework.stereotype.Service;
import ua.valentino.Project1.mappers.*;
import ua.valentino.Project1.dtos.song.*;
import lombok.RequiredArgsConstructor;
import ua.valentino.Project1.repositories.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongMapper mapper;
    private final ISongRepository songRepository;

    public List<SongItemDTO> getAllSongs() {
        return songRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}