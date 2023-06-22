package com.sof_eng.Controller.FileHandler;

import com.sof_eng.Mapper.ExperimentMapper;
import com.sof_eng.Mapper.FileMapper;
import com.sof_eng.Mapper.UserMapper;
import com.sof_eng.Util.JwtTokenUtil;
import com.sof_eng.model.CommonResult;
import com.sof_eng.model.DTO.Experiment;
import com.sof_eng.model.DTO.otreeFile;
import com.sof_eng.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RestController
@RequestMapping("/api")
public class FileUploadController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private ExperimentMapper experimentMapper;

    @Value("${file.upload-path}")
    private String uploadPath;

    @Value("${file.allowed-types}")
    private String allowedTypes;

    @Value("${file.max-size}")
    private long maxFileSize;

    @PostMapping("/upload")
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file,@RequestHeader("Authorization") String token) throws IOException {
        if(!jwtTokenUtil.validateToken(token))
            return "invalid token";
        String username= jwtTokenUtil.getUsernameFromToken(token);
        //System.out.println(username);
        if (file == null || file.isEmpty()) {
            return "upload file can not be empty";
        }

        if (!StringUtils.hasText(uploadPath)) {
            return "filepath not configured";
        }

        if (!isFileTypeAllowed(file)) {
            return "file type not allowed";
        }

        /*if (!isFileContentValid(file)) {
            return "文件内容不合法";
        }*/

        if (file.getSize() > maxFileSize) {
            return "file size exceed limit:";
        }

        try {
            uploadPath=System.getProperty("user.dir");
            String fileName = file.getOriginalFilename();
            User user=userMapper.getUserByName(username);
            if(fileName.endsWith(".py")){
                otreeFile otreeFile=new otreeFile();
                uploadPath=uploadPath+File.separator+username+File.separator+"PyPath";
                otreeFile.setFiletype("python");
                otreeFile.setFounder(user.getUsername());
                String uniqueFileName=generateUniqueFileName(fileName);
                otreeFile.setDirectory(uploadPath+File.separator+uniqueFileName);
                otreeFile.setTitle(fileName);
                otreeFile.setFounder_id(user.getId());
                fileMapper.insertFileRec(otreeFile);
                //System.out.println(uploadPath);
                String filePath = uploadPath + File.separator + uniqueFileName;
                System.out.println(filePath);
                File dir=new File(uploadPath);
                File dest = new File(filePath);
                file.transferTo(dest);
            }
            else {
                Experiment experiment=new Experiment();
                experiment.setFounder(user.getUsername());
                experiment.setFounderId(user.getId());
                String uniqueFileName=generateUniqueFileName(fileName);
                uploadPath = uploadPath + File.separator + username + File.separator + "ExPath";
                String filePath = uploadPath + File.separator + fileName;
                File dir=new File(uploadPath);
                File dest = new File(filePath);
                file.transferTo(dest);
                System.out.println(uploadPath+"     "+fileName);
                unzipotree(uploadPath,fileName,uniqueFileName);
                experiment.setDirectory(uploadPath+File.separator+uniqueFileName.replaceFirst(".otreezip",""));
                experiment.setTitle(fileName.replaceFirst(".otreezip",""));
                experiment.setUrl("N/A");
                experimentMapper.addExperiment(experiment);
            }

            // 将文件名保存到数据库中
            return "file upload successfully";
        } catch (IOException e) {
            throw new IOException("unable to upload file");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private void unzipotree(String uploadPath,String fileName, String uniqueFileName) throws IOException, InterruptedException {
        ProcessBuilder processBuilder=new ProcessBuilder();
        Map<String, String> env = processBuilder.environment();
        String pathValue = env.get("PATH");
        String newPathValue = pathValue + ":/home/ubuntu/.local/bin";
        env.put("PATH", newPathValue);
        String path="cd "+uploadPath+" && "+"otree unzip "+fileName+" && rm -f "+fileName+" && mv "+fileName.replaceFirst(".otreezip","")+"/ "+uniqueFileName.replaceFirst(".otreezip","");
        System.out.println(path);
        processBuilder.command("bash", "-c",path);
        Process process = processBuilder.start();
        process.waitFor();
        return;
    }

    private boolean isFileTypeAllowed(MultipartFile file) {
        String fileExtension = getFileExtension(file.getOriginalFilename());
        if (fileExtension != null) {
            List<String> allowedExtensions = Arrays.asList(allowedTypes.split(","));
            return allowedExtensions.contains(fileExtension.toLowerCase());
        }
        return false;
    }

    /*private boolean isFileContentValid(MultipartFile file) throws IOException {
        // 验证文件类型是否为图像类型
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return false;
        }

        // 进一步验证文件内容，例如使用文件头、魔术数字等方式

        // 示例：验证文件内容为图片（JPEG 或 PNG）
        byte[] fileBytes = file.getBytes();

        // JPEG 文件的文件头：FF D8 FF E0
        // PNG 文件的文件头：89 50 4E 47 0D 0A 1A 0A
        if (fileBytes.length >= 4) {
            byte[] jpegHeader = {(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE0};
            byte[] pngHeader = {(byte)0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A};

            return startsWith(fileBytes, jpegHeader) || startsWith(fileBytes, pngHeader);
        }

        return false;
    }*/

    private boolean startsWith(byte[] array, byte[] prefix) {
        if (array.length < prefix.length) {
            return false;
        }
        for (int i = 0; i < prefix.length; i++) {
            if (array[i] != prefix[i]) {
                return false;
            }
        }
        return true;
    }

    private String generateUniqueFileName(String originalFileName) {
        String fileExtension = getFileExtension(originalFileName);
        String uniqueFileName = UUID.randomUUID().toString();
        if (fileExtension != null) {
            uniqueFileName += "." + fileExtension;
        }
        return uniqueFileName;
    }

    private String getFileExtension(String fileName) {
        if (StringUtils.hasText(fileName)) {
            int dotIndex = fileName.lastIndexOf(".");
            if (dotIndex >= 0 && dotIndex < fileName.length() - 1) {
                return fileName.substring(dotIndex + 1);
            }
        }
        return null;
    }
}


