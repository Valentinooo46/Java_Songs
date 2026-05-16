package ua.valentino.Project1.mappers;

import ua.valentino.Project1.entities.*;
import ua.valentino.Project1.dtos.song.*;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SongMapper {

    SongItemDTO toDto(Song song);
}