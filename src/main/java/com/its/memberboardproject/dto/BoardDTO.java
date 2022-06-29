package com.its.memberboardproject.dto;

import com.its.memberboardproject.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private Long id;
    private String boardTitle;
    private String boardWriter;
    private String boardContents;
    private int boardHits;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private MultipartFile boardFile;
    private String boardFileName;

    public BoardDTO(String boardTitle, String boardWriter, String boardContents, int boardHits, String boardFileName) {
        this.boardTitle = boardTitle;
        this.boardWriter = boardWriter;
        this.boardContents = boardContents;
        this.boardHits = boardHits;
        this.boardFileName = boardFileName;
    }

    public BoardDTO(Long id, String boardTitle, String boardWriter, String boardContents, int boardHits, LocalDateTime createdDate, LocalDateTime updatedDate, String boardFileName) {
        this.id = id;
        this.boardTitle = boardTitle;
        this.boardWriter = boardWriter;
        this.boardContents = boardContents;
        this.boardHits = boardHits;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.boardFileName = boardFileName;

    }

    public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setCreatedDate(boardEntity.getCreatedTime());
        boardDTO.setUpdatedDate(boardEntity.getUpdatedTime());
        boardDTO.setBoardFileName(boardEntity.getBoardFileName());
        return boardDTO;
    }
}
