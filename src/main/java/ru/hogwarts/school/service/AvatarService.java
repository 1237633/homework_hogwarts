package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repo.AvatarRepo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;



import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarService {
    private StudentService studentService;
    private AvatarRepo avatarRepo;
    private Logger logger;
    private String currMethod;
    @Value("${path.to.avatars.folder}")
    String path;

    public AvatarService(StudentService studentService, AvatarRepo avatarRepo) {
        this.studentService = studentService;
        this.avatarRepo = avatarRepo;
        this.logger = LoggerFactory.getLogger(AvatarService.class);
    }

    public void uploadAvatar(int studentId, MultipartFile avatarFile) throws IOException {
        currMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug("Executing method {}", currMethod);

        if(avatarFile == null){
            return;
        }
        Student student = studentService.getStudent(studentId);
        Path filePath = Path.of(path, student + "." + getExtension(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (InputStream is = avatarFile.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)) {
            bis.transferTo(bos);
        }
        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepo.save(avatar);
    }

    public Avatar findAvatar(int id) {
        currMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug("Executing method {}", currMethod);

        return avatarRepo.findByStudentId(id).orElseGet(Avatar::new);
    }

    private Object getExtension(String fileName) {
        currMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug("Executing method {}", currMethod);

        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public Page<Avatar> getAll(int offset, int pageSize) {
        currMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug("Executing method {}", currMethod);

        PageRequest pageRequest = PageRequest.of(offset, pageSize);
        return avatarRepo.findAll(pageRequest);
    }
}
