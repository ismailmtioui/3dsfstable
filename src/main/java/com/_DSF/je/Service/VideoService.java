
package com._DSF.je.Service;

import com._DSF.je.Entity.Course;
import com._DSF.je.Entity.Video;
import com._DSF.je.Repository.CourseRepository;
import com._DSF.je.Repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final CourseRepository courseRepository;
    private final String uploadDirectory = "C:/videos/";

    @Autowired
    public VideoService(VideoRepository videoRepository, CourseRepository courseRepository) {
        this.videoRepository = videoRepository;
        this.courseRepository = courseRepository;
    }

    public Video saveVideo(Video video, MultipartFile file, Long courseId) throws IOException {
        File dir = new File(uploadDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String filePath = file.getOriginalFilename(); // Just the filename
        File videoFile = new File(uploadDirectory + filePath);
        file.transferTo(videoFile);

        video.setFilePath(filePath); // Only filename
        video.setFileType(file.getContentType());
        video.setFileSize(file.getSize());
        video.setUploadDate(new java.util.Date());

        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()) {
            video.setCourse(courseOptional.get());
        } else {
            throw new IllegalArgumentException("Invalid course ID: " + courseId);
        }

        return videoRepository.save(video);
    }

    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    public Optional<Video> getVideoById(Long id) {
        return videoRepository.findById(id);
    }

    public Video updateVideo(Long id, Video updatedVideo) {
        Optional<Video> existingVideoOpt = videoRepository.findById(id);
        if (existingVideoOpt.isPresent()) {
            Video existingVideo = existingVideoOpt.get();
            existingVideo.setTitle(updatedVideo.getTitle());
            existingVideo.setCourse(updatedVideo.getCourse());
            return videoRepository.save(existingVideo);
        }
        return null;
    }

    public void deleteVideo(Long id) {
        videoRepository.deleteById(id);
    }

    public ResponseEntity<Resource> streamVideo(String fileName, HttpHeaders headers) {
        try {

            String decodedFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
            File videoFile = new File(decodedFileName);

            if (!videoFile.exists()) {
                return ResponseEntity.notFound().build();
            }

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, "video/mp4");
            long fileLength = videoFile.length();


            if (headers.getRange() != null && !headers.getRange().isEmpty()) {
                HttpRange range = headers.getRange().get(0);
                long start = range.getRangeStart(0);
                long end = range.getRangeEnd(fileLength - 1);


                if (start < 0 || end >= fileLength || start > end) {
                    return ResponseEntity.status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE).build();
                }

                long contentLength = end - start + 1;
                responseHeaders.add(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + fileLength);
                responseHeaders.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(contentLength));

                try (RandomAccessFile raf = new RandomAccessFile(videoFile, "r")) {
                    raf.seek(start);
                    byte[] buffer = new byte[(int) contentLength];
                    raf.readFully(buffer);
                    InputStream inputStream = new ByteArrayInputStream(buffer);
                    return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                            .headers(responseHeaders)
                            .body(new InputStreamResource(inputStream));
                }
            }

            responseHeaders.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileLength));
            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(new FileSystemResource(videoFile));

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
