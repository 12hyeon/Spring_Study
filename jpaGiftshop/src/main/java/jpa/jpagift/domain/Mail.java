package jpa.jpagift.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter @Getter
public class Mail {
    private String address;
    private String title;
    private String message;
}
