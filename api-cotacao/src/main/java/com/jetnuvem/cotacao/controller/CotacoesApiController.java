package com.jetnuvem.cotacao.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.jetnuvem.cotacao.api.CotacoesApi;
import com.jetnuvem.cotacao.model.Cotacao;
import com.jetnuvem.cotacao.model.CriarCotacaoRequest;
import com.jetnuvem.cotacao.service.CotacaoService;

@RestController
public class CotacoesApiController implements CotacoesApi {

    private final CotacaoService service;

    public CotacoesApiController(CotacaoService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<Cotacao> criarCotacao(CriarCotacaoRequest request) {
        Cotacao cotacao = service.criar(request);
        return ResponseEntity.status(201).body(cotacao);
    }

    @Override
    public ResponseEntity<Cotacao> consultarCotacao(UUID cotacaoId) {
        return ResponseEntity.ok(service.consultar(cotacaoId));
    }
}

