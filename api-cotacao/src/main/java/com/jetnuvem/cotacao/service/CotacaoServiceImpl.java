package com.jetnuvem.cotacao.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jetnuvem.cotacao.model.Cotacao;
import com.jetnuvem.cotacao.model.CriarCotacaoRequest;
import com.jetnuvem.cotacao.repository.CotacaoRepository;

@Service
public class CotacaoServiceImpl implements CotacaoService {

  private final CotacaoRepository repository;

  public CotacaoServiceImpl(CotacaoRepository repository) {
    this.repository = repository;
  }

  @Override
  public Cotacao criar(CriarCotacaoRequest request) {
    Cotacao cotacao = new Cotacao();

    cotacao.setCotacaoId(UUID.randomUUID());
    cotacao.setStatus(Cotacao.StatusEnum.CRIADA);

    cotacao.setClienteId(request.getClienteId());
    cotacao.setPremioSugerido(request.getPremioSugerido());

    return repository.salvar(cotacao);
  }

  @Override
  public Cotacao consultar(UUID cotacaoId) {
    return repository.buscarPorId(cotacaoId)
        .orElseThrow(() -> new IllegalArgumentException("Cotação não encontrada: " + cotacaoId));
  }
}
