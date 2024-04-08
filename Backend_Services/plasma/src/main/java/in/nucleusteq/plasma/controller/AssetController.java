package in.nucleusteq.plasma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.nucleusteq.plasma.dto.in.asset.AssetAddInDTO;
import in.nucleusteq.plasma.dto.in.asset.AssetAllocationInDTO;
import in.nucleusteq.plasma.dto.out.asset.AssetDetailOutDTO;
import in.nucleusteq.plasma.dto.out.asset.AssetsListOutDTO;
import in.nucleusteq.plasma.payload.SuccessResponse;
import in.nucleusteq.plasma.service.AssetAllocationService;
import in.nucleusteq.plasma.service.AssetService;

@RestController
@RequestMapping("/plasma/assets")
public class AssetController {
    @Autowired
    private AssetService assetService;
    
    @Autowired
    private AssetAllocationService assetAllocationService;


    @PostMapping("/add")
    public ResponseEntity<SuccessResponse> addAsset(
            @RequestBody AssetAddInDTO assetAddInDTO) {
        SuccessResponse successResponse = assetService.addAsset(assetAddInDTO);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<AssetsListOutDTO>> getAssetList() {
        List<AssetsListOutDTO> assetsList = assetService.assetList();
        return new ResponseEntity<>(assetsList, HttpStatus.OK);
    }

    @GetMapping("/unassigned")
    public ResponseEntity<List<AssetsListOutDTO>> getUnassignedAssets() {
        List<AssetsListOutDTO> unassignedAssets = assetService
                .getUnassignedAssets();
        return new ResponseEntity<>(unassignedAssets, HttpStatus.OK);
    }

    @GetMapping("/{assetId}")
    public ResponseEntity<AssetDetailOutDTO> getAssetDetails(
            @PathVariable("assetId") int assetId) {
        AssetDetailOutDTO assetDetail = assetService.getAssetDetails(assetId);
        return new ResponseEntity<>(assetDetail, HttpStatus.OK);
    }
    
    @PostMapping("/allocation")
    public ResponseEntity<SuccessResponse> updateAssetAllocation(
            @RequestBody AssetAllocationInDTO assetAllocationDTO) {
        SuccessResponse response = assetAllocationService
                .updateAssetAllocation(assetAllocationDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
