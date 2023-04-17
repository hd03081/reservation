package kr.or.connect.reservation.service;

import java.util.List;

import kr.or.connect.reservation.dto.FileInfo;

public interface FileService {
	public FileInfo getFileInfo(Integer id);
	
	public List<FileInfo> getFileNameForChecking(String fileName);
}
