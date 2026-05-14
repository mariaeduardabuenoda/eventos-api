package unicv.poo.eventos_api.service;

import unicv.poo.eventos_api.entity.Local;
import unicv.poo.eventos_api.dto.LocalRequestDTO;
import unicv.poo.eventos_api.dto.LocalResponseDTO;
import unicv.poo.eventos_api.mapper.LocalMapper;
import unicv.poo.eventos_api.repository.EventoRepository;
import unicv.poo.eventos_api.repository.LocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalService {

    @Autowired
    private LocalRepository localRepository;

    @Autowired
    private LocalMapper localMapper;

    public List<LocalResponseDTO> listarTodos(){
        List<Local> locais = localRepository.findAll();
        return localMapper.toResponseDTOList(locais);
    }

    public LocalResponseDTO buscarPorId(Long id){
        Local local = localRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Local não encontrado com o ID: " + id));
        return localMapper.toResponseDTO(local);
    }

    public LocalResponseDTO salvar(LocalRequestDTO localRequestDTO){
        Local local = localMapper.toEntity(localRequestDTO);
        Local localSalvo = localRepository.save(local);
        return localMapper.toResponseDTO(localSalvo);
    }

    public LocalResponseDTO atualizar(Long id, LocalRequestDTO localRequestDTO){
        Local localExistente = localRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Não é possível atualizar: Local não encontrado."));

        if(localRequestDTO.capacidade()< localExistente.getCapacidade()){
            boolean possuiEventoMaior = EventoRepository.existsByLocalIdAndParticipantesMaxGreaterThan(id, localRequestDTO.capacidade());

            if(possuiEventoMaior){
                throw new RuntimeException("Não é possível reduzir a capacidade: existe um evento previsto que excede a nova capacidade.");
            }
        }

        Local local = localMapper.toEntity(localRequestDTO);
        local.setId(id);

        Local localAtualizado = localRepository.save(local);
        return localMapper.toResponseDTO(localAtualizado);
    }
    

    public void deletar(Long id){
        if(!localRepository.existsById(id)){
            throw new RuntimeException("Não é possivel remover: Local não encontrado.");
        }
        localRepository.deleteById(id);
    }
}