package model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id","bookUser"})
public class Book {

    private Integer id;
    private String title;
    private LocalDateTime date;
    private Integer bookUser;
}
