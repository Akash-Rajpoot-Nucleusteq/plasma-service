package in.nucleusteq.plasma.service.impl;

import in.nucleusteq.plasma.dao.AssetAllocationRepository;
import in.nucleusteq.plasma.dao.AssetRepository;
import in.nucleusteq.plasma.dao.EmployeeRepository;
import in.nucleusteq.plasma.entity.Asset;
import in.nucleusteq.plasma.entity.AssetAllocation;
import in.nucleusteq.plasma.entity.Employee;
import in.nucleusteq.plasma.enums.AssetsStatus;
import in.nucleusteq.plasma.exception.NotFoundException;
import in.nucleusteq.plasma.payload.SuccessResponse;
import in.nucleusteq.plasma.dto.in.asset.AssetAllocationInDTO;
import in.nucleusteq.plasma.service.AssetAllocationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetAllocationServiceImplementation
        implements
            AssetAllocationService {
    @Autowired
    private AssetAllocationRepository assetAllocationRepository;
    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public SuccessResponse updateAssetAllocation(
            AssetAllocationInDTO assetAllocationDTO) {
        Asset asset = assetRepository.findById(assetAllocationDTO.getAssetId())
                .orElseThrow(() -> new NotFoundException("Asset with ID "
                        + assetAllocationDTO.getAssetId() + " not found."));
        Employee employee = employeeRepository
                .findById(assetAllocationDTO.getEmployeeId())
                .orElseThrow(() -> new NotFoundException("Employee with ID "
                        + assetAllocationDTO.getEmployeeId() + " not found."));
        AssetAllocation assetAllocation = new AssetAllocation();
        assetAllocation
                .setAllocationDate(assetAllocationDTO.getAllocationDate());
        assetAllocation.setAssetsId(asset);
        assetAllocation.setEmployeeId(employee);
        assetAllocationRepository.save(assetAllocation);
        asset.setAssetsStatus(AssetsStatus.ASSIGNED);
        assetRepository.save(asset);
        return new SuccessResponse(
                "Asset" + asset.getAssetsName() + " Allocated Successfuly to "
                        + employee.getUserPersonalDetail().getFirstName()+" "
                        + employee.getUserPersonalDetail().getLastName(),
                200);
    }
}
