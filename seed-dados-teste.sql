-- ============================================================
-- Script de dados de teste — Supermercado dos Operários
-- ============================================================
-- Só ADICIONA dados novos (categorias, produtos, clientes,
-- detentos e pedidos) — não mexe na tabela de usuarios, pra não
-- arriscar login de admin.
--
-- Como rodar: abra seu cliente MySQL (Workbench, DBeaver, ou o
-- terminal "mysql -u root supermercado_penitenciaria_db"),
-- conecte no banco supermercado_penitenciaria_db e execute este
-- script inteiro de uma vez.
--
-- Ele usa LAST_INSERT_ID() pra encadear os IDs gerados
-- automaticamente, então precisa ser executado em sequência,
-- numa única sessão/conexão (não em blocos separados).
-- ============================================================

USE supermercado_penitenciaria_db;

-- ---------- Categorias ----------

INSERT INTO categorias (categoria_nome, categoria_descricao, categoria_limite_quantidade)
VALUES ('Alimentos', 'Itens alimentícios não perecíveis', 20);
SET @cat_alimentos = LAST_INSERT_ID();

INSERT INTO categorias (categoria_nome, categoria_descricao, categoria_limite_quantidade)
VALUES ('Higiene Pessoal', 'Produtos de higiene e cuidados pessoais', 10);
SET @cat_higiene = LAST_INSERT_ID();

INSERT INTO categorias (categoria_nome, categoria_descricao, categoria_limite_quantidade)
VALUES ('Roupas', 'Vestuário básico', 5);
SET @cat_roupas = LAST_INSERT_ID();

INSERT INTO categorias (categoria_nome, categoria_descricao, categoria_limite_quantidade)
VALUES ('Eletrônicos', 'Rádios, fones e itens eletrônicos permitidos', 2);
SET @cat_eletronicos = LAST_INSERT_ID();

-- ---------- Produtos ----------

INSERT INTO produtos (produto_nome, produto_preco, produto_descricao, categoria_id) VALUES
  ('Bolacha Recheada', 4.50, 'Pacote 140g', @cat_alimentos);
SET @prod_bolacha = LAST_INSERT_ID();

INSERT INTO produtos (produto_nome, produto_preco, produto_descricao, categoria_id) VALUES
  ('Café 500g', 12.90, 'Torrado e moído', @cat_alimentos);
SET @prod_cafe = LAST_INSERT_ID();

INSERT INTO produtos (produto_nome, produto_preco, produto_descricao, categoria_id) VALUES
  ('Achocolatado em Pó', 8.50, 'Lata 400g', @cat_alimentos);
SET @prod_achocolatado = LAST_INSERT_ID();

INSERT INTO produtos (produto_nome, produto_preco, produto_descricao, categoria_id) VALUES
  ('Salgadinho', 6.00, 'Pacote 100g', @cat_alimentos);
SET @prod_salgadinho = LAST_INSERT_ID();

INSERT INTO produtos (produto_nome, produto_preco, produto_descricao, categoria_id) VALUES
  ('Sabonete', 3.00, 'Unidade 90g', @cat_higiene);
SET @prod_sabonete = LAST_INSERT_ID();

INSERT INTO produtos (produto_nome, produto_preco, produto_descricao, categoria_id) VALUES
  ('Pasta de Dente', 5.50, 'Tubo 90g', @cat_higiene);
SET @prod_pasta = LAST_INSERT_ID();

INSERT INTO produtos (produto_nome, produto_preco, produto_descricao, categoria_id) VALUES
  ('Shampoo', 9.90, 'Frasco 200ml', @cat_higiene);
SET @prod_shampoo = LAST_INSERT_ID();

INSERT INTO produtos (produto_nome, produto_preco, produto_descricao, categoria_id) VALUES
  ('Desodorante', 8.00, 'Roll-on 50ml', @cat_higiene);
SET @prod_desodorante = LAST_INSERT_ID();

INSERT INTO produtos (produto_nome, produto_preco, produto_descricao, categoria_id) VALUES
  ('Camiseta Branca', 25.00, 'Tamanho único, gola careca', @cat_roupas);
SET @prod_camiseta = LAST_INSERT_ID();

INSERT INTO produtos (produto_nome, produto_preco, produto_descricao, categoria_id) VALUES
  ('Cueca', 12.00, 'Kit com 1 unidade', @cat_roupas);
