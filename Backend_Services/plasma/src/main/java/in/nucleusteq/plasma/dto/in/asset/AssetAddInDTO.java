package in.nucleusteq.plasma.dto.in.asset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssetAddInDTO {
    private String assetsName;
    private String assetsType;
    private String serialNumber;
    private String providedBy;
    private String operatingSystem;
    private String workLocation;
}
