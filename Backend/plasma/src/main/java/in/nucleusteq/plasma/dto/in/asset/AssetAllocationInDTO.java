package in.nucleusteq.plasma.dto.in.asset;

import java.util.Date;

import lombok.Data;

@Data
public class AssetAllocationInDTO {
    private Date allocationDate;
    private int assetId;
    private String employeeId;
}
