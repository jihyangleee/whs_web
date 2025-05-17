package studio.thinkground.project.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile; // spring에서 제공하는 인터페이스 (MultipartFile)
import studio.thinkground.project.entity.BoardEntity;

import java.time.LocalDateTime;

// DTO( data transfer object) : 데이터를 전송할때 사용하는 객체, VO, Bean ,Entity(다른 목적을 가진 클래스)
@Getter
@Setter
@ToString
@NoArgsConstructor //기본 생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자


public class BoardDTO {
    private Long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private int boardHits;
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;
    private MultipartFile boardFile; // 여러 파일 저장 가능 / save.html -> controller로 넘어올때 파일을 담는 용도
    private String originalFileName; // 원본 파일 이름
    private String storedFileName; // 서버 저장용 파일 이름
    private int fileAttached; // 파일 첨부 여부(첨부 1, 미첨부 0)

    public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId()); //Entity에서 데이터를 꺼내서 DTO에 저장
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardPass(boardEntity.getBoardPass());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setBoardCreatedTime(boardEntity.getCreatedTime());
        boardDTO.setBoardUpdatedTime(boardEntity.getUpdatedTime());
        return boardDTO;
    }
}


