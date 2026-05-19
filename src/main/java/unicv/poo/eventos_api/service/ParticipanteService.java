package unicv.poo.eventos_api.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicv.poo.eventos_api.dto.ParticipanteRequestDto;
import unicv.poo.eventos_api.dto.ParticipanteResponseDto;
import unicv.poo.eventos_api.entity.Participante;
import unicv.poo.eventos_api.mapper.ParticipanteMapper;
import unicv.poo.eventos_api.repository.ParticipanteRepository;

@Service
public class ParticipanteService {

    @Autowired
    private ParticipanteRepository participanteRepository;

    @Autowired
    private ParticipanteMapper participanteMapper;

    public List<ParticipanteResponseDto> listarTodos() {
        return participanteMapper.toDTOList(participanteRepository.findAll());
    }

    public ParticipanteResponseDto buscarPorId(Long id) {
        return participanteRepository.findById(id)
                .map(participanteMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Participante não encontrado"));
    }

    public ParticipanteResponseDto salvar(ParticipanteRequestDto participanteDTO) {

        if (participanteRepository.existsByNome(participanteDTO.nome())) {
            throw new RuntimeException("Nome já cadastrado");
        }

        if (participanteRepository.existsByEmail(participanteDTO.email())) {
            throw new RuntimeException("Email já cadastrado");
        }
        
        if (participanteRepository.existsByTelefone(participanteDTO.telefone())) {
            throw new RuntimeException("Telefone já cadastrado");
        }
        Participante participante = participanteMapper.toEntity(participanteDTO);
        return participanteMapper.toDTO(participanteRepository.save(participante));
    }

    public ParticipanteResponseDto atualizar(Long id, ParticipanteRequestDto participanteDTO) {
        // Busca o participante existente
        Participante participante = participanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Participante não encontrado"));
        
        // Valida nome duplicado (exceto se for o mesmo nome)
        if (!participante.getNome().equals(participanteDTO.nome()) &&
                participanteRepository.existsByNome(participanteDTO.nome())) {
            throw new RuntimeException("Nome já cadastrado");
        }
        
        // Valida email duplicado (exceto se for o mesmo email)
        if (!participante.getEmail().equals(participanteDTO.email()) &&
                participanteRepository.existsByEmail(participanteDTO.email())) {
            throw new RuntimeException("Email já cadastrado");
        }
        
        // Valida telefone duplicado (exceto se for o mesmo telefone)
        if (!participante.getTelefone().equals(participanteDTO.telefone()) &&
                participanteRepository.existsByTelefone(participanteDTO.telefone())) {
            throw new RuntimeException("Telefone já cadastrado");
        }
        
        // Atualiza os dados
        participante.setNome(participanteDTO.nome());
        participante.setEmail(participanteDTO.email());
        participante.setTelefone(participanteDTO.telefone());
        
        // Salva e retorna DTO
        return participanteMapper.toDTO(participanteRepository.save(participante));
    }

    public void deletar(Long id) {
        if (!participanteRepository.existsById(id)) {
            throw new RuntimeException("Participante não encontrado");
        }
        participanteRepository.deleteById(id);
    }
}