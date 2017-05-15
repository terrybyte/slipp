insert into USER (ID, USERID ,PASSWORD ,NAME ,EMAIL ) values (1, 'terry', '1234', 'terry', 'terry@test.com');
insert into USER (ID, USERID ,PASSWORD ,NAME ,EMAIL ) values (2, 'terry2', '1234', 'terry2', 'terry2@test.com');

INSERT INTO QUESTION (ID, WRITER_ID, TITLE, CONTENTS, CREATE_DATE) VALUES (1, 1, '제목 테스트1','내용 테스트1', CURRENT_TIMESTAMP());
INSERT INTO QUESTION (ID, WRITER_ID, TITLE, CONTENTS, CREATE_DATE) VALUES (2, 2, '제목 테스트2','내용 테스트2', CURRENT_TIMESTAMP());