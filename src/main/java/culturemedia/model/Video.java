package culturemedia.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record Video(@NotNull String code,@NotNull @Size(min = 3) String title,@NotNull String description,@NotNull Double duration) {}