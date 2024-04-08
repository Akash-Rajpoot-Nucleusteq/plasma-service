package in.nucleusteq.plasma.service;

import java.util.List;

import in.nucleusteq.plasma.dto.in.asset.AssetAddInDTO;
import in.nucleusteq.plasma.dto.out.asset.AssetDetailOutDTO;
import in.nucleusteq.plasma.dto.out.asset.AssetsListOutDTO;
import in.nucleusteq.plasma.payload.SuccessResponse;


public interface AssetService {
    public SuccessResponse addAsset(AssetAddInDTO assetAddInDTO);
    public List<AssetsListOutDTO> assetList();
    public List<AssetsListOutDTO> getUnassignedAssets();
    public AssetDetailOutDTO getAssetDetails(int assetId);
}
