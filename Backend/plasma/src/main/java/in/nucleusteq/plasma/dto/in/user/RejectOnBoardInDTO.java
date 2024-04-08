package in.nucleusteq.plasma.dto.in.user;

import in.nucleusteq.plasma.enums.OnBoardingStatus;

public class RejectOnBoardInDTO {
    private int id;
    private OnBoardingStatus onBoardingStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OnBoardingStatus getOnBoardingStatus() {
        return onBoardingStatus;
    }

    public void setOnBoardingStatus(OnBoardingStatus onBoardingStatus) {
        this.onBoardingStatus = onBoardingStatus;
    }
}
