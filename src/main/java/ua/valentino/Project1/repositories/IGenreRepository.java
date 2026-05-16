package ua.valentino.Project1.repositories;

import ua.valentino.Project1.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository public interface IGenreRepository extends JpaRepository<Genre, Long>{}
