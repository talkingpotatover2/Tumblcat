package com.cat.project.img;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long imgId;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String imgLink;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String imgDesc;

	private String imgOriName;

	private String imgStoredName;
}