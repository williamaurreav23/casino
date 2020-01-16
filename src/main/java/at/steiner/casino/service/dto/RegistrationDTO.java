package at.steiner.casino.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link at.steiner.casino.domain.Player} entity.
 */
public class RegistrationDTO implements Serializable {

    private String name;

    private Boolean isChild;

    private Integer passNumber;

    private Integer money;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isIsChild() {
        return isChild;
    }

    public void setIsChild(Boolean isChild) {
        this.isChild = isChild;
    }

    public Integer getPassNumber() {
        return passNumber;
    }

    public void setPassNumber(Integer passNumber) {
        this.passNumber = passNumber;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationDTO that = (RegistrationDTO) o;
        return Objects.equals(name, that.name) &&
            Objects.equals(isChild, that.isChild) &&
            Objects.equals(passNumber, that.passNumber) &&
            Objects.equals(money, that.money);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, isChild, passNumber, money);
    }

    @Override
    public String toString() {
        return "PlayerDTO{" +
            ", name='" + getName() + "'" +
            ", isChild='" + isIsChild() + "'" +
            ", passNumber=" + getPassNumber() +
            ", money=" + getMoney() +
            "}";
    }
}
