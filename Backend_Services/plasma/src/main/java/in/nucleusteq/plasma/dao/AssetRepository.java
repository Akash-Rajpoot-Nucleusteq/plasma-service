package in.nucleusteq.plasma.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import in.nucleusteq.plasma.entity.Asset;
import in.nucleusteq.plasma.enums.AssetsStatus;

public interface AssetRepository extends JpaRepository<Asset, Integer> {
    Asset findBySerialNumber(@Param("serialNumber") String serialNumber);
    List<Asset> findByAssetsStatus(AssetsStatus assetsStatus);
}
