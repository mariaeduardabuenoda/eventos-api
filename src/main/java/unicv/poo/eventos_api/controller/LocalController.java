package unicv.poo.eventos_api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import unicv.poo.eventos_api.dto.LocalRequestDTO;
import unicv.poo.eventos_api.dto.LocalResponseDTO;
import unicv.poo.eventos_api.service.LocalService;

@Tag(name = "Locais", description = "Endpoints para gerenciamento de locais")
@RestController
@RequestMapping("api/locais")
public class LocalController{

    @Autowired
    private LocalService localService;

    @Operation(summary = "Lista todos os locais", description = "Retorna uma lista com todos os locais cadastrados")
    @GetMapping
    public ResponseEntity<List<LocalResponseDTO>> listarLocais(){
        List<LocalResponseDTO> locais = LocalService.listarTodos();
        return ResponseEntity.ok(locais);
    }

    @Operation(summary = "Busca um local por ID", description = "Retorna os detalhes de um local especifico")
    @GetMapping("/{id}")
    public ResponseEntity<LocalResponseDTO> buscarPorId(@PathVariable Long id){
        Optional<LocalResponseDTO> localResponseDTO = LocalService.buscarPorId(id);
        return localResponseDTO.map(ResponseEntity::ok)
                               .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cria um novo usuário", description = "Cadastra um novo usuário no sistema")
    @PostMapping
    public ResponseEntity<ApiResponse<LocalRequestDTO>> criarUsuario(@Valid @RequestBody LocalRequestDTO localRequestDTO){
        try{
            // Tenta salvar o local
            LocalRequestDTO savedLocal = localService.salvar(localRequestDTO);

           // Retorna sucesso com o LocalRequestDTO salvo
           ApiResponse<LocalRequestDTO> response = new ApiResponse<>(savedLocal);
           return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
         // Cria um erro com a mensagem específica
         ErroResponse erroResponse = new ErroResponse("Argumento inválido", e.getMessage());
         ApiResponse<LocalRequestDTO> response = new ApiResponse<>(erroResponse);
         return ResponseEntity.badRequest().body(response);
        }catch (Exception e){
         // Cria um erro genérico
         ErroResponse erroResponse = new ErroResponse("Erro interno" , e.getMessage());
         ApiResponse<LocalRequestDTO> response = new ApiResponse<>(erroResponse);
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }


    }


}