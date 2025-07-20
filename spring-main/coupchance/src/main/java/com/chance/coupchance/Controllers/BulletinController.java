package com.chance.coupchance.Controllers;

import com.chance.coupchance.Entites.Bulletin;
import com.chance.coupchance.Service.BulletinService;
import com.chance.coupchance.DTO.BulletinDTO;
import com.chance.coupchance.DTO.BulletinResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bulletins")
public class BulletinController {
    private final BulletinService bulletinService;

    public BulletinController(BulletinService bulletinService) {
        this.bulletinService = bulletinService;
    }

    @PostMapping
    public BulletinResponseDTO createBulletin(@RequestBody Bulletin bulletin) {
        Bulletin savedBulletin = bulletinService.createBulletin(bulletin);
        return BulletinResponseDTO.fromEntity(savedBulletin);
    }

    @PostMapping("/complet")
    public BulletinResponseDTO createBulletinComplet(@RequestBody BulletinDTO dto) {
        Bulletin savedBulletin = bulletinService.createBulletinFromDTO(dto);
        return BulletinResponseDTO.fromEntity(savedBulletin);
    }

    @GetMapping
    public List<BulletinResponseDTO> getAllBulletins() {
        return bulletinService.getAllBulletins().stream()
                .map(BulletinResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BulletinResponseDTO getBulletinById(@PathVariable Long id) {
        Bulletin bulletin = bulletinService.getBulletinById(id);
        return BulletinResponseDTO.fromEntity(bulletin);
    }

    @PutMapping("/{id}")
    public BulletinResponseDTO updateBulletin(@PathVariable Long id, @RequestBody Bulletin bulletin) {
        Bulletin updatedBulletin = bulletinService.updateBulletin(id, bulletin);
        return BulletinResponseDTO.fromEntity(updatedBulletin);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBulletin(@PathVariable Long id) {
        bulletinService.deleteBulletin(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/eleve/{eleveId}")
    public List<BulletinResponseDTO> getBulletinsByEleve(@PathVariable Long eleveId) {
        return bulletinService.getBulletinsByEleve(eleveId).stream()
                .map(BulletinResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/trimestre/{trimestre}")
    public List<BulletinResponseDTO> getBulletinsByTrimestre(@PathVariable int trimestre) {
        return bulletinService.getBulletinsByTrimestre(trimestre).stream()
                .map(BulletinResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/eleve/{eleveId}/trimestre/{trimestre}")
    public List<BulletinResponseDTO> getBulletinsByEleveAndTrimestre(
            @PathVariable Long eleveId,
            @PathVariable int trimestre) {
        return bulletinService.getBulletinsByEleveAndTrimestre(eleveId, trimestre).stream()
                .map(BulletinResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
}