SET @prod_cueca = LAST_INSERT_ID();

INSERT INTO produtos (produto_nome, produto_preco, produto_descricao, categoria_id) VALUES
  ('Meia', 8.00, 'Par', @cat_roupas);
SET @prod_meia = LAST_INSERT_ID();

INSERT INTO produtos (produto_nome, produto_preco, produto_descricao, categoria_id) VALUES
  ('Rádio FM', 45.00, 'Rádio portátil a pilha', @cat_eletronicos);
SET @prod_radio = LAST_INSERT_ID();

INSERT INTO produtos (produto_nome, produto_preco, produto_descricao, categoria_id) VALUES
  ('Fone de Ouvido', 20.00, 'Fone com fio, modelo simples', @cat_eletronicos);
SET @prod_fone = LAST_INSERT_ID();

-- ---------- Clientes ----------

INSERT INTO clientes (cliente_nome, cliente_cadastro_penitenciaria, cliente_telefone) VALUES
  ('Maria da Silva', '2024001', '(34) 99111-2233');
SET @cli_maria = LAST_INSERT_ID();

INSERT INTO clientes (cliente_nome, cliente_cadastro_penitenciaria, cliente_telefone) VALUES
  ('José Oliveira', '2024002', '(34) 99222-3344');
SET @cli_jose = LAST_INSERT_ID();

INSERT INTO clientes (cliente_nome, cliente_cadastro_penitenciaria, cliente_telefone) VALUES
  ('Ana Pereira', '2024003', '(34) 99333-4455');
SET @cli_ana = LAST_INSERT_ID();

INSERT INTO clientes (cliente_nome, cliente_cadastro_penitenciaria, cliente_telefone) VALUES
  ('Carlos Souza', '2024004', '(34) 99444-5566');
SET @cli_carlos = LAST_INSERT_ID();

INSERT INTO clientes (cliente_nome, cliente_cadastro_penitenciaria, cliente_telefone) VALUES
  ('Fernanda Lima', '2024005', '(34) 99555-6677');
SET @cli_fernanda = LAST_INSERT_ID();

-- ---------- Detentos ----------

INSERT INTO detentos (detento_nome, detento_codigo_infopen, detento_tipo_localizacao, detento_identificador_localizacao, detento_numero_cela) VALUES
  ('João Santos', 'INF10001', 'ALA', 'A', '12');
SET @det_joao = LAST_INSERT_ID();

INSERT INTO detentos (detento_nome, detento_codigo_infopen, detento_tipo_localizacao, detento_identificador_localizacao, detento_numero_cela) VALUES
  ('Pedro Almeida', 'INF10002', 'ALA', 'B', '05');
SET @det_pedro = LAST_INSERT_ID();

INSERT INTO detentos (detento_nome, detento_codigo_infopen, detento_tipo_localizacao, detento_identificador_localizacao, detento_numero_cela) VALUES
  ('Rafael Nunes', 'INF10003', 'ALA', 'C', '03');
SET @det_rafael = LAST_INSERT_ID();

INSERT INTO detentos (detento_nome, detento_codigo_infopen, detento_tipo_localizacao, detento_identificador_localizacao, detento_numero_cela) VALUES
  ('Marcos Costa', 'INF10004', 'PAVILHAO', '3', '20');
SET @det_marcos = LAST_INSERT_ID();

INSERT INTO detentos (detento_nome, detento_codigo_infopen, detento_tipo_localizacao, detento_identificador_localizacao, detento_numero_cela) VALUES
  ('Lucas Ferreira', 'INF10005', 'PAVILHAO', '1', '08');
SET @det_lucas = LAST_INSERT_ID();

INSERT INTO detentos (detento_nome, detento_codigo_infopen, detento_tipo_localizacao, detento_identificador_localizacao, detento_numero_cela) VALUES
  ('Bruno Rocha', 'INF10006', 'PAVILHAO', '2', '15');
SET @det_bruno = LAST_INSERT_ID();

-- ---------- Pedidos + Itens ----------
-- Um pedido pra cada status, cobrindo Alas e Pavilhões, pra dar
-- pra ver a tela principal (em andamento) e o histórico com
-- pedidos de verdade.

