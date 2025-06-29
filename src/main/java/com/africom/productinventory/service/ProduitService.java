package com.africom.productinventory.service;

import com.africom.productinventory.domain.MouvementStock;
import com.africom.productinventory.domain.Produit;
import com.africom.productinventory.repository.MouvementStockRepository;
import com.africom.productinventory.repository.ProduitRepository;
import com.africom.productinventory.service.dto.ProduitDTO;
import com.africom.productinventory.service.mapper.ProduitMapper;
import com.africom.productinventory.web.rest.errors.BadRequestAlertException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;
/**
 * Service Implementation for managing {@link Produit}.
 */
@Service
@Transactional
public class ProduitService {

    private final Logger log = LoggerFactory.getLogger(ProduitService.class);

    private final ProduitRepository produitRepository;
    @Autowired
    private MouvementStockRepository mouvementStockRepository;
    private final MailService mailService;


    private final ProduitMapper produitMapper;

    public ProduitService(ProduitRepository produitRepository, MailService mailService, ProduitMapper produitMapper) {
        this.produitRepository = produitRepository;
        this.mailService = mailService;
        this.produitMapper = produitMapper;
    }

    /**
     * Save a produit.
     *
     * @param produitDTO the entity to save.
     * @return the persisted entity.
     */
    public ProduitDTO save(ProduitDTO produitDTO) {
        log.debug("Request to save Produit : {}", produitDTO);
        Produit produit = produitMapper.toEntity(produitDTO);
        produit = produitRepository.save(produit);
        return produitMapper.toDto(produit);
    }

    /**
     * Get all the produits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProduitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Produits");
        return produitRepository.findAll(pageable)
            .map(produitMapper::toDto);
    }


    /**
     * Get one produit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProduitDTO> findOne(Long id) {
        log.debug("Request to get Produit : {}", id);
        return produitRepository.findById(id)
            .map(produitMapper::toDto);
    }

    /**
     * Delete the produit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Produit : {}", id);
        produitRepository.deleteById(id);
    }

    public byte[] exportProduitsByFamilleToExcel(Long familleId) throws IOException {
        List<Produit> produits = produitRepository.findAllByFamilleId(familleId);

        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("ProduitsFamille");

        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        String[] headers = {"ID", "Nom du produit", "Famille", "Détails"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        int rowIndex = 1;
        for (Produit p : produits) {
            Row row = sheet.createRow(rowIndex++);
            int col = 0;
            row.createCell(col++).setCellValue(p.getId());
            row.createCell(col++).setCellValue(p.getNom());
            String nomFamille = (p.getFamille() != null) ? p.getFamille().getNom() : "";
            row.createCell(col++).setCellValue(nomFamille);
            String detailsStr = "";
            if (p.getDetails() != null) {
                try {
                    Object detailsObj = p.getDetails();
                    if (detailsObj instanceof String) {
                        detailsObj = objectMapper.readValue((String) detailsObj, Object.class);
                    }
                    detailsStr = objectMapper.writeValueAsString(detailsObj);
                } catch (Exception e) {
                    detailsStr = p.getDetails().toString();
                }
            }
            row.createCell(col).setCellValue(detailsStr);
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            workbook.write(baos);
            workbook.close();
            return baos.toByteArray();
        }
    }

    /*public ByteArrayOutputStream exportProduitsByFamilleToExcel(Long familleId) throws IOException {
        List<Produit> produits = produitRepository.findByFamilleId(familleId);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Produits");

        // En-tête
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Nom", "Description", "Prix", "Stock", "Catégorie", "Image URL", "Détails"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Lignes de données
        int rowIndex = 1;
        for (Produit produit : produits) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(produit.getId());
            row.createCell(1).setCellValue(produit.getNom());
            row.createCell(2).setCellValue(produit.getDescription() != null ? produit.getDescription() : "");
            row.createCell(3).setCellValue(produit.getPrix());
            row.createCell(4).setCellValue(produit.getStock());
            row.createCell(5).setCellValue(produit.getCategorie() != null ? produit.getCategorie() : "");
            row.createCell(6).setCellValue(produit.getImageUrl() != null ? produit.getImageUrl() : "");

            // Serialisation des détails (Object JSON)
            ObjectMapper mapper = new ObjectMapper();
            try {
                row.createCell(7).setCellValue(mapper.writeValueAsString(produit.getDetails()));
            } catch (Exception e) {
                row.createCell(7).setCellValue("Erreur JSON");
            }
        }

        // Auto-sizing
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Output
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return out;
    }**/


    @Transactional(readOnly = true)
    public List<ProduitDTO> findAllByFamilleId(Long familleId) {
        return produitRepository.findAllByFamilleId(familleId)
            .stream()
            .map(produitMapper::toDto)
            .collect(Collectors.toList());
    }
    /**
     * Décrémente le stock d'un produit de la quantité donnée (pour une commande).
     */
    public ProduitDTO decrementStock(Long produitId, int quantite) {
        Produit produit = produitRepository.findById(produitId)
            .orElseThrow(() -> new BadRequestAlertException("Produit non trouvé", "produit", "notfound"));
        if (produit.getStock() < quantite) {
            throw new BadRequestAlertException("Stock insuffisant", "produit", "lowstock");
        }
        produit.setStock(produit.getStock() - quantite);
        produit = produitRepository.save(produit);
        return produitMapper.toDto(produit);
    }

