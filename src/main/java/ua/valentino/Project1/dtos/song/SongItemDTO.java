package ua.valentino.Project1.dtos.song;

import lombok.Data;

@Data
public class SongItemDTO {
    private Long id;
    private String name;
    private String fileName;
    private String artist;
    private String album;
}