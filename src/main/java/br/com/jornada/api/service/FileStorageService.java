package br.com.jornada.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.jornada.api.property.FileStorageProperties;

@Service

public class FileStorageService {

  private final Path fileStorageLocation;

  public FileStorageService(FileStorageProperties fileStorageProperties) {

    this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

    try {

      Files.createDirectories(this.fileStorageLocation);

    } catch (Exception ex) {

      throw new RuntimeException("Couldn't create the directory where the upload files will be saved.", ex);

    }

  }

  public String storeFile(MultipartFile file, String newName) {

    String extension = getFileExtension(file.getOriginalFilename());

    String fileName = StringUtils
        .cleanPath(
            newName.concat(".").concat(extension));

    try {

      if (fileName.contains("..")) {

        throw new RuntimeException("Sorry! File name which contains invalid path sequence " + fileName);

      }

      Path targetLocation = this.fileStorageLocation.resolve(fileName);

      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

      return fileName;

    } catch (IOException ex) {

      ex.printStackTrace();

    }
    return "";

  }

  static String getFileExtension(String filename) {
    if (filename.contains("."))
      return filename.substring(filename.lastIndexOf(".") + 1);
    else
      return "";
  }
}
