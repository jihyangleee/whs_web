package studio.thinkground.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import studio.thinkground.project.entity.BoardEntity;

public interface BoardRepository extends JpaRepository <BoardEntity, Long> {
    //update board_table set board_hists= board_hits+1 where id=?
    //jpa에서 제공하는 annotation
    @Modifying
    @Query(value="update BoardEntity b set b.boardHits = b.boardHits+1 where b.id=:id")
    void updateHits(@Param("id") Long id);

}
