package com.cat.project.img;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

	Image findByImgStoredName(String str);
}
