package com.africom.finance;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.africom.finance");

        noClasses()
            .that()
                .resideInAnyPackage("com.africom.finance.service..")
            .or()
                .resideInAnyPackage("com.africom.finance.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.africom.finance.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
