package com.logistics.Smangement.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.TypeAlias;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TypeAlias("FRIGO")
public class FrigoColis extends Colis{
    private Double temperatureMin;
    private Double temperatureMax;
}
