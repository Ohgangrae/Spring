CREATE SEQUENCE SEQ_BOARD;

CREATE TABLE TBL_BOARD(
	BOARD_BNO NUMBER PRIMARY KEY,
	BOARD_TITLE VARCHAR2(2000) NOT NULL,
	BOARD_CONTENT VARCHAR2(2000) NOT NULL,
	BOARD_WRITER VARCHAR2(1000) NOT NULL,
	BOARD_REGISTER_DATE DATE DEFAULT SYSDATE,
	BOARD_UPDATE_DATE DATE DEFAULT SYSDATE
);

SELECT * FROM TBL_BOARD;

INSERT INTO TBL_BOARD
(BOARD_BNO, BOARD_TITLE, BOARD_CONTENT, BOARD_WRITER)
VALUES(SEQ_BOARD.NEXTVAL, '테스트 제목3', '테스트 내용3', '한동석');


INSERT INTO TBL_BOARD
(BOARD_BNO, BOARD_TITLE, BOARD_CONTENT, BOARD_WRITER)
(SELECT SEQ_BOARD.NEXTVAL, BOARD_TITLE, BOARD_CONTENT, BOARD_WRITER FROM TBL_BOARD);

/*
 * FROM, ON, WHERE, SELECT, ORDER BY
 * */

SELECT /*+ INDEX_DESC(TBL_BOARD SYS_C007186) */ 
ROWNUM, BOARD_BNO, BOARD_TITLE, BOARD_CONTENT, BOARD_WRITER 
FROM TBL_BOARD

TRUNCATE TABLE TBL_BOARD;















