package com._DSF.je.Service;

import com._DSF.je.Entity.Course;
import com._DSF.je.Entity.Video;
import com._DSF.je.Repository.CourseRepository;
import com._DSF.je.Repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final CourseRepository courseRepository; // Add this line
    private final String uploadDirectory = "C:/videos/";

    @Autowired
    public VideoService(VideoRepository videoRepository, CourseRepository courseRepository) {
        this.videoRepository = videoRepository;
        this.courseRepository = courseRepository; // Initialize CourseRepository
    }

    public Video saveVideo(Video video, MultipartFile file, Long courseId) throws IOException {
        // Create the directory if it doesn't exist
        File dir = new File(uploadDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Save the file locally
        String filePath = uploadDirectory + file.getOriginalFilename();
        File videoFile = new File(filePath);
        file.transferTo(videoFile);

        // Set file details in the Video entity
        video.setFilePath(filePath);
        video.setFileType(file.getContentType());
        video.setFileSize(file.getSize());
        video.setUploadDate(new java.util.Date());

        // Fetch and set the Course entity
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
}
