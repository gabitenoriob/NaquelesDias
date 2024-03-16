package com.example.NaquelesDias.infrastructure.DTOs;

import com.example.NaquelesDias.model.user.BiologicalInformation.AddressInformation;
import com.example.NaquelesDias.model.user.BiologicalInformation.BiologicalInformation;
import com.example.NaquelesDias.model.user.User;

public record RegisterDTO (User user, BiologicalInformation biologicalInfo, AddressInformation addressInfo) {
}