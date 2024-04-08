package in.nucleusteq.plasma.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.nucleusteq.plasma.dao.AssetRepository;
import in.nucleusteq.plasma.dto.in.asset.AssetAddInDTO;
import in.nucleusteq.plasma.dto.out.asset.AssetDetailOutDTO;
import in.nucleusteq.plasma.dto.out.asset.AssetsListOutDTO;
import in.nucleusteq.plasma.entity.Asset;
import in.nucleusteq.plasma.enums.AssetsStatus;
import in.nucleusteq.plasma.exception.DuplicateException;
import in.nucleusteq.plasma.exception.NotFoundException;
import in.nucleusteq.plasma.payload.SuccessResponse;
import in.nucleusteq.plasma.service.AssetService;

@Service
public class AssetServiceImplementation implements AssetService {
    @Autowired
    private AssetRepository assetRepository;
    
    @Override
    public SuccessResponse addAsset(AssetAddInDTO assetAddInDTO) {
        Asset existingAsset = assetRepository.findBySerialNumber(assetAddInDTO.getSerialNumber());
        if (existingAsset!=null) {
            throw new DuplicateException("Asset with serial number " + assetAddInDTO.getSerialNumber() + " already exists.");
        }
            Asset asset = new Asset();
            asset.setAssetsName(assetAddInDTO.getAssetsName());
            asset.setAssetsStatus(AssetsStatus.UNASSIGNED);
            asset.setAssetsType(assetAddInDTO.getAssetsType());
            asset.setOperatingSystem(assetAddInDTO.getOperatingSystem());
            asset.setProvidedBy(assetAddInDTO.getProvidedBy());
            asset.setSerialNumber(assetAddInDTO.getSerialNumber());
            asset.setWorkLocation(assetAddInDTO.getWorkLocation());
        
            assetRepository.save(asset);
       
            return new SuccessResponse("Asset added successfully.", 200);
    }
    
    @Override
    public List<AssetsListOutDTO> assetList(){
        List<Asset> assets = assetRepository.findAll();
        List<AssetsListOutDTO> assetsListOutDTOs = new ArrayList<>();

        for (Asset asset : assets) {
            AssetsListOutDTO assetsListOutDTO = new AssetsListOutDTO();
            assetsListOutDTO.setAssetsId(asset.getAssetsId());
            assetsListOutDTO.setAssetsName(asset.getAssetsName());
            assetsListOutDTO.setAssetsType(asset.getAssetsType());
            assetsListOutDTO.setSerialNumber(asset.getSerialNumber());
            assetsListOutDTO.setProvidedBy(asset.getProvidedBy());
            assetsListOutDTO.setOperatingSystem(asset.getOperatingSystem());
            assetsListOutDTO.setWorkLocation(asset.getWorkLocation());
            assetsListOutDTO.setAssetsStatus(asset.getAssetsStatus());
            assetsListOutDTOs.add(assetsListOutDTO);
        }

        return assetsListOutDTOs;
        
    }
    
    @Override
    public List<AssetsListOutDTO> getUnassignedAssets() {
        List<Asset> unassignedAssets = assetRepository.findByAssetsStatus(AssetsStatus.UNASSIGNED);
        List<AssetsListOutDTO> unassignedAssetsDTO = new ArrayList<>();

        for (Asset asset : unassignedAssets) {
            AssetsListOutDTO assetDTO = new AssetsListOutDTO();
            assetDTO.setAssetsId(asset.getAssetsId());
            assetDTO.setAssetsName(asset.getAssetsName());
            assetDTO.setAssetsType(asset.getAssetsType());
            assetDTO.setSerialNumber(asset.getSerialNumber());
            assetDTO.setProvidedBy(asset.getProvidedBy());
            assetDTO.setOperatingSystem(asset.getOperatingSystem());
            assetDTO.setWorkLocation(asset.getWorkLocation());
            assetDTO.setAssetsStatus(asset.getAssetsStatus());
            unassignedAssetsDTO.add(assetDTO);
        }

        return unassignedAssetsDTO;
    }
    
    @Override
    public AssetDetailOutDTO getAssetDetails(int assetId) {
        Optional<Asset> optionalAsset = assetRepository.findById(assetId);
        if (optionalAsset.isPresent()) {
            Asset asset = optionalAsset.get();
            AssetDetailOutDTO assetDetail = new AssetDetailOutDTO();
            assetDetail.setAssetsId(asset.getAssetsId());
            assetDetail.setAssetsName(asset.getAssetsName());
            assetDetail.setAssetsType(asset.getAssetsType());
            assetDetail.setSerialNumber(asset.getSerialNumber());
            assetDetail.setProvidedBy(asset.getProvidedBy());
            assetDetail.setOperatingSystem(asset.getOperatingSystem());
            assetDetail.setWorkLocation(asset.getWorkLocation());
            assetDetail.setAssetsStatus(asset.getAssetsStatus());
            return assetDetail;
        } else {
           
            throw new NotFoundException("Asset with ID " + assetId + " not found.");
        }
    }
}
