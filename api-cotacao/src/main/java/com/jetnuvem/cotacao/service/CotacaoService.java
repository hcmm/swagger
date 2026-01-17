package com.jetnuvem.cotacao.service;

import java.util.UUID;

import com.jetnuvem.cotacao.model.Cotacao;
import com.jetnuvem.cotacao.model.CriarCotacaoRequest;

public interface CotacaoService {
  Cotacao criar(CriarCotacaoRequest request);
  Cotacao consultar(UUID cotacaoId);
}
