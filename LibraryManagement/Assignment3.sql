INSERT INTO tbl_author
VALUES (1, 'John'), (2, 'Bongz'), (3, 'Gandhi'), (4, 'Megyn'), (5, 'George'), (6, 'Stephen King');



INSERT INTO tbl_library_branch
VALUES (1, 'Sharpstown', 'New York'), (2, 'Portland Library', 'Oregon'),
		(3, 'Seattle Library', 'Washington'), (4, 'St Louis Library', 'Missouri'),
        (5, 'Richmond Library', 'Virginia'), (6, 'Houston', 'Texas');



INSERT INTO tbl_genre
VALUES (1, 'Fiction'), (2, 'Satire'), (3, 'Anthologies'), (4, 'Drama'), (5, 'Romance'),
		(6, 'Horror');



INSERT INTO tbl_borrower
VALUES (1, 'James', 'San Jose', '503-285-7264'), (2, 'Laura', 'Miami', '721-452-9087'),
		(3, 'Maria', 'Jersey City', '654-987-0981'), (4, 'Allie', 'Bay Area', '342-320-0980'),
        (5, 'Nosizo', 'Manzini', '890-236-1283'), (6, 'Nomcebo', 'Mbabane', '321-535-6254');



INSERT INTO tbl_publisher
VALUES (1, 'Amazon', 'Seattle', '360-432-9087'), (2, 'McMillan', 'Mbabane', '291-345-9087'),
		(3, 'Pearson', 'London', '453-492-0952'), (4, 'Random House', 'New York', '675-438-3456'),
        (5, 'Wiley', 'Memphis', '142-675-9834'), (6, 'Informa', 'Omaha', '487-854-8473');



INSERT INTO tbl_book
VALUES (1, 'The lost Tribe', 1), (2, 'Things Fall Apart', 2), (3, 'Killing Jesus', 3),
		(4, 'The Great Gatsby', 4), (5, 'Game of thrones', 5), (6, 'Decision Points', 6);



INSERT INTO tbl_book_genres
VALUES (1, 1), (1,2), (3, 1), (2, 6), (5, 3), (6, 4);



INSERT INTO tbl_book_authors
VALUES (5, 2), (4, 4), (3, 2), (1, 6), (5, 1), (3, 5);



INSERT INTO tbl_book_loans
VALUES (1, 2, 3, '2014-7-4 04:13:54', '2014-7-6 04:13:54', '2014-8-4 04:13:54'),
		(3, 2, 3, '2014-7-4 04:13:55', '2015-7-6 04:13:54', '2014-9-4 04:13:54'),
        (5, 6, 5, '2014-7-3 04:13:54', '2014-7-6 04:15:54', '2015-8-4 04:13:54'),
        (3, 4, 1, '2012-8-4 04:13:54', '2012-9-6 04:13:54', '2012-9-4 06:13:54'),
        (1, 3, 3, '2011-7-4 04:13:54', '2011-8-6 04:13:54', '2011-8-5 04:13:54'),
        (2, 6, 5, '2015-2-4 04:13:54', '2015-7-6 04:13:54', '2015-9-4 04:13:54');



INSERT INTO tbl_book_copies
VALUES (5, 2, 2), (3, 2, 1), (1, 3, 6), (3, 1, 5), (3, 3, 2), (5, 5, 5);




-- *** This is where the assignment begins ***

-- 1
SELECT noOfCopies
FROM ((tbl_book NATURAL JOIN tbl_book_copies ) NATURAL JOIN tbl_library_branch ) 
WHERE title='The Lost Tribe' AND branchName='Sharpstown';


-- 2
SELECT branchName, noOfCopies
FROM ((tbl_book NATURAL JOIN tbl_book_copies ) NATURAL JOIN tbl_library_branch ) 
WHERE title='The Lost Tribe';


-- 3
SELECT name
	FROM tbl_borrower B
	WHERE cardNo NOT IN
    (SELECT cardNo FROM tbl_book_loans );


-- 4
SELECT b.Title, r.Name, r.Address
	FROM tbl_book b, tbl_borrower r, tbl_book_loans bl, tbl_library_branch lb
	WHERE lb.branchName='Sharpstown' AND lb.branchId=bl.branchId AND
	bl.dueDate='today' AND bl.cardNo=r.CardNo AND bl.bookId=b.bookId;


-- 5
SELECT lb.branchName, COUNT(*)
	FROM tbl_book_copies b, tbl_library_branch lb
	WHERE b.branchId = lb.branchId
	GROUP BY lb.branchName;


-- 6
SELECT	b.name, b.address, COUNT(*)
	FROM		tbl_borrower b, tbl_book_loans l
	WHERE	b.cardNo = l.cardNo
	GROUP BY	b.cardNo, b.Name, b.address
	HAVING	COUNT(*) > 5;


-- 7
SELECT title, noOfCopies
	FROM (((tbl_author NATURAL JOIN tbl_book) NATURAL JOIN tbl_book_copies)
	NATURAL JOIN tbl_library_branch)
	WHERE authorName = 'Stephen King' and branchName = 'Central';
