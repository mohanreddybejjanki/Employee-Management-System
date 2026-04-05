package com.emp.service;

import com.emp.entity.Announcement;
import com.emp.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    public List<Announcement> getAll() {
        return announcementRepository.findAllByOrderByPostedOnDesc();
    }

    public Optional<Announcement> getById(Long id) {
        return announcementRepository.findById(id);
    }

    public Announcement save(Announcement announcement) {
        return announcementRepository.save(announcement);
    }

    public void delete(Long id) {
        announcementRepository.deleteById(id);
    }
}
