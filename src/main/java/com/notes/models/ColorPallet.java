package com.notes.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@ToString
@Document(collection = "colorPallets")
public class ColorPallet {

    @Id
    private String id;
    private String userName;
    private List<String> colors;

}
