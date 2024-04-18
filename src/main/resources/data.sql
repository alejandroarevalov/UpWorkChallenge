INSERT INTO Classrooms (qr_code) -- MD5 hashes
     VALUES ('b098078be0c4a994e3214daf50b5ba57'),
            ('ee850bf9fd577e190640e84803dd673b'),
            ('313fda6e61d9cd405800d1e8c26096f5');


INSERT INTO Activities (name, start_date, end_date, code, classroom_id)
     VALUES ('Java Programming I', '2024-04-15 08:00:00', '2024-04-15 10:00:00', 'JAVPRG1', 1),
            ('Java Programming II', '2024-04-19 10:30:00', '2024-04-19 12:30:00', 'JAVPRG2', 1),
            ('Java Programming III', '2024-04-19 12:30:00', '2024-04-19 14:30:00', 'JAVPRG3', 1),
            ('History of music', '2024-04-15 08:00:00', '2024-04-15 10:00:00', 'HSTMSC', 2),
            ('History of art', '2024-04-19 10:30:00', '2024-04-19 12:30:00', 'HSTART', 2),
            ('Far east philosophy', '2024-04-19 12:30:00', '2024-04-19 14:30:00', 'FRESTPHY', 2),
            ('Cooking I', '2024-04-15 10:00:00', '2024-04-15 12:00:00', 'CKNGI', 3),
            ('Spanish II', '2024-04-19 12:30:00', '2024-04-19 14:30:00', 'SPNII', 3),
            ('Spanish I', '2024-04-19 14:30:00', '2024-04-19 16:30:00', 'SPNI', 3);

INSERT INTO Users (user_name, password)
     VALUES ('alejandroarevalov@gmail.com', '123456'),
            ('julianasamp89@gmail.com', '234567'),
            ('cecikumara@gmail.com', '345678');

INSERT INTO Users_Activities (user_id, activity_id)
     VALUES (1, 1),
            (1, 2),
            (2, 5),
            (2, 6);