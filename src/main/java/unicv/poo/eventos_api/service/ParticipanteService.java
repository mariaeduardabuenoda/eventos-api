package unicv.poo.eventos_api.service;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unicv.poo.eventos_api.dto.ParticipanteRequestDto;
import unicv.poo.eventos_api.dto.ParticipanteResponseDto;
import unicv.poo.eventos_api.entity.Participante;
import unicv.poo.eventos_api.exception.RegraNegocioException;
import unicv.poo.eventos_api.mapper.ParticipanteMapper;
import unicv.poo.eventos_api.repository.ParticipanteRepository;
import unicv.poo.eventos_api.repository.InscricaoRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParticipanteService {

    private final ParticipanteRepository participanteRepository;
    private final InscricaoRepository inscricaoRepository;
    private final ParticipanteMapper participanteMapper;

    public List<ParticipanteResponseDto> listarTodos() {
        return participanteMapper.toDTOList(participanteRepository.findAll());
    }

    public ParticipanteResponseDto buscarPorId(Long id) {
        return participanteRepository.findById(id)
                .map(participanteMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Participante com ID " + id + " não encontrado."));
    }

    public ParticipanteResponseDto salvar(ParticipanteRequestDto participanteDTO) {
        if (participanteRepository.existsByNome(participanteDTO.nome())) {
            throw new RegraNegocioException("Nome já cadastrado.");
        }
        if (participanteRepository.existsByEmail(participanteDTO.email())) {
            throw new RegraNegocioException("Email já cadastrado.");
        }
        if (participanteRepository.existsByTelefone(participanteDTO.telefone())) {
            throw new RegraNegocioException("Telefone já cadastrado.");
        }

        Participante participante = participanteMapper.toEntity(participanteDTO);
        return participanteMapper.toDTO(participanteRepository.save(participante));
    }

    public ParticipanteResponseDto atualizar(Long id, ParticipanteRequestDto participanteDTO) {
        Participante participante = participanteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Participante com ID " + id + " não encontrado."));

        if (!participante.getNome().equals(participanteDTO.nome()) &&
                participanteRepository.existsByNome(participanteDTO.nome())) {
            throw new RegraNegocioException("Nome já cadastrado.");
        }
        if (!participante.getEmail().equals(participanteDTO.email()) &&
                participanteRepository.existsByEmail(participanteDTO.email())) {
            throw new RegraNegocioException("Email já cadastrado.");
        }
        if (!participante.getTelefone().equals(participanteDTO.telefone()) &&
                participanteRepository.existsByTelefone(participanteDTO.telefone())) {
            throw new RegraNegocioException("Telefone já cadastrado.");
        }

        participante.setNome(participanteDTO.nome());
        participante.setEmail(participanteDTO.email());
        participante.setTelefone(participanteDTO.telefone());

        return participanteMapper.toDTO(participanteRepository.save(participante));
    }


    @Transactional
public void deletar(Long id) {
    if (!participanteRepository.existsById(id)) {
        throw new EntityNotFoundException("Participante com ID " + id + " não encontrado.");
    }

    if (inscricaoRepository.existsByParticipanteIdAndStatus(id, "CONFIRMADA")) {
        throw new RegraNegocioException("Não é possível excluir um participante com inscrições ativas.");
    }

    inscricaoRepository.deleteByParticipanteIdAndStatus(id, "CANCELADA");
    participanteRepository.deleteById(id);
}

    public Participante buscarEntidadePorId(Long id) {
        return participanteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Participante com ID " + id + " não encontrado."));
    }
}