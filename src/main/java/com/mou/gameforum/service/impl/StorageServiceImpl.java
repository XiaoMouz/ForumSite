package com.mou.gameforum.service.impl;

import com.mou.gameforum.config.StorageConfiguration;
import com.mou.gameforum.exception.StorageException;
import com.mou.gameforum.exception.StorageFileNotFoundException;
import com.mou.gameforum.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service("storageService")
public class StorageServiceImpl implements StorageService {

    private static final Logger logger = LoggerFactory.getLogger(StorageServiceImpl.class);

    private final Path rootLocation;

    @Autowired
    public StorageServiceImpl(StorageConfiguration properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(Objects.requireNonNull(file.getOriginalFilename())));
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public void storeToPath(MultipartFile file, String path) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            Path path1 = Paths.get(this.rootLocation.toString(),path);
            Files.copy(file.getInputStream(), path1.resolve(Objects.requireNonNull(file.getOriginalFilename())));
        } catch (IOException e) {
            logger.error(e.toString());
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            logger.info(file.toString());
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read file #2: " + filename);
            }
        } catch (MalformedURLException e) {
            logger.error(e.toString());
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        if(!Files.exists(rootLocation)) {
            try {
                Files.createDirectory(rootLocation);
            } catch (IOException e) {
                throw new StorageException("Could not initialize storage", e);
            }
        }
    }

    @Override
    public Resource loadAsDirectory(String directory) {
        try {
            Path file = Paths.get(directory);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read directory: " + directory);

            }
        } catch (MalformedURLException e) {
            logger.error(e.toString());
            throw new StorageFileNotFoundException("Could not read directory: " + directory, e);
        }
    }

    @Override
    public Stream<Path> loadAllDirectories() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(Files::isDirectory)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            logger.error(e.toString());
            throw new StorageException("Failed to read stored directories", e);
        }
    }

    @Override
    public Stream<Path> listDirectories(String parentDirectory, String directory) {
        try {
            if(parentDirectory==null){
                parentDirectory= String.valueOf(this.rootLocation);
            }
            Path path = Paths.get(parentDirectory,directory);
            logger.info(path.toString());
            return Files.walk(path, 1)
                    .filter(Files::isDirectory)
                    .filter(path1 -> !path1.equals(path))
                    .map(path::relativize);
        } catch (IOException e) {
            logger.error(e.toString());
            throw new StorageException("Failed to read stored files in directory: " + directory, e);
        }
    }

    @Override
    public List<Resource> listResources(String parentDirectory,String directory) {
        if(parentDirectory==null){
            parentDirectory= String.valueOf(this.rootLocation);
        }
        try {
            Path path;
            if(directory==null){
                path = Paths.get(parentDirectory);
            }else {
                path = Paths.get(parentDirectory,directory);
            }
            List<Resource> resources = new ArrayList<>();
            Files.walk(path, 1).forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        resources.add(new UrlResource(filePath.toUri()));
                    } catch (MalformedURLException e) {
                        throw new StorageException("Could not retrieve the resource", e);
                    }
                }
            });
            return resources;
        } catch (IOException e) {
            logger.error(e.toString());
            throw new StorageException("Failed to read stored files in directory: " + directory, e);
        }
    }

    @Override
    public void createDirectory(String directory) {
        try {
            Path path = Paths.get(directory);
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new StorageException("Failed to create directory: " + directory, e);
        }
    }

    @Override
    public void deleteDirectory(String directory) {
        try {
            Path path = Paths.get(directory);
            FileSystemUtils.deleteRecursively(path);
        } catch (IOException e) {
            throw new StorageException("Failed to delete directory: " + directory, e);
        }
    }

    @Override
    public void storeAvatar(MultipartFile file,Integer userid) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            Path path1 = Paths.get(this.rootLocation.toString(),"Avatar");
            // if path not exist create
            if(!Files.exists(path1)) {
                Files.createDirectory(path1);
            }
            //rename file to userid
            String filename = userid + "." + Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];
            Files.copy(file.getInputStream(), path1.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            logger.error(e.toString());
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }
}