package br.com.jpribeiro.misr_assignment.model;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class PlotOfLand {

    public static final String IDLE = "IDLE";
    public static final String IRRIGATING = "IRRIGATING";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String name;

    @Positive
    private Integer amountOfWater;

    @Positive
    private Integer timeSlots;

    @ManyToOne
    @JoinColumn(name="area_type_id")
    private AreaType areaType;

    @NotNull
    @Pattern(regexp = "IDLE|IRRIGATING")
    private String status = IDLE;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmountOfWater() {
        return amountOfWater;
    }

    public void setAmountOfWater(Integer amountOfWater) {
        this.amountOfWater = amountOfWater;
    }

    public Integer getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(Integer timeSlots) {
        this.timeSlots = timeSlots;
    }

    public AreaType getAreaType() {
        return areaType;
    }

    public void setAreaType(AreaType areaType) {
        this.areaType = areaType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
