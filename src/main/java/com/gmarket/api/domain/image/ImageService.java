package com.gmarket.api.domain.image;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Date;

@Service
public class ImageService {

    private String imagePath;

    @PostConstruct
    public void init() throws IOException {

        String osName = System.getProperty("os.name");


        if(osName.matches(".*Windows.*")) {
            imagePath = "C://images/";
        } else {
            // INFO : chown -R 응 이용하여 해당 폴더 권한 설정 이슈 있었음
            imagePath = "/usr/images/";
        }

        File file = new File(imagePath);
        if(!file.isDirectory()) {
            Files.createDirectory(file.toPath());
        }
    }

    public String create(MultipartFile file) throws Exception {

        Date date = new Date();
        StringBuilder sb = new StringBuilder();

        // 이미지 이름과 시간을 합쳐서 중복 불가능한 이미지 리소스 주소 문자열 생성
        if(file.isEmpty()) {
            throw new IllegalStateException("이미지가 없습니다.");
        } else {
            sb.append(date.getTime());
            sb.append(file.getOriginalFilename());
        }

        File dest = new File(imagePath + sb.toString());

        try {
            file.transferTo(dest);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            throw e;
        }

        return sb.toString();
    }

    public byte[] getImage(String imageName) throws Exception {

        InputStream imageStream = new FileInputStream(imagePath + imageName);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();
        return imageByteArray;
    }
}
