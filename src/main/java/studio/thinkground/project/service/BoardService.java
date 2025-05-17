package studio.thinkground.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import studio.thinkground.project.dto.BoardDTO;
import studio.thinkground.project.entity.BoardEntity;
import studio.thinkground.project.entity.BoardFileEntity;
import studio.thinkground.project.repository.BoardFileRepository;
import studio.thinkground.project.repository.BoardRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// DTO -> Entity (Entity Class)
// Enitity -> DTO // Entity는 db와 연관되어 있기 때문에 노출 최소화 필요 (DTO Class)

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;


    public void save(BoardDTO boardDTO) throws IOException {
        // 파일 첨부 여부에 따라 로직 분리
        if (boardDTO.getBoardFile().isEmpty()) {
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO); //dto -> entity로 바꾸는 변환 작업 혹은 객체 만들기
            boardRepository.save(boardEntity); //Repository에 Entity만 저장 가능
        } else {
            /*
              1. DTO에 담긴 파일 꺼내기
              2. 파일의 이름 가져옴
              3. 서버 저장용 이름을 만듦
              // 내사진.jpg => 83979835392_내사진.jpg
              4. 저장 경로 설정
              5. 해당 경로에 파일 저장
              6. board_table에 해당 데이터 save 처리
              7. board_file_table에 해당 데이터 save 처리
             */
            MultipartFile boardFile = boardDTO.getBoardFile(); //1 -> DTO에 있음
            String originalFilename = boardFile.getOriginalFilename();//2
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename; //3
            String savePath = "D:/springboot_img/" + storedFileName; //4 / D:/springboot_img/83979835392_내사진.jpg
            boardFile.transferTo(new File(savePath)); //5
            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDTO);
            Long savedId = boardRepository.save(boardEntity).getId();
            BoardEntity board = boardRepository.findById(savedId).get(); //부모 entity를 가져옴

            BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFilename, storedFileName); // BoardFileEntity 객체로 변환하기 위한 작업
            boardFileRepository.save(boardFileEntity);
        }

    }

    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll(); //Entity가 list 형식으로 나옴
        List<BoardDTO> boardDTOList = new ArrayList<>(); //Entity를 DTO로 바꿔야 한다.
        for (BoardEntity boardEntity : boardEntityList) {
            {
                boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
            }
        }
        return boardDTOList;
    }

    @Transactional//데이터의 일관성을 유지하기 위해서
    public void updateHits(Long id) {
        boardRepository.updateHits(id);// JPA에서 제공하는 메서드가 아니라 임의로 만든 메서드를 사용하는 경우 -> Transactional을 붙여야 한다.

    }

    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            return boardDTO;
        } else {
            return null;
        }
    }

    public BoardDTO update(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO); // toSaveEntity랑 다른 점 setId가 있음 update는 jpa 메소드에 없다
        boardRepository.save(boardEntity);
        return findById(boardDTO.getId());

    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }




}