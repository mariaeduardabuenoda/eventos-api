package unicv.poo.eventos_api.service;


import unicv.poo.eventos_api.entity.Local;
import unicv.poo.eventos_api.dto.LocalRequestDTO;
import unicv.poo.eventos_api.dto.LocalResponseDTO;
import unicv.poo.eventos_api.mapper.LocalMapper;
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

    //  Listar Locais (GET /api/locais)
    public List<LocalResponseDTO> listarTodos(){
        List<Local> locais = localRepository.findAll();
        return localMapper.toResponseDTOList(locais);

    }

    // Buscar Local por ID (GET /api/locais/{id})
    public Optional<LocalResponseDTO> buscarPorId(Long id){
        Local local = localRepository.findById(id)
        .orElseThrow( () -> new RuntimeException("Local não encontrado com o ID: " + id));
        return localMapper.toResponseDTO(local);
    }

    //  Registrar Local (POST /api/locais)
    public LocalRequestDTO salvar(LocalRequestDTO localRequestDTO){
        Local local = localMapper.toEntity(LocalRequestDTO);
        Local localSalvo = localRepository.save(local);
        return localMapper.toResponseDTO(localSalvo);
    }

     //  Atualizar Local (PUT /api/locais/{id})
     public LocalResponseDTO atualizar(Long id, LocalRequestDTO localRequestDTO){
        if (!localRepository.existsById(id)){
            throw new RuntimeException("Não é possivel atualizar: Local não encontrado.");
        }

        Local local = localMapper.toEntity(localRequestDTO);
        local.setId(id);

        Local localAtualizado = localRepository.save(local);
        return localMapper.toResponseDTO(localAtualizado);
     }


     // Remover Local (DELETE /api/locais/{id})
    public void deletar (Long id){
        if(!localRepository.existsById(id)){
            throw new RuntimeException("Não é possivel remover: Local não encontrado.");
        }
        localRepository.deletarById(id);
    }





}