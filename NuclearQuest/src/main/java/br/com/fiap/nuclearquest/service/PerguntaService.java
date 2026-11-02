package br.com.fiap.nuclearquest.service;

import br.com.fiap.nuclearquest.dto.*;
import br.com.fiap.nuclearquest.exception.RecursoNaoEncontradoException;
import br.com.fiap.nuclearquest.exception.RegraNegocioException;
import br.com.fiap.nuclearquest.model.*;
import br.com.fiap.nuclearquest.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PerguntaService {

    private final PerguntaRepository perguntaRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;
    private final RespostaUsuarioRepository respostaRepository;

    @Transactional(readOnly = true)
    public List<PerguntaResponse> listar() {
        return perguntaRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<PerguntaResponse> buscarPorTexto(String texto) {
        return perguntaRepository.findByEnunciadoContainingIgnoreCase(texto)
                .stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public PerguntaResponse buscarPorId(Long id) {
        return toResponse(obter(id));
    }

    @Transactional
    public PerguntaResponse criar(PerguntaRequest req) {
        Categoria categoria = categoriaRepository.findById(req.categoriaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Categoria não encontrada: " + req.categoriaId()));
        validarAlternativas(req.alternativas());

        Pergunta pergunta = new Pergunta();
        pergunta.setEnunciado(req.enunciado());
        pergunta.setCategoria(categoria);
        preencherAlternativas(pergunta, req.alternativas());

        return toResponse(perguntaRepository.save(pergunta));
    }

    @Transactional
    public PerguntaResponse atualizar(Long id, PerguntaRequest req) {
        Pergunta pergunta = obter(id);
        if (!pergunta.getCategoria().getId().equals(req.categoriaId())) {
            throw new RegraNegocioException("A categoria de uma pergunta não pode ser alterada.");
        }
        validarAlternativas(req.alternativas());

        pergunta.setEnunciado(req.enunciado());
        atualizarAlternativas(pergunta, req.alternativas());

        return toResponse(perguntaRepository.saveAndFlush(pergunta));
    }

    @Transactional
    public void deletar(Long id) {
        Pergunta pergunta = obter(id);
        perguntaRepository.delete(pergunta);
        perguntaRepository.flush();
    }

    @Transactional
    public ResultadoResponse responder(Long perguntaId, RespostaRequest req) {
        Pergunta pergunta = obter(perguntaId);
        Usuario usuario = usuarioRepository.findById(req.usuarioId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Usuário não encontrado: " + req.usuarioId()));

        Alternativa escolhida = pergunta.getAlternativas().stream()
                .filter(a -> a.getId().equals(req.alternativaId()))
                .findFirst()
                .orElseThrow(() -> new RegraNegocioException(
                        "A alternativa informada não pertence a esta pergunta."));

        Alternativa correta = pergunta.getAlternativas().stream()
                .filter(a -> Boolean.TRUE.equals(a.getCorreta()))
                .findFirst()
                .orElseThrow(() -> new RegraNegocioException(
                        "Pergunta sem alternativa correta cadastrada."));

        Long categoriaId = pergunta.getCategoria().getId();
        RespostaUsuario resposta = new RespostaUsuario();
        resposta.setDataResposta(LocalDateTime.now());
        resposta.setUsuario(usuario);
        resposta.setAlternativa(escolhida);
        resposta.setPerguntaId(pergunta.getId());
        resposta.setAlternativaCategoriaId(categoriaId);
        resposta.setCategoriaId(categoriaId);
        respostaRepository.save(resposta);

        boolean acertou = Boolean.TRUE.equals(escolhida.getCorreta());
        String mensagem = acertou
                ? "Parabéns! Resposta correta."
                : "Resposta incorreta. A resposta certa é: " + correta.getTexto();
        return new ResultadoResponse(acertou, correta.getTexto(), mensagem);
    }

    // métodos auxiliares

    private Pergunta obter(Long id) {
        return perguntaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pergunta não encontrada: " + id));
    }

    private void validarAlternativas(List<AlternativaRequest> alternativas) {
        long corretas = alternativas.stream()
                .filter(a -> Boolean.TRUE.equals(a.correta())).count();
        if (corretas != 1) {
            throw new RegraNegocioException("A pergunta deve ter exatamente uma alternativa correta.");
        }
    }

    private void preencherAlternativas(Pergunta pergunta, List<AlternativaRequest> novas) {
        for (AlternativaRequest a : novas) {
            Alternativa alt = new Alternativa();
            alt.setTexto(a.texto());
            alt.setCorreta(a.correta());
            pergunta.adicionarAlternativa(alt);
        }
    }

    // alternativas
    private void atualizarAlternativas(Pergunta pergunta, List<AlternativaRequest> novas) {
        List<Alternativa> atuais = pergunta.getAlternativas();
        if (atuais.size() == novas.size()) {
            for (int i = 0; i < novas.size(); i++) {
                atuais.get(i).setTexto(novas.get(i).texto());
                atuais.get(i).setCorreta(novas.get(i).correta());
            }
        } else {
            atuais.clear();
            preencherAlternativas(pergunta, novas);
        }
    }

    private PerguntaResponse toResponse(Pergunta p) {
        List<AlternativaResponse> alternativas = p.getAlternativas().stream()
                .map(a -> new AlternativaResponse(a.getId(), a.getTexto(), a.getCorreta()))
                .toList();
        return new PerguntaResponse(p.getId(), p.getEnunciado(),
                p.getCategoria().getId(), p.getCategoria().getNome(), alternativas);
    }
}