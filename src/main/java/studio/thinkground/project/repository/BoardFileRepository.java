package studio.thinkground.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studio.thinkground.project.entity.BoardEntity;
import studio.thinkground.project.entity.BoardFileEntity;

import java.util.List;
// Long은 pk의 타입
public interface BoardFileRepository extends JpaRepository<BoardFileEntity,Long> {
    List<BoardFileEntity> findAllByBoardEntity(BoardEntity boardEntity);
}
