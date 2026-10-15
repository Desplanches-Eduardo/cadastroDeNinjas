-- V3: Aponta o img_url dos ninjas para as imagens que ficam em resources/static/img.
-- Antes a coluna vinha com o nome do arquivo solto (img-itachi.jpg), que nao existia em lugar
-- nenhum, e por isso o <img> do template aparecia quebrado.
-- O caminho comecando com / e o que o Spring Boot serve a partir de static/.

UPDATE tb_cadastro SET img_url = '/img/itachi-uchiha.png'   WHERE nome = 'Itachi Uchiha';
UPDATE tb_cadastro SET img_url = '/img/shikamaru-nara.png'  WHERE nome = 'Shikamaru Nara';
UPDATE tb_cadastro SET img_url = '/img/tsunade-senju.png'   WHERE nome = 'Tsunade Senju';
UPDATE tb_cadastro SET img_url = '/img/gai.png'             WHERE nome = 'Gai';
UPDATE tb_cadastro SET img_url = '/img/gaara.png'           WHERE nome = 'Gaara';
UPDATE tb_cadastro SET img_url = '/img/hashirama-senju.png' WHERE nome = 'Hashirama Senju';
UPDATE tb_cadastro SET img_url = '/img/naruto.png'          WHERE nome = 'Naruto';
UPDATE tb_cadastro SET img_url = '/img/tobirama-senju.png'  WHERE nome = 'Tobirama Senju';
UPDATE tb_cadastro SET img_url = '/img/minato-namikaze.png' WHERE nome = 'Minato Namikaze';
