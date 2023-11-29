package dev.buga.springbatchexample.utility;

import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;

public class DwellingValidator implements Validator<FieldSet> {
    @Override
    public void validate(FieldSet dwellingDTO) throws ValidationException {
//
//        if (dwellingDTO.getDate() == null) {
//            throw new ValidationException("Date must not be empty");
//        }
//
//        if (dwellingDTO.getSA2Code() <= 0) {
//            throw new ValidationException("SA2Code must be a positive integer");
//        }
//
//        if (dwellingDTO.getSA2Name() == null || dwellingDTO.getSA2Name().isEmpty()) {
//            throw new ValidationException("SA2Code must not be empty");
//        }
//
//        // Validate Authority
//        if (dwellingDTO.getAuthority() == null || dwellingDTO.getAuthority().isEmpty()) {
//            throw new ValidationException("Authority must not be empty");
//        }
//
//        // Validate numeric constraints
//        if (dwellingDTO.getTotalDwellings() < 0 || dwellingDTO.getHouses() < 0 || dwellingDTO.getApartments() < 0
//                || dwellingDTO.getRetirementVillas() < 0 || dwellingDTO.getTownhouses() < 0) {
//            throw new ValidationException("Numeric fields must not be negative");
//        }

    }
}
