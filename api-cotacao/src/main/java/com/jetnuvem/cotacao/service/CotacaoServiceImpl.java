package com.jetnuvem.cotacao.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jetnuvem.cotacao.model.Cotacao;
import com.jetnuvem.cotacao.model.CriarCotacaoRequest;
import com.jetnuvem.cotacao.model.Endereco;
import com.jetnuvem.cotacao.model.ProcessarEnderecoRequest;
import com.jetnuvem.cotacao.repository.CotacaoRepository;
import com.jetnuvem.viacep.client.api.ViaCepApiClient;

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
	public Endereco processar(ProcessarEnderecoRequest request) {
		com.jetnuvem.viacep.client.model.Endereco enderecoViaCep = processarCotacao(request.getCep());
		return converterParaEnderecoCotacao(enderecoViaCep);
	}

	private com.jetnuvem.viacep.client.model.Endereco processarCotacao(String cep) {

		try {
			if (viaCepClient == null) {
				System.out.println("ViaCepApiClient não está disponível");
				throw new RuntimeException("ViaCepApiClient não está disponível");
			}
			ResponseEntity<com.jetnuvem.viacep.client.model.Endereco> response = viaCepClient.consultarCep(cep);
			com.jetnuvem.viacep.client.model.Endereco endereco = response.getBody();
			// Verifica se o CEP é inexistente
			if (Boolean.TRUE.equals(endereco.getErro())) {
				System.out.println("CEP válido, mas inexistente na base.");
				throw new RuntimeException("CEP válido, mas inexistente na base.");
			}

			// CEP encontrado com sucesso
			System.out.println("CEP encontrado. Logradouro: " + endereco.getLogradouro());
			// segue seu fluxo de cotação...
			
			return endereco;
		} catch (FeignException e) {
			System.out.println("Erro ao chamar ViaCEP: HTTP " + e.status());
		} catch (Exception e) {
			System.out.println("Erro ao processar resposta do ViaCEP: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	private Endereco converterParaEnderecoCotacao(com.jetnuvem.viacep.client.model.Endereco enderecoViaCep) {
		if (enderecoViaCep == null) {
			return null;
		}

		Endereco endereco = new Endereco();
		endereco.setCep(enderecoViaCep.getCep());
		endereco.setLogradouro(enderecoViaCep.getLogradouro());
		endereco.setComplemento(enderecoViaCep.getComplemento());
		endereco.setUnidade(enderecoViaCep.getUnidade());
		endereco.setBairro(enderecoViaCep.getBairro());
		endereco.setLocalidade(enderecoViaCep.getLocalidade());
		endereco.setUf(enderecoViaCep.getUf());
		endereco.setEstado(enderecoViaCep.getEstado());
		endereco.setRegiao(enderecoViaCep.getRegiao());
		endereco.setIbge(enderecoViaCep.getIbge());
		endereco.setGia(enderecoViaCep.getGia());
		endereco.setDdd(enderecoViaCep.getDdd());
		endereco.setSiafi(enderecoViaCep.getSiafi());
		endereco.setErro(enderecoViaCep.getErro());

		return endereco;

	}
	
}
