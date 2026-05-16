package ua.valentino.Project1.data;

import com.mpatric.mp3agic.*;
import groovy.io.FileType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import ua.valentino.Project1.entities.Genre;
import ua.valentino.Project1.entities.RoleEntity;
import ua.valentino.Project1.entities.Song;
import ua.valentino.Project1.repositories.IGenreRepository;
import ua.valentino.Project1.repositories.IRoleRepository;
import ua.valentino.Project1.repositories.ISongRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Component
@RequiredArgsConstructor
public class AppSeedData {
    @Value("${upload.dir}")
    private String uploadDir;
    // final - теж саме, що readonly у С#
    private final IGenreRepository genreRepository;
    private final ISongRepository songRepository;
    private final IRoleRepository roleRepository;
    private final Faker faker = new Faker(new Locale("uk"));

    @PostConstruct
    public void seed() {
        System.out.println("---------Run seed data-----------");
        seedRoles();
        seedGenres();
        try {
            seedSongs();
        } catch (IOException e) {
            System.out.println("Error reead files");
        }
    }

    private void seedRoles() {
        if (roleRepository.count() == 0) {
            RoleEntity userRole = new RoleEntity();
            userRole.setName("User");
            roleRepository.save(userRole);
            System.out.println("✓ Role 'User' created");
        }
    }

    private void seedGenres() {
        if (genreRepository.count() == 0) {
            List<String> genres = new ArrayList<>();
            int n = 10, i = 0;
            while (i < n) {
                String genreName = faker.music().genre();
                if (!genres.contains(genreName)) {
                    genres.add(genreName);
                    i++;
                }
            }
            for (String genreName : genres) {
                Genre genre = new Genre();
                genre.setName(genreName);
                genreRepository.save(genre);
            }
        }
    }

    private void seedSongs() throws IOException {
        if (songRepository.count() == 0) {
            var path = Paths.get(uploadDir.trim());
            if (!Files.exists(path)) {
                System.out.println("Upload directory not found: " + path);
                return;
            }

            var genres = genreRepository.findAll();
            Random random = new Random();
            Scanner scanner = new Scanner(System.in);

            Files.list(path)
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        try {
                            Mp3File mp3file = new Mp3File(file.toFile());

                            ID3v2 id3v2Tag;
                            if (mp3file.hasId3v2Tag()) {
                                id3v2Tag = mp3file.getId3v2Tag();
                            } else {
                                // === Інтерактивне меню ===
                                System.out.println("Файл без ID3v2: " + file.getFileName());
                                System.out.print("Введи артиста: ");
                                String artist = scanner.nextLine();
                                System.out.print("Введи назву треку: ");
                                String title = scanner.nextLine();
                                System.out.print("Введи альбом: ");
                                String album = scanner.nextLine();
                                System.out.print("Введи рік: ");
                                String year = scanner.nextLine();
                                System.out.print("Введи жанр: ");
                                String genreDesc = scanner.nextLine();

                                id3v2Tag = new ID3v24Tag();
                                id3v2Tag.setArtist(artist);
                                id3v2Tag.setTitle(title);
                                id3v2Tag.setAlbum(album);
                                id3v2Tag.setYear(year);
                                id3v2Tag.setGenreDescription(genreDesc);

                                mp3file.setId3v2Tag(id3v2Tag);

                                // Зберігаємо у новий файл
                                String newFileName = file.toString().replace(".mp3", "_tagged.mp3");
                                mp3file.save(newFileName);

                                // Видаляємо оригінал
                                Files.delete(file);

                                // Перепризначаємо file на новий файл
                                file = Paths.get(newFileName);
                                mp3file = new Mp3File(file.toFile());
                            }

                            // === Збереження у базу ===
                            Song song = new Song();
                            song.setArtist(id3v2Tag.getArtist());
                            String albumValue = id3v2Tag.getAlbum();
                            if (albumValue == null || albumValue.isBlank()) {
                                albumValue = "Unknown Album";
                            }
                            song.setAlbum(albumValue);

                            song.setName(id3v2Tag.getTitle());
                            song.setFileName(file.getFileName().toString());

                            Collections.shuffle(genres);
                            int count = 1 + random.nextInt(3);
                            List<Genre> randomGenres = genres.stream().limit(count).toList();
                            song.setGenres(randomGenres);

                            songRepository.save(song);

                        } catch (Exception e) {
                            System.out.println("----Problem reading file: " + e.getMessage());
                        }
                    });
                    scanner.close();
        }
    }

}