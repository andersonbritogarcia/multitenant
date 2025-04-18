package tech.andersonbrito.app;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

class AppApplicationTests {

    static ApplicationModules modules = ApplicationModules.of(AppApplication.class);

    @Test
    void shouldBeCompliant() {
        modules.verify();
    }

    @Test
    void writeDocumentationSnippets() {
        new Documenter(modules).writeModulesAsPlantUml()
                               .writeIndividualModulesAsPlantUml()
                               .writeModuleCanvases()
                               .writeAggregatingDocument();
    }
}
