package com.its.memberboardproject.service;

import com.its.memberboardproject.common.PagingConst;
import com.its.memberboardproject.dto.BoardDTO;
import com.its.memberboardproject.entity.BoardEntity;
import com.its.memberboardproject.entity.MemberEntity;
import com.its.memberboardproject.repository.BoardRepository;
import com.its.memberboardproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public Long save(BoardDTO boardDTO) throws IOException {
        System.out.println("boardDTO = " + boardDTO);
        MultipartFile boardFile = boardDTO.getBoardFile();
        String boardFileName = boardFile.getOriginalFilename();
        boardFileName = System.currentTimeMillis()+"_"+boardFileName;
        String savePath = "D:\\springboot_img\\" + boardFileName;
        if(!boardFile.isEmpty()){ //파일이 있으면
            boardFile.transferTo(new File(savePath));
        }
        boardDTO.setBoardFileName(boardFileName);

        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(boardDTO.getBoardWriter());
        if(optionalMemberEntity.isPresent()){
            MemberEntity memberEntity = optionalMemberEntity.get();
            System.out.println("memberEntity = " + memberEntity);
            Long savedId = boardRepository.save(BoardEntity.toBoard(boardDTO, memberEntity)).getId();
            return savedId;

        }else {
            System.out.println("memberEntity = ");
            return null;
        }
    }

    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber();
        page = (page == 1)? 0: (page-1);
        Page<BoardEntity> boardEntities = boardRepository.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        Page<BoardDTO> boardList = boardEntities.map(
                board -> new BoardDTO(board.getId(),
                        board.getBoardTitle(),
                        board.getBoardWriter(),
                        board.getBoardContents(),
                        board.getBoardHits(),
                        board.getCreatedTime(),
                        board.getUpdatedTime(),
                        board.getBoardFileName()
                ));
        return boardList;
    }

    @Transactional
    public BoardDTO findById(Long id) {
        boardRepository.boardHits(id);
        Optional<BoardEntity>optionalBoardEntity = boardRepository.findById(id);
        if(optionalBoardEntity.isPresent()){
            return BoardDTO.toBoardDTO(optionalBoardEntity.get());
        }else {
            return null;
        }
    }
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

    public void update(BoardDTO boardDTO) {
        boardRepository.save(BoardEntity.toUpdateEntity(boardDTO));
    }

    @Transactional
    public List<BoardDTO> search(String q) {
        List<BoardEntity> boardEntityList = boardRepository.findByBoardTitleContainingOrBoardContentsContaining(q, q);
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for(BoardEntity boardEntity: boardEntityList){
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList;
    }
}
