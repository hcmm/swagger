package com.jetnuvem.cotacao.service;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.jetnuvem.cotacao.model.Cotacao;
import com.jetnuvem.cotacao.model.CriarCotacaoRequest;
import com.jetnuvem.cotacao.model.ProcessarCotacaoRequest;
import com.jetnuvem.cotacao.repository.CotacaoRepository;
import com.jetnuvem.viacep.client.api.ViaCepApiClient;
import com.jetnuvem.viacep.client.model.Endereco;

import feign.FeignException;

@Service
public class CotacaoServiceImpl implements CotacaoService {

	private final CotacaoRepository repository;
	private final ViaCepApiClient viaCepClient;

	public CotacaoServiceImpl(CotacaoRepository repository, @Autowired(required = false) ViaCepApiClient viaCepClient) {
		this.repository = repository;
		this.viaCepClient = viaCepClient;
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

	@Override
	public Cotacao processar(UUID cotacaoId, ProcessarCotacaoRequest request) {
		Cotacao cotacao = consultar(cotacaoId);
		processarCotacao(request.getCep());
		return cotacao;
	}

	public void processarCotacao(String cep) {
		if (viaCepClient == null) {
			System.out.println("ViaCepApiClient não está disponível");
			return;
		}

		try {
			ResponseEntity<Endereco> response = viaCepClient.consultarCep(cep);
			Endereco endereco = response.getBody();

			if (endereco == null) {
				throw new IllegalStateException("Resposta vazia do ViaCEP");
			}

			// Verifica se o CEP é inexistente
			if (Boolean.TRUE.equals(endereco.getErro())) {
				System.out.println("CEP válido, mas inexistente na base.");
				return;
			}

			// CEP encontrado com sucesso
			System.out.println("CEP encontrado. Logradouro: " + endereco.getLogradouro());
			// segue seu fluxo de cotação...

		} catch (FeignException e) {
			System.out.println("Erro ao chamar ViaCEP: HTTP " + e.status());
		} catch (Exception e) {
			System.out.println("Erro ao processar resposta do ViaCEP: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
