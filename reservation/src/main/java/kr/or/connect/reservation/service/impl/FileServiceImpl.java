package kr.or.connect.reservation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.reservation.dao.FileDao;
import kr.or.connect.reservation.dao.ReviewWriteDao;
import kr.or.connect.reservation.dto.FileInfo;
import kr.or.connect.reservation.service.FileService;

@Service
public class FileServiceImpl implements FileService {
	
	@Autowired
	FileDao fileDao;
	
	@Autowired
	ReviewWriteDao reviewWriteDao;

	@Override
	public FileInfo getFileInfo(Integer id) {
		
		//각 이미지 태그 src에서 받은 file_id 값을 기반으로 하여 해당 이미지파일의 file_info 객체를 리턴해주는 매서드
		FileInfo fileInfo = new FileInfo();
		fileInfo = fileDao.getFileInfo(id);
		return fileInfo;
	}

	@Override
	public List<FileInfo> getFileNameForChecking(String fileName) {
		List<FileInfo> fileInfoCheck = reviewWriteDao.getFileName(fileName);
		return fileInfoCheck;
	}
	
	
	
}
