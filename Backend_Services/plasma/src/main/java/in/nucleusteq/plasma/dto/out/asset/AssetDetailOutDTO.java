package in.nucleusteq.plasma.dto.out.asset;

import in.nucleusteq.plasma.enums.AssetsStatus;
import lombok.Data;

@Data
public class AssetDetailOutDTO {
    private int assetsId;
    private String assetsName;
    private String assetsType;
    private String serialNumber;
    private String providedBy;
    private String operatingSystem;
    private String workLocation;
    private AssetsStatus assetsStatus;
}
