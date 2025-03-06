package services.validators;

import models.module2.Entrainment;
import models.module6.InstallationSportive;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EntrainmentValidator {

    private List<String> errors;

    public EntrainmentValidator() {
        this.errors = new ArrayList<>();
    }

    public List<String> validate(Entrainment entrainment, InstallationSportive installationSportive) {
        errors.clear();
        validateRequiredFields(entrainment, installationSportive);

        if (installationSportive != null) {
            validateInstallationAvailability(installationSportive);
            validateTimeSlotAvailability(entrainment, installationSportive);
        }

        validateDateFields(entrainment);
        return errors;
    }

    private void validateRequiredFields(Entrainment entrainment, InstallationSportive installationSportive) {
        if (entrainment.getNom() == null || entrainment.getNom().trim().isEmpty()) {
            errors.add("Le nom de l'entrainement est obligatoire.");
        }
        if (entrainment.getEquipe() == null) {
            errors.add("L'équipe associée à l'entrainement est obligatoire.");
        }
        if (entrainment.getDescription() == null || entrainment.getDescription().trim().isEmpty()) {
            errors.add("La description de l'entrainement est obligatoire.");
        }
        if (installationSportive == null) {
            errors.add("L'installation sportive est obligatoire.");
        }
    }

    private void validateInstallationAvailability(InstallationSportive installationSportive) {
        if (!installationSportive.isDisponible()) {
            errors.add("L'installation sportive sélectionnée n'est pas disponible.");
        }
    }

    private void validateTimeSlotAvailability(Entrainment entrainment, InstallationSportive installationSportive) {
        if (!isTimeSlotAvailable(entrainment, installationSportive)) {
            errors.add("Le créneau horaire sélectionné est déjà réservé pour cette installation.");
        }
    }

    private boolean isTimeSlotAvailable(Entrainment entrainment, InstallationSportive installationSportive) {
        if (entrainment.getDateDebut() == null || entrainment.getDateFin() == null) {
            return false;
        }

        // Check for overlapping time slots
        for (Entrainment existingEntrainment : installationSportive.getEntrainments()) {
            if (existingEntrainment.getDateDebut() == null || existingEntrainment.getDateFin() == null) {
                continue;
            }

            if (entrainment.getDateDebut().isBefore(existingEntrainment.getDateFin()) &&
                    entrainment.getDateFin().isAfter(existingEntrainment.getDateDebut())) {
                return false;
            }
        }
        return true;
    }

    private void validateDateFields(Entrainment entrainment) {
        if (entrainment.getDateDebut() == null) {
            errors.add("La date de début est obligatoire.");
            return;
        }
        if (entrainment.getDateFin() == null) {
            errors.add("La date de fin est obligatoire.");
            return;
        }

        // Check if the start date is in the future
        if (entrainment.getDateDebut().isBefore(LocalDateTime.now())) {
            errors.add("La date de début doit être dans le futur.");
        }

        // Check if the end date is after the start date
        if (entrainment.getDateFin().isBefore(entrainment.getDateDebut())) {
            errors.add("La date de fin doit être après la date de début.");
        }

        // Check if the reservation duration is more than 8 hours
        Duration duration = Duration.between(entrainment.getDateDebut(), entrainment.getDateFin());
        if (duration.toHours() > 8) {
            errors.add("La durée de réservation ne peut pas dépasser 8 heures.");
        }
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public List<String> getErrors() {
        return errors;
    }
}
