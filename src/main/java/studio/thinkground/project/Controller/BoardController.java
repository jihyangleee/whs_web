package studio.thinkground.project.Controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import studio.thinkground.project.dto.BoardDTO;
import studio.thinkground.project.entity.BoardFileEntity;
import studio.thinkground.project.repository.BoardFileRepository;
import studio.thinkground.project.repository.BoardRepository;
import studio.thinkground.project.service.BoardService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;

    @GetMapping("/index")
    public String index(){
        return "index";
    }


    @GetMapping("/save")
    public String saveForm() {
        return "save";
    }
// 같은 메소드 , 같은 주소는 불가능
    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO) throws IOException {
        System.out.println("boardDTO =" +boardDTO);
        boardService.save(boardDTO);
        return "index";
    }
    @GetMapping("/") // 데이터를 가져올때는 모델 객체를 사용한다.
    public String findAll(Model model){
        // DB에서 전체 게시글 데이터를 가져와서 list.html에 보여준다. boardService에는 dto 형태로 된 것을 그대로 list로 받아서
        // model 객체에서 접근 -> list.html에서 보여준다.
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList",boardDTOList);
        return "list";
    }
    @GetMapping("/{id}")  //경로 상에 있는 값을 가져올 때는 PathVariable을 이용한다.
    public  String findById(@PathVariable Long id, Model model){
    /*
      해당 게시글의 조회수를 하나 올리고
      게시글 데이터를 가져와서 detail.html에 출력
    */
     boardService.updateHits(id);
     BoardDTO boardDTO = boardService.findById(id);
     model.addAttribute("board",boardDTO); //해당 게시글 dto로 받아오고 그것을 model에 담아서
     return "detail";

    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate",boardDTO);
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model){
        BoardDTO board= boardService.update(boardDTO);
        model.addAttribute("board",board); //수정 완료 객체
        return "detail";
        //return "redirect:/board/" + boardDTO.getId();

    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        boardService.delete(id);
        return "redirect:/board/";
    }
    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable Long fileId) throws Exception {
        BoardFileEntity file = boardFileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("파일 없음"));

        String path = "D:/springboot_img/" + file.getStoredFileName();
        Resource resource = new UrlResource(Paths.get(path).toUri());

        if (!resource.exists()) throw new FileNotFoundException("파일 없음");

        String encodedName = URLEncoder.encode(file.getOriginalFileName(), "UTF-8").replaceAll("\\+", "%20");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}