-- 1) Maria -> João (Ala A) — Em Montagem
INSERT INTO pedidos (cliente_id, detento_id, pedido_status, pedido_data_criacao, pedido_observacao)
VALUES (@cli_maria, @det_joao, 'EM_MONTAGEM', NOW() - INTERVAL 1 DAY, NULL);
SET @ped1 = LAST_INSERT_ID();
INSERT INTO itens_pedido (pedido_id, produto_id, item_quantidade, item_preco_unitario) VALUES
  (@ped1, @prod_bolacha, 3, 4.50),
  (@ped1, @prod_sabonete, 2, 3.00);

-- 2) José -> Pedro (Ala B) — Aguardando Pagamento
INSERT INTO pedidos (cliente_id, detento_id, pedido_status, pedido_data_criacao, pedido_observacao)
VALUES (@cli_jose, @det_pedro, 'AGUARDANDO_PAGAMENTO', NOW() - INTERVAL 2 DAY, 'Cliente vai trazer o comprovante do PIX amanhã.');
SET @ped2 = LAST_INSERT_ID();
INSERT INTO itens_pedido (pedido_id, produto_id, item_quantidade, item_preco_unitario) VALUES
  (@ped2, @prod_cafe, 1, 12.90),
  (@ped2, @prod_achocolatado, 1, 8.50),
  (@ped2, @prod_pasta, 2, 5.50);

-- 3) Ana -> Rafael (Ala C) — Pago
INSERT INTO pedidos (cliente_id, detento_id, pedido_status, pedido_data_criacao, pedido_observacao)
VALUES (@cli_ana, @det_rafael, 'PAGO', NOW() - INTERVAL 3 DAY, NULL);
SET @ped3 = LAST_INSERT_ID();
INSERT INTO itens_pedido (pedido_id, produto_id, item_quantidade, item_preco_unitario) VALUES
  (@ped3, @prod_camiseta, 1, 25.00),
  (@ped3, @prod_meia, 2, 8.00),
  (@ped3, @prod_shampoo, 1, 9.90);

-- 4) Carlos -> Marcos (Pavilhão 3) — Aguardando Entrega
INSERT INTO pedidos (cliente_id, detento_id, pedido_status, pedido_data_criacao, pedido_observacao)
VALUES (@cli_carlos, @det_marcos, 'AGUARDANDO_ENTREGA', NOW() - INTERVAL 4 DAY, NULL);
SET @ped4 = LAST_INSERT_ID();
INSERT INTO itens_pedido (pedido_id, produto_id, item_quantidade, item_preco_unitario) VALUES
  (@ped4, @prod_radio, 1, 45.00),
  (@ped4, @prod_fone, 1, 20.00);

-- 5) Fernanda -> Lucas (Pavilhão 1) — Entregue (vai pro histórico)
INSERT INTO pedidos (cliente_id, detento_id, pedido_status, pedido_data_criacao, pedido_observacao)
VALUES (@cli_fernanda, @det_lucas, 'ENTREGUE', NOW() - INTERVAL 6 DAY, NULL);
SET @ped5 = LAST_INSERT_ID();
INSERT INTO itens_pedido (pedido_id, produto_id, item_quantidade, item_preco_unitario) VALUES
  (@ped5, @prod_salgadinho, 4, 6.00),
  (@ped5, @prod_desodorante, 1, 8.00);

-- 6) Maria -> Bruno (Pavilhão 2) — Recusado (vai pro histórico)
INSERT INTO pedidos (cliente_id, detento_id, pedido_status, pedido_data_criacao, pedido_motivo_recusa)
VALUES (@cli_maria, @det_bruno, 'RECUSADO', NOW() - INTERVAL 7 DAY, 'Detento foi transferido para outra unidade.');
SET @ped6 = LAST_INSERT_ID();
INSERT INTO itens_pedido (pedido_id, produto_id, item_quantidade, item_preco_unitario) VALUES
  (@ped6, @prod_cueca, 1, 12.00);

-- ============================================================
-- Fim do script.
-- Resumo: 4 categorias, 13 produtos, 5 clientes, 6 detentos
-- (3 em Alas, 3 em Pavilhões) e 6 pedidos, um em cada status —
-- incluindo Entregue e Recusado pra testar o histórico.
-- ============================================================
