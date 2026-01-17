package com.jetnuvem.cotacao.repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.jetnuvem.cotacao.model.Cotacao;

@Repository
public class CotacaoRepositoryEmMemoria implements CotacaoRepository {

  private final Map<UUID, Cotacao> banco = new ConcurrentHashMap<>();

  @Override
  public Cotacao salvar(Cotacao cotacao) {
    banco.put(cotacao.getCotacaoId(), cotacao);
    return cotacao;
  }

  @Override
  public Optional<Cotacao> buscarPorId(UUID cotacaoId) {
    return Optional.ofNullable(banco.get(cotacaoId));
  }
}
