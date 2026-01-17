package com.jetnuvem.cotacao.repository;

import com.jetnuvem.cotacao.model.Cotacao;

import java.util.Optional;
import java.util.UUID;

public interface CotacaoRepository {
  Cotacao salvar(Cotacao cotacao);
  Optional<Cotacao> buscarPorId(UUID cotacaoId);
}