    /**
     * Incrémente le stock d'un produit (par réapprovisionnement ou annulation de commande).
     */
    public ProduitDTO incrementStock(Long produitId, int quantite) {
        Produit produit = produitRepository.findById(produitId)
            .orElseThrow(() -> new BadRequestAlertException("Produit non trouvé", "produit", "notfound"));
        produit.setStock(produit.getStock() + quantite);
        produit = produitRepository.save(produit);
        return produitMapper.toDto(produit);
    }

    /**
     * Retourne la liste des produits dont le stock est inférieur à un seuil donné.
     * Utile pour alerter le magasiner de commander plus.
     */
    @Transactional(readOnly = true)
    public List<ProduitDTO> findLowStock(int seuil) {
        return produitRepository.findByStockLessThan(seuil)
            .stream().map(produitMapper::toDto).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public Map<String, Integer> analyserTendancesProduitsParStock() {
        List<Produit> produits = produitRepository.findAll();
        Map<String, Integer> tendances = new HashMap<>();

        for (Produit produit : produits) {
            int nbSorties = mouvementStockRepository.countByProduitIdAndTypeMouvement(produit.getId(), "SORTIE");
            tendances.put(produit.getNom(), nbSorties);
        }

        return tendances.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
            ));
    }

    @Transactional
    public void mettreAJourStockProduit(Long idProduit, int nouveauStock) {
        Produit produit = produitRepository.findById(idProduit)
            .orElseThrow(() -> new BadRequestAlertException("Produit non trouvé", "produit", "notfound"));

        if (nouveauStock < 0) {
            throw new BadRequestAlertException("Le stock ne peut pas être négatif", "produit", "invalidstock");
        }

        int stockAvant = Optional.ofNullable(produit.getStock()).orElse(0);
        produit.setStock(nouveauStock);
        produitRepository.save(produit);

        enregistrerMouvementStock(produit, stockAvant, nouveauStock);
    }

    @Scheduled(cron = "0 0 8 * * *") // Chaque jour à 8h
    public void verifierStockBas() {
        List<ProduitDTO> produitsCritiques = produitRepository.findByStockLessThan(10)
            .stream().map(produitMapper::toDto).collect(Collectors.toList());

        for (ProduitDTO p : produitsCritiques) {
            log.warn("Stock faible détecté pour le produit : {}", p.getNom());
            mailService.envoyerAlerteStock("admin@tunisales.com", p.getNom(), p.getStock());
        }
    }

    private void enregistrerMouvementStock(Produit produit, int ancienneQte, int nouvelleQte) {
        MouvementStock mvt = new MouvementStock();
        mvt.setProduit(produit);
        mvt.setQuantite(Math.abs(ancienneQte - nouvelleQte));
        mvt.setTypeMouvement(nouvelleQte < ancienneQte ? "sortie" : "entrée");
        mvt.setDateMouvement(Instant.now());
        mouvementStockRepository.save(mvt);
    }
    public List<Map<String, Object>> analyserRentabiliteProduits() {
        List<Produit> produits = produitRepository.findAll();

        return produits.stream().map(produit -> {
                Map<String, Object> result = new HashMap<>();
                result.put("id", produit.getId());
                result.put("nom", produit.getNom());

                double prixVente = Optional.ofNullable(produit.getPrix()).orElse(0.0);
                double seuilCout = prixVente * 0.6; // estimation d’un coût d’achat à 60% du prix de vente
                double marge = prixVente - seuilCout;

                result.put("prixVente", prixVente);
                result.put("estimationCout", seuilCout);
                result.put("marge", marge);
                result.put("rentable", marge > 0);

                return result;
            }).sorted((a, b) -> Double.compare((Double) b.get("marge"), (Double) a.get("marge")))
            .collect(Collectors.toList());
    }
    public Map<String, Integer> predireDemandeProduits() {
        List<MouvementStock> mouvements = mouvementStockRepository.findAll();

        Map<String, List<MouvementStock>> parProduit = mouvements.stream()
            .filter(m -> m.getTypeMouvement().equals("SORTIE"))
            .collect(Collectors.groupingBy(m -> m.getProduit().getNom()));

        Map<String, Integer> predictions = new HashMap<>();

        for (Map.Entry<String, List<MouvementStock>> entry : parProduit.entrySet()) {
            String produit = entry.getKey();
            List<MouvementStock> sorties = entry.getValue();

            int moyenne = sorties.stream()
                .collect(Collectors.groupingBy(
                    m -> m.getDateMouvement().atZone(ZoneId.systemDefault()).toLocalDate().getMonth()
                ))
                .values().stream()
                .mapToInt(l -> l.stream().mapToInt(MouvementStock::getQuantite).sum())
                .map(val -> val / 3) // moyenne sur 3 mois
                .sum();

            predictions.put(produit, moyenne);
        }

        return predictions;
    }


}
