CREATE TABLE idiom (
	id int unsigned NOT NULL PRIMARY KEY, 
	word varchar(20) NOT NULL,
	pronunciation varchar(50) NOT NULL,
	explanation varchar(500) NOT NULL,
	source varchar(500) NOT NULL,
	UNIQUE unx_word(word)
) ENGINE=InnoDB ROW_FORMAT=COMPRESSED KEY_BLOCK_SIZE=4;