package in.nucleusteq.plasma.service;

import in.nucleusteq.plasma.dto.in.asset.AssetAllocationInDTO;
import in.nucleusteq.plasma.payload.SuccessResponse;

public interface AssetAllocationService {
    public SuccessResponse updateAssetAllocation(AssetAllocationInDTO assetAllocationDTO);
}